package com.urlshortener.Services;


import com.urlshortener.Configs.OwnUserDetails;
import com.urlshortener.Entities.Address;
import com.urlshortener.Entities.User;
import com.urlshortener.POJOS.AddressPOJO;
import com.urlshortener.Repositories.AddressRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Optional;

@Service
public class AddressService {

    private final AddressRepository addressRepository;

    private final OwnUserDetailService ownUserDetailService;

    @Autowired
    public AddressService(AddressRepository addressRepository, OwnUserDetailService ownUserDetailService) {
        this.addressRepository = addressRepository;
        this.ownUserDetailService = ownUserDetailService;
    }

    private Date expDate(int days) {
        System.out.println("Actual date: " + Calendar.getInstance().getTime());
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH, days); // to get previous year add 1
        Date expiryDate = cal.getTime();
        System.out.println("Exp date: " + expiryDate);
        return expiryDate;
    }

    private Date nowDATE() {
        Calendar cal = Calendar.getInstance();
        return cal.getTime();
    }

    private Optional<User> loggedUser(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if(!(auth instanceof AnonymousAuthenticationToken)){
            return Optional.of((User)auth.getPrincipal());
        } else
            return Optional.empty();
    }

    private Address addAddressWithUser(AddressPOJO addressPOJO, User user) {
        Address address = new Address();
        address.setUrl(addressPOJO.getUrl());
        address.setUser(user);
        address.setExpDATE(expDate(180));
        address.setSetupDATE(nowDATE());
        address = addressRepository.save(address);
        return address;
    }

    private Address addAddressWithoutUser(AddressPOJO addressPOJO) {
        Address address = new Address();
        address.setUrl(addressPOJO.getUrl());
        address.setExpDATE(expDate(30));
        address.setSetupDATE(nowDATE());
        address = addressRepository.save(address);
        return address;
    }

    public Address addAddress(AddressPOJO addressPOJO) {
       if(loggedUser().isPresent())
            return addAddressWithUser(addressPOJO, loggedUser().get());

            return addAddressWithoutUser(addressPOJO);
    }

    public Address getURL(String shortURL) {

        int URLID = Integer.parseInt(shortURL, 16);
        System.out.println("Poszukiwany adres: " + URLID);
        Optional<Address> optionalAddress = addressRepository.findById(URLID);
        if (optionalAddress.isPresent()) {
            System.out.println("Odnaleziono adres w bazie");
            Address address = optionalAddress.get();
            if (address.isActive() && address.getExpDATE().compareTo(nowDATE()) > 0) {
                System.out.println("Aktywny, Nie wygasły");
                return address;
            } else if (!address.isActive()) {
                System.out.println("Nie aktywny");
                return null;
            } else {
                System.out.println("Wygasły");
                address.setActive(false);
                addressRepository.save(address);
                return null;
            }
        }
        System.out.println("Nie odnaleziono w bazie");
        return null;

    }

    public Iterable<Address> getAllActivesURLs() {
        Iterable<Address> addresses = addressRepository.findAll();
        HashSet<Address> addresses1 = new HashSet<>();
        for (Address address : addresses) {
            if (address.isActive() && address.getExpDATE().compareTo(nowDATE()) < 0) {
                addresses1.add(address);
            } else if (address.isActive()) {
                address.setActive(false);
                addressRepository.save(address);

            }
        }
        return (addresses1);
    }

    public Iterable<Address> getUserAddresses(User user) {
        return addressRepository.findByUserID(user.getID());
    }

    public Iterable<Address> getUserAddresses() {
        if(loggedUser().isPresent())
            return addressRepository.findByUserID(loggedUser().get().getID());
        else
            return null;

    }

    public String deleteAddress(Integer id) {
        Optional<OwnUserDetails> ownUserDetails = Optional.ofNullable((OwnUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        if (ownUserDetails.isPresent()) {
            User user = ownUserDetails.get().getUser();
            Optional<Address> address = addressRepository.findById(id);
            System.out.println("User logged");
            if (address.isPresent()) {
                Address address1 = address.get();
                if (address1.getUser() != null && address1.getUser().getUsername().equals(user.getUsername())) {
                    address1.setActive(false);
                    addressRepository.save(address1);
                    System.out.println("Deleted Element");
                    return "Deleted element";
                } else {
                    System.out.println("Wrong user");
                    return "You have no permissions to delete the element";
                }
            } else {
                return "No address";
            }
        } else {
            return "You are not logged";
        }

    }
}
