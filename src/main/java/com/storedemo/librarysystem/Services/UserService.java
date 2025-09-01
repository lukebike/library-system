package com.storedemo.librarysystem.Services;

import com.storedemo.librarysystem.DTOs.Mappers.UserMapper;
import com.storedemo.librarysystem.DTOs.User.CreateUserDTO;
import com.storedemo.librarysystem.DTOs.User.UpdateUserDTO;
import com.storedemo.librarysystem.DTOs.User.UserDTO;
import com.storedemo.librarysystem.Entities.User;
import com.storedemo.librarysystem.ExceptionHandler.UserNotFoundException;
import com.storedemo.librarysystem.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

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

    public UserDTO updateUser(Long id, UpdateUserDTO updateUserDTO){
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isEmpty()) return null;
        User user = userOptional.get();
        if(!updateUserDTO.currentPassword().equals(user.getPassword())) throw new UserNotFoundException("Password does not match");
        user.setFirstName(updateUserDTO.firstName());
        user.setLastName(updateUserDTO.lastName());
        user.setEmail(updateUserDTO.email());
        if(updateUserDTO.newPassword() != null && !updateUserDTO.newPassword().isEmpty()) {
            user.setPassword(updateUserDTO.newPassword());
        }
        User savedUser = userRepository.save(user);
        return userMapper.toDTO(savedUser);
    }

    public UserDTO createUser(CreateUserDTO createUserDTO) {
        User user = new User();
        user.setFirstName(createUserDTO.firstName());
        user.setLastName(createUserDTO.lastName());
        user.setEmail(createUserDTO.email());
        String hashedPassword = passwordEncoder.encode(createUserDTO.password());
        user.setPassword(hashedPassword);
        user.setRegistrationDate(LocalDateTime.now());
        user.setRole("USER");
        User saved = userRepository.save(user);
        return userMapper.toDTO(saved);
    }

    public boolean deleteUser(Long id){
        Optional<User> userOptional = userRepository.findById(id);
        if(userOptional.isEmpty()) return false;
        userOptional.get().setEnabled(false);
        userRepository.save(userOptional.get());
        return true;
    }
}
