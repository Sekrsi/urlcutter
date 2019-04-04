package com.urlshortener.ox.Controllers;


import com.urlshortener.ox.POJOS.AddressPOJO;
import com.urlshortener.ox.Services.AddressService;
import com.urlshortener.ox.Entities.Address;
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
    public Iterable<Address> getAllURLs(){
        return addressService.getAllURLs();
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
