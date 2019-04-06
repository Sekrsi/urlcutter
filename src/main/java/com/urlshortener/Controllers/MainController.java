package com.urlshortener.Controllers;


import com.urlshortener.Entities.Address;
import com.urlshortener.POJOS.AddressPOJO;
import com.urlshortener.Services.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/cutter")
public class MainController {

    private final AddressService addressService;

    @Autowired
    public MainController(AddressService addressService) {
        this.addressService = addressService;
    }


   /* @GetMapping("")
    @ResponseBody
    public Iterable<Address> getAllActivesURLs(){
        return addressService.getAllActivesURLs();
    }*/

    @PostMapping("")
    @ResponseBody
    public Address addAddress(@RequestBody AddressPOJO address) {
        return addressService.addAddress(address);
    }


    @GetMapping("/{shortURL}")
    @ResponseBody
    public Address getURL(@PathVariable String shortURL) {
        return addressService.getURL(shortURL);
    }


}
