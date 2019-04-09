package com.urlshortener.Controllers;


import com.urlshortener.Entities.Address;
import com.urlshortener.POJOS.AddressPOJO;
import com.urlshortener.Services.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@CrossOrigin
@RequestMapping("/cutter")
public class MainController {

    private final AddressService addressService;

    @Autowired
    public MainController(AddressService addressService) {
        this.addressService = addressService;
    }

    @PostMapping("/add")
    @ResponseBody
    public String addAddress(@ModelAttribute AddressPOJO address, Model model) {
        String url = Integer.toHexString(addressService.addAddress(address).getUrlID());
        url = "http://localhost:8080/cutter/" + url;
        model.addAttribute("url", url);
        return url;

        // todo
        // create new method in addressService
    }

    @GetMapping("")
    public String addAddress(Model model) {
        model.addAttribute("Address", new AddressPOJO());
        return "addURL";
    }

    @GetMapping("/{shortURL}")
    public String getURL(@PathVariable String shortURL,
                         Model model) {
        Optional<Address> address = Optional.ofNullable(addressService.getURL(shortURL));
        if (address.isPresent()) {
            String a = address.get().getUrl();
            model.addAttribute("address", a);
            return "getURL";
        } else
            return "404";
    }



      /* @GetMapping("")
    @ResponseBody
    public Iterable<Address> getAllActivesURLs(){
        return addressService.getAllActivesURLs();
    }*/

      /*
    @GetMapping("/{shortURL}")
    @ResponseBody
    public Address getURL(@PathVariable String shortURL) {
        return addressService.getURL(shortURL);
    }
*/
}
