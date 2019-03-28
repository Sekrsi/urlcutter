package com.urlshortener.ox.Controllers;


import com.urlshortener.ox.DataManagers.AddressDataManager;
import com.urlshortener.ox.Entities.Address;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/cutter")
public class MainController {

    private final AddressDataManager addressDataManager;

    @Autowired
    public MainController(AddressDataManager addressDataManager) {
        this.addressDataManager = addressDataManager;
    }


    @GetMapping({"/", ""})
    @ResponseBody
    public Iterable<Address> getAllURLs(){
        return addressDataManager.getAllURLs();
    }

    @PostMapping({"/", ""})
    @ResponseBody
    public Address cutURL(@RequestBody Address address) {
        return addressDataManager.saveWithHEX(address);
    }

    @GetMapping("/{shortURL}")
    @ResponseBody
    public Address getURL(@PathVariable String shortURL) {
        return addressDataManager.getURL(shortURL);
    }


}
