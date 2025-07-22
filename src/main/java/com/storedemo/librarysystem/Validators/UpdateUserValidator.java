package com.storedemo.librarysystem.Validators;

import br.com.fluentvalidator.AbstractValidator;
import com.storedemo.librarysystem.DTOs.User.UpdateUserDTO;
import org.springframework.stereotype.Component;

import static java.util.function.Predicate.not;

@Component
public class UpdateUserValidator extends AbstractValidator<UpdateUserDTO> {

    @Override
    public void rules() {
        ruleFor(UpdateUserDTO::firstName)
                .must(not(String::isEmpty))
                .withMessage("First name cannot be empty")
                .withFieldName("firstName")
                .withAttempedValue(UpdateUserDTO::firstName);

        ruleFor(UpdateUserDTO::firstName)
                .must(str -> str.length() > 2)
                .withMessage("First name must be longer than 2 characters")
                .withFieldName("firstName")
                .withAttempedValue(UpdateUserDTO::firstName);

        ruleFor(UpdateUserDTO::lastName)
                .must(not(String::isEmpty))
                .withMessage("Last name cannot be empty")
                .withFieldName("lastName")
                .withAttempedValue(UpdateUserDTO::lastName);

        ruleFor(UpdateUserDTO::lastName)
                .must(str -> str.length() > 2)
                .withMessage("Last name must be longer than 2 characters")
                .withFieldName("lastName")
                .withAttempedValue(UpdateUserDTO::lastName);

        ruleFor(UpdateUserDTO::email)
                .must(not(String::isEmpty))
                .withMessage("Email cannot be empty")
                .withFieldName("email")
                .withAttempedValue(UpdateUserDTO::email);

        ruleFor(UpdateUserDTO::email)
                .must(email -> email.contains("@"))
                .withMessage("Email must contain '@' symbol")
                .withFieldName("email")
                .withAttempedValue(UpdateUserDTO::email);


        ruleFor(UpdateUserDTO::newPassword)
                .must(pw -> pw == null || pw.isEmpty() ||
                        (pw.length() >= 8 &&
                                pw.matches(".*[A-Z].*") &&
                                pw.matches(".*[a-z].*") &&
                                pw.matches(".*\\d.*") &&
                                pw.matches(".*[!@#$%^&*(),.?\":{}|<>].*")))
                .withMessage("New password must be at least 8 characters, contain upper and lower case letters, a number, and a special character");

        ruleFor(UpdateUserDTO::currentPassword)
                .must(not(String::isEmpty))
                .withMessage("Current password cannot be empty")
                .withFieldName("currentPassword")
                .withAttempedValue(UpdateUserDTO::currentPassword);
    }
}