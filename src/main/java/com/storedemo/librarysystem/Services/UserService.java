package com.storedemo.librarysystem.Services;

import com.storedemo.librarysystem.DTOs.Mappers.UserMapper;
import com.storedemo.librarysystem.DTOs.User.CreateUserDTO;
import com.storedemo.librarysystem.DTOs.User.UserDTO;
import com.storedemo.librarysystem.Entities.User;
import com.storedemo.librarysystem.ExceptionHandler.UserNotFoundException;
import com.storedemo.librarysystem.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    private final UserMapper userMapper;

    public UserService() {
        this.userMapper = new UserMapper();
    }

    public List<UserDTO> getAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream().map(userMapper::toDTO).collect(Collectors.toList());
    }

    public UserDTO getUserByEmail(String email) {
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isPresent()) {
            return userMapper.toDTO(user.get());
        }
        throw new UserNotFoundException("User with email " + email + " not found");
    }

    public UserDTO createUser(CreateUserDTO createUserDTO) {
        User user = new User();
        user.setFirstName(createUserDTO.firstName());
        user.setLastName(createUserDTO.lastName());
        user.setEmail(createUserDTO.email());
        user.setPassword(createUserDTO.password());
        user.setRegistrationDate(LocalDateTime.now());
        User saved = userRepository.save(user);
        return userMapper.toDTO(saved);
    }

}
