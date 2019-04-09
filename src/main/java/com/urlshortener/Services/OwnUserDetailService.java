package com.urlshortener.Services;


import com.urlshortener.Configs.OwnUserDetails;
import com.urlshortener.Entities.User;
import com.urlshortener.Repositories.UserRepository;

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
            User user = userRepository.findByEmail(username);
            if(user == null) {
                user = userRepository.findByUsername(username);
                if (user == null) {
                   // throw new UsernameNotFoundException(username);
                    return null;
                }
                else {
                    return new OwnUserDetails(user);
                }
            }else {
                return new OwnUserDetails(user);
            }
       }else {

            User user = userRepository.findByUsername(username);
            if (user == null) {
                //throw new UsernameNotFoundException(username);
                return null;
            }
            return new OwnUserDetails(user);

        }

    }



}
