package com.storedemo.librarysystem.Validators;

import br.com.fluentvalidator.AbstractValidator;
import com.storedemo.librarysystem.DTOs.Author.CreateAuthorDTO;
import org.springframework.stereotype.Component;


import static java.util.function.Predicate.not;

@Component
public class AuthorValidator extends AbstractValidator<CreateAuthorDTO> {

    @Override
    public void rules(){

        ruleFor(CreateAuthorDTO::firstName)
                .must(not(String::isEmpty))
                .withMessage("First name cannot be empty")
                .withFieldName("firstName")
                .withAttempedValue(CreateAuthorDTO::firstName);

        ruleFor(CreateAuthorDTO::firstName)
                .must(String -> String.length() > 2)
                .withMessage("First name must be longer than 2 characters")
                .withFieldName("firstName")
                .withAttempedValue(CreateAuthorDTO::firstName);

        ruleFor(CreateAuthorDTO::lastName)
        .must(not(String::isEmpty))
                .withMessage("Last name cannot be empty")
                .withFieldName("lastName")
                .withAttempedValue(CreateAuthorDTO::lastName);

        ruleFor(CreateAuthorDTO::firstName)
                .must(String -> String.length() > 2)
                .withMessage("Last name must be longer than 2 characters")
                .withFieldName("lastName")
                .withAttempedValue(CreateAuthorDTO::lastName);

        ruleFor(CreateAuthorDTO::nationality)
                .must(not(String::isEmpty))
                .withMessage("Nationality cannot be empty")
                .withFieldName("nationality")
                .withAttempedValue(CreateAuthorDTO::nationality);

        ruleFor(CreateAuthorDTO::nationality)
                .must(String -> String.length() > 2)
                .withMessage("Nationality must be longer than 2 characters")
                .withFieldName("nationality")
                .withAttempedValue(CreateAuthorDTO::nationality);
    }
}
