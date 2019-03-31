package com.urlshortener.ox.Controllers;


import com.urlshortener.ox.Configs.OwnUserDetails;
import com.urlshortener.ox.DataManagers.AddressDataManager;
import com.urlshortener.ox.Entities.Address;
import com.urlshortener.ox.Entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;


@RestController()
@RequestMapping("/user")
public class UserController {

    @Autowired
    AddressDataManager addressDataManager;

    @GetMapping("")
    @ResponseBody
    public User userInfo() {
        OwnUserDetails ownUserDetails = (OwnUserDetails)
                SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        User user = ownUserDetails.getUser();
        System.out.println(user.getEmail() + " " + user.getUsername() + " " + user.getRole().getAuthority());
        return user;
    }

    @GetMapping("/addresses")
    public Iterable<Address> userAddresses(){
        System.out.println("userAdresses");
        OwnUserDetails ownUserDetails = (OwnUserDetails)
                SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        User user = ownUserDetails.getUser();
        return addressDataManager.getUserAddresses(user);
    }



}
