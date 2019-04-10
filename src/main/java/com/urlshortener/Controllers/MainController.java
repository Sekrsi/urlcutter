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
@RequestMapping("/web")
public class MainController {

    private final AddressService addressService;

    @Autowired
    public MainController(AddressService addressService) {
        this.addressService = addressService;
    }

    @PostMapping("/add")
    public String addAddress(@ModelAttribute Address address, Model model) {
        String url = Integer.toHexString(addressService.addAddress(address).getUrlID());
        url = "http://localhost:8080/web/" + url;
        model.addAttribute("url", url);
        return "url";

    }

    @GetMapping("")
    public String addAddress(Model model) {
        model.addAttribute("Address", new AddressPOJO());
        return "addURL";
    }

    @GetMapping("/{shortURL}")
    public String getURL(@PathVariable String shortURL,
                         Model model) {
        Optional<Address> address = Optional.ofNullable(addressService.getAddress(shortURL));
        if (address.isPresent()) {
            String a = address.get().getUrl();
            model.addAttribute("address", a);
            return "getURL";
        } else
            return "404";
    }




}
