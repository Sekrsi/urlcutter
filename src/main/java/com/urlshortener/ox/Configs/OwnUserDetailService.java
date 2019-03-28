package com.urlshortener.ox.Configs;


import com.urlshortener.ox.Entities.User;
import com.urlshortener.ox.Entities.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class OwnUserDetailService implements UserDetailsService {

    private final
    UserRepository userRepository;

    @Autowired
    public OwnUserDetailService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException(username);
        }
        return new OwnUserDetails(user);
    }
}
