package com.urlshortener.Controllers;

import com.urlshortener.POJOS.AddressPOJO;
import com.urlshortener.Services.AddressService;
import com.urlshortener.Entities.Address;
import org.springframework.web.bind.annotation.*;


@RestController()
@RequestMapping("/user")
public class UserController {

    private final
    AddressService addressService;

    public UserController(AddressService addressService) {
        this.addressService = addressService;
    }



    @GetMapping("/addresses")
    public Iterable<Address> userAddresses(){
        return addressService.getUserAddresses();
    }

    @PostMapping("/addresses")
    public Address addAddress(@RequestBody AddressPOJO address){
        return addressService.addAddress(address);
    }

    @DeleteMapping("/addresses/{id}")
    public String deleteAddress(@PathVariable Integer id){
        return addressService.deleteAddress(id);
    }

    @PutMapping("/addresses")
    public Address updateAddress(@RequestBody Address address){
        return address;
    }

    /*
    @GetMapping("")
    @ResponseBody
    public User userInfo() { */
    /*    try {
            OwnUserDetails ownUserDetails = (OwnUserDetails)
                    SecurityContextHolder.getContext().getAuthentication().getPrincipal();

            User user = ownUserDetails.getUser();
            System.out.println(user.getEmail() + " " + user.getUsername() + " " + user.getRole().getAuthority());
            return user;
        }catch (Exception e){
            return null;
        }
        }*/
}
