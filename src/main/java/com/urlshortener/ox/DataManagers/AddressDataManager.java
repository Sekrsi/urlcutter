package com.urlshortener.ox.DataManagers;


import com.urlshortener.ox.Entities.Address;
import com.urlshortener.ox.Entities.AddressRepository;
import com.urlshortener.ox.Entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class AddressDataManager {

    private final AddressRepository addressRepository;

    @Autowired
    public AddressDataManager(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    public Address saveWithHEX(Address address){
        address = addressRepository.save(address);
        address.setHEXvalue(Integer.toHexString(address.getUrlID()));
        return address;
    }

    public Address getURL(String shortURL) {

        int URLID = Integer.parseInt(shortURL, 16);
        Optional<Address> optionalAddress = addressRepository.findById(URLID);
        if(optionalAddress.isPresent())
        {
            Address address = optionalAddress.get();
            address.setHEXvalue(Integer.toHexString(address.getUrlID()));
            return address;
        }

        return null;
    }

    public Iterable<Address> getAllURLs(){
        return addressRepository.findAll();
    }

    public Iterable<Address> getUserAddresses(User user){
        return addressRepository.userAddresses(user.getId());
    }


}
