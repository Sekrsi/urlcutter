package com.urlshortener.ox.Services;


import com.urlshortener.ox.Configs.OwnUserDetails;
import com.urlshortener.ox.Entities.Address;
import com.urlshortener.ox.Repositories.AddressRepository;
import com.urlshortener.ox.Entities.User;
import com.urlshortener.ox.POJOS.AddressPOJO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.stereotype.Service;


import java.util.*;

@Service
public class AddressService {

    private final AddressRepository addressRepository;

    private final OwnUserDetailService ownUserDetailService;

    private Date expDate(int days){
        System.out.println("Actual date: " + Calendar.getInstance().getTime());
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH, days); // to get previous year add 1
        Date expiryDate = cal.getTime();
        System.out.println("Exp date: " +expiryDate);
        return expiryDate;
    }

    private Date nowDATE(){
        Calendar cal = Calendar.getInstance();
        return cal.getTime();
    }

    private Address addAddressWithUser(AddressPOJO addressPOJO, User user){
        Address address = new Address();
        address.setUrl(addressPOJO.getUrl());
        address.setUser(user);
        address.setExpDATE(expDate(180));
        address.setSetupDATE(nowDATE());
        address = addressRepository.save(address);
        return address;
    }

    private Address addAddressWithoutUser(AddressPOJO addressPOJO){
        Address address = new Address();
        address.setUrl(addressPOJO.getUrl());
        address.setExpDATE(expDate(30));
        address.setSetupDATE(nowDATE());
        address = addressRepository.save(address);
        return address;
    }


    @Autowired
    public AddressService(AddressRepository addressRepository, OwnUserDetailService ownUserDetailService) {
        this.addressRepository = addressRepository;
        this.ownUserDetailService = ownUserDetailService;
    }

    public Address addAddress(AddressPOJO addressPOJO) {
          Optional<OwnUserDetails> ownUserDetails = Optional.ofNullable((OwnUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        if(ownUserDetails.isPresent()){
            User user = ownUserDetails.get().getUser();
            return addAddressWithUser(addressPOJO,user);
        }
        else {
            return addAddressWithoutUser(addressPOJO);
        }

    }

    public Address getURL(String shortURL) {

        int URLID = Integer.parseInt(shortURL, 16);
        Optional<Address> optionalAddress = addressRepository.findById(URLID);
        if(optionalAddress.isPresent()){
            Address address = optionalAddress.get();
            if(address.isActive() && address.getExpDATE().compareTo(nowDATE())<0){
                return address;
            }
            else if(!address.isActive()){
                return null;
            }
            else {
                address.setActive(false);
                addressRepository.save(address);
                return null;
            }
        }
        return null;

    }

    public Iterable<Address> getAllActivesURLs(){
        Iterable<Address> addresses = addressRepository.findAll();
        HashSet<Address> addresses1 = new HashSet<>();
        for(Address address: addresses){
            if(address.isActive() && address.getExpDATE().compareTo(nowDATE())<0){
                addresses1.add(address);
            }
            else if (address.isActive()) {
                    address.setActive(false);
                    addressRepository.save(address);

            }
        }
        return (addresses1);
    }

    public Iterable<Address> getUserAddresses(User user){
        return addressRepository.findByUserID(user.getID());
    }

    public String deleteAddress(Integer id) {
        Optional<OwnUserDetails> ownUserDetails = Optional.ofNullable((OwnUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        if (ownUserDetails.isPresent()) {
            User user = ownUserDetails.get().getUser();
            Optional<Address> address = addressRepository.findById(id);
            System.out.println("User logged");
            if(address.isPresent()) {
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
            }
            else {
                return "No address";
            }
        }
        else {
            return "You are not logged";
        }

    }
}
