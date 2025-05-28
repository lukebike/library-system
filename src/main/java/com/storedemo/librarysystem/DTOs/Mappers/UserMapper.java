package com.storedemo.librarysystem.DTOs.Mappers;

import com.storedemo.librarysystem.DTOs.User.UserDTO;
import com.storedemo.librarysystem.Entities.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

        public UserDTO toDTO(User user) {
            return new UserDTO(
                    user.getId(),
                    user.getFirstName(),
                    user.getLastName(),
                    user.getEmail(),
                    user.getRegistrationDate()
            );
        }


        public User toEntity(UserDTO userDTO) {
            User user = new User();
            user.setId(userDTO.id());
            user.setFirstName(userDTO.firstName());
            user.setLastName(userDTO.lastName());
            user.setEmail(userDTO.email());
            user.setRegistrationDate(userDTO.registrationDate());
            return user;
        }

    }

