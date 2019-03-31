package com.urlshortener.ox.Services;


import com.urlshortener.ox.Configs.OwnUserDetails;
import com.urlshortener.ox.Entities.User;
import com.urlshortener.ox.Entities.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.regex.Pattern;


@Service
public class OwnUserDetailService implements UserDetailsService {

    private final
    UserRepository userRepository;

    @Autowired
    public OwnUserDetailService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    private static boolean isEmailValid(String email) {
        final Pattern EMAIL_REGEX = Pattern.compile("[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", Pattern.CASE_INSENSITIVE);
        return EMAIL_REGEX.matcher(email).matches();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        if(isEmailValid(username)){
            System.out.println("Email probably valid");
            User user = userRepository.findByEmail(username);
            if(user == null) {
                System.out.println("looking by username");
                user = userRepository.findByUsername(username);
                if (user == null) {
                    System.out.println("No found " + username);
                    throw new UsernameNotFoundException(username);
                }
                else {
                    System.out.println("Found " + username);
                    return new OwnUserDetails(user);
                }

            }else {
                return new OwnUserDetails(user);
            }
       }else {

            System.out.println("Email invalid");
            System.out.println("looking by username");
            User user = userRepository.findByUsername(username);
            if (user == null) {
                System.out.println("No found " + username);
                throw new UsernameNotFoundException(username);
            }
            System.out.println("Found user! " + username);
            return new OwnUserDetails(user);

        }

    }
}
