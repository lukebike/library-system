package com.storedemo.librarysystem.Services;

import com.storedemo.librarysystem.Entities.User;
import com.storedemo.librarysystem.ExceptionHandler.UserNotFoundException;
import com.storedemo.librarysystem.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UserNotFoundException {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new UserNotFoundException(email));

        return org.springframework.security.core.userdetails.User.builder().username(user.getEmail()).password(user.getPassword()).authorities("ROLE_" + user.getRole()).build();
    }
}
