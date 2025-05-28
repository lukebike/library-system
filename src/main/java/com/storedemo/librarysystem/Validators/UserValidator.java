package com.storedemo.librarysystem.Validators;

import br.com.fluentvalidator.AbstractValidator;
import com.storedemo.librarysystem.DTOs.User.CreateUserDTO;
import org.springframework.stereotype.Component;

import static java.util.function.Predicate.not;

@Component
public class UserValidator extends AbstractValidator<CreateUserDTO> {

    @Override
    public void rules(){

        ruleFor(CreateUserDTO::firstName)
                .must(not(String::isEmpty))
                .withMessage("First name cannot be empty")
                .withFieldName("firstName")
                .withAttempedValue(CreateUserDTO::firstName);

        ruleFor(CreateUserDTO::firstName)
                .must(String -> String.length() > 2)
                .withMessage("First name must be longer than 2 characters")
                .withFieldName("firstName")
                .withAttempedValue(CreateUserDTO::firstName);

        ruleFor(CreateUserDTO::lastName)
                .must(not(String::isEmpty))
                .withMessage("Last name cannot be empty")
                .withFieldName("lastName")
                .withAttempedValue(CreateUserDTO::lastName);

        ruleFor(CreateUserDTO::firstName)
                .must(String -> String.length() > 2)
                .withMessage("Last name must be longer than 2 characters")
                .withFieldName("lastName")
                .withAttempedValue(CreateUserDTO::lastName);

        ruleFor(CreateUserDTO::email)
                .must(not(String::isEmpty))
                .withMessage("Email cannot be empty")
                .withFieldName("email")
                .withAttempedValue(CreateUserDTO::email);

        ruleFor(CreateUserDTO::email)
                .must(email -> email.contains("@"))
                .withMessage("Email must contain '@' symbol")
                .withFieldName("email")
                .withAttempedValue(CreateUserDTO::email);

        ruleFor(CreateUserDTO::password)
                .must(pw -> pw != null && pw.length() >= 8)
                .withMessage("Password must be at least 8 characters long")
                .must(pw -> pw.matches(".*[A-Z].*"))
                .withMessage("Password must contain at least one uppercase letter")
                .must(pw -> pw.matches(".*[a-z].*"))
                .withMessage("Password must contain at least one lowercase letter")
                .must(pw -> pw.matches(".*\\d.*"))
                .withMessage("Password must contain at least one number")
                .must(pw -> pw.matches(".*[!@#$%^&*(),.?\":{}|<>].*"))
                .withMessage("Password must contain at least one special character");
    }
}
