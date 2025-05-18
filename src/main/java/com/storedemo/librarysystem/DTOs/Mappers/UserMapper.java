package com.storedemo.librarysystem.DTOs.Mappers;

import com.storedemo.librarysystem.DTOs.*;
import com.storedemo.librarysystem.Entities.Author;
import com.storedemo.librarysystem.Entities.Book;
import com.storedemo.librarysystem.Entities.User;

public class UserMapper {

        public static UserDTO toDTO(User user) {
            return new UserDTO(
                    user.getId(),
                    user.getFirstName(),
                    user.getLastName(),
                    user.getEmail(),
                    user.getPassword(),
                    user.getRegistrationDate()
            );
        }


        public static User toEntity(UserDTO userDTO) {
            User user = new User();
            user.setId(userDTO.id());
            user.setFirstName(userDTO.firstName());
            user.setLastName(userDTO.lastName());
            user.setEmail(userDTO.email());
            user.setPassword(userDTO.password());
            user.setRegistrationDate(userDTO.registrationDate());
            return user;
        }

        public static SimpleUserDTO toSimpleDTO(User user) {
            return new SimpleUserDTO(user.getId(), user.getFirstName(), user.getLastName(), user.getEmail());
        }
    }

