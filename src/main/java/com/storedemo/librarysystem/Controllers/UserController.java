package com.storedemo.librarysystem.Controllers;

import br.com.fluentvalidator.context.ValidationResult;
import com.storedemo.librarysystem.DTOs.User.CreateUserDTO;
import com.storedemo.librarysystem.DTOs.User.UpdateUserDTO;
import com.storedemo.librarysystem.DTOs.User.UserDTO;
import com.storedemo.librarysystem.Services.UserService;
import com.storedemo.librarysystem.Validators.UpdateUserValidator;
import com.storedemo.librarysystem.Validators.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserValidator userValidator;

    @Autowired
    private UpdateUserValidator updateUserValidator;

    @GetMapping
    public ResponseEntity<List<UserDTO>> getAllUsers() {
     List<UserDTO> usersList = userService.getAllUsers();
     if(usersList.isEmpty()){
         return new ResponseEntity<>(HttpStatus.NO_CONTENT);
     } else {
         return new ResponseEntity<>(usersList, HttpStatus.OK);
     }
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<UserDTO> getUserByEmail(@PathVariable String email) {
        UserDTO userDTO = userService.getUserByEmail(email);
        if(userDTO == null){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(userDTO, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Long id, @RequestBody UpdateUserDTO updateUserDTO) {
        ValidationResult validationResult = updateUserValidator.validate(updateUserDTO);
        if(!validationResult.isValid()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(validationResult);
        }
        UserDTO updatedUser = userService.updateUser(id, updateUserDTO);
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody CreateUserDTO createUserDTO) {
        ValidationResult validationResult = userValidator.validate(createUserDTO);
        if(!validationResult.isValid()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(validationResult);
        }
        UserDTO userDTO = userService.createUser(createUserDTO);
        return new ResponseEntity<>(userDTO, HttpStatus.CREATED);
    }

    @DeleteMapping
    public ResponseEntity<?> deleteUser(@PathVariable Long id){
        boolean isDeleted = userService.deleteUser(id);
        if(!isDeleted){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
