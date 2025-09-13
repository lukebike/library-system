package com.storedemo.librarysystem.Services;

import com.storedemo.librarysystem.Entities.User;
import com.storedemo.librarysystem.ExceptionHandler.UserNotFoundException;
import com.storedemo.librarysystem.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;


import java.util.*;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UserNotFoundException {
        Optional<User> userOptional = userRepository.findByEmail(email);
        if(userOptional.isEmpty()) {
            throw new UserNotFoundException("User with email " + email + " not found");
        }
        User user = userOptional.get();

        return org.springframework.security.core.userdetails.User.withUsername(user.getEmail())
                .password(user.getPassword())
                .authorities("ROLE_" + user.getRole())
                .accountExpired(false)
                .accountLocked(false)
                .disabled(!user.isEnabled())
                .build();
    }
}

//    private UserDetails buildUserDetails(User user){
//        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
//        for (Role role : user.getRoles()) {
//            grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_" + role.getName()));
//
//            for(Permission permission : role.getPermissions()) {
//                grantedAuthorities.add(new SimpleGrantedAuthority(permission.getName()));
//            }
//        }
//
//        return org.springframework.security.core.userdetails.User.builder()
//                .username(user.getEmail())
//                .password(user.getPassword())
//                .authorities(grantedAuthorities).disabled(!user.isEnabled()).build();
//    }
//
//
//}
