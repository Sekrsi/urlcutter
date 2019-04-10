package com.urlshortener.Controllers;

import com.urlshortener.Entities.Address;
import com.urlshortener.Services.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api")
public class ApiController {

    private final AddressService addressService;

    public ApiController(AddressService addressService) {
        this.addressService = addressService;
    }


    @GetMapping("")
    public Iterable<Address> getAllActivesURLs(){
        return addressService.getAllActivesURLs();
    }

    @GetMapping("/{shortURL}")
    public Address getAddress(@PathVariable String shortURL) {
        return addressService.getAddress(shortURL);
    }

    @PostMapping("")
    public Address addAddress(@RequestBody Address address){
        return addressService.addAddress(address);
    }

/*    @GetMapping("/{shortURL}")
    public Address getURL(@PathVariable String shortURL) {
        Optional<Address> address = Optional.ofNullable(addressService.getAddress(shortURL));
        return address.orElse(null);
    }*/

}
