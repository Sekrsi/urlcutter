package com.urlshortener.ox.Services;


import com.urlshortener.ox.Configs.OwnUserDetails;
import com.urlshortener.ox.Entities.Address;
import com.urlshortener.ox.Repositories.AddressRepository;
import com.urlshortener.ox.Entities.User;
import com.urlshortener.ox.POJOS.AddressPOJO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.stereotype.Service;


import java.util.Calendar;
import java.util.Date;
import java.util.Optional;

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

    public Iterable<Address> getAllURLs(){
        return addressRepository.findAll();
        // todo
        // check if address is active and not expired
    }

    public Iterable<Address> getUserAddresses(User user){
        return addressRepository.findByUserID(user.getID());
    }

    public String deleteAddress(Integer id){
        Optional<Address> address = addressRepository.findById(id);
        if(address.isPresent()){
            System.out.println("Element exist");
            try {
                OwnUserDetails ownUserDetails = (OwnUserDetails)
                        SecurityContextHolder.getContext().getAuthentication().getPrincipal();
                User user = ownUserDetails.getUser();

                Address address1 = address.get();
                System.out.println("User logged");
                if(address1.getUser()!=null && address1.getUser().getUsername().equals(user.getUsername())) {
                    address1.setActive(false);
                    addressRepository.save(address1);
                    System.out.println("Deleted Element");
                    return "Deleted element";
                }
                else {
                    System.out.println("Wrong user");
                    return "You have no permissions to delete element";
                }
            }
            catch (ClassCastException e)
            {
                e.printStackTrace();
                System.out.println("Exception");
                return "You are not logged";
            }
        }
        return "Element no exist";
    }

}
