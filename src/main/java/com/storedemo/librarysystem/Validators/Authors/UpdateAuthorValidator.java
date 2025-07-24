package com.storedemo.librarysystem.Validators.Authors;

import br.com.fluentvalidator.AbstractValidator;
import com.storedemo.librarysystem.DTOs.Author.UpdateAuthorDTO;
import org.springframework.stereotype.Component;


import static java.util.function.Predicate.not;

@Component
public class UpdateAuthorValidator extends AbstractValidator<UpdateAuthorDTO> {

    @Override
    public void rules(){

        ruleFor(UpdateAuthorDTO::firstName)
                .must(not(String::isEmpty))
                .withMessage("First name cannot be empty")
                .withFieldName("firstName")
                .withAttempedValue(UpdateAuthorDTO::firstName);

        ruleFor(UpdateAuthorDTO::firstName)
                .must(String -> String.length() > 2)
                .withMessage("First name must be longer than 2 characters")
                .withFieldName("firstName")
                .withAttempedValue(UpdateAuthorDTO::firstName);

        ruleFor(UpdateAuthorDTO::lastName)
                .must(not(String::isEmpty))
                .withMessage("Last name cannot be empty")
                .withFieldName("lastName")
                .withAttempedValue(UpdateAuthorDTO::lastName);

        ruleFor(UpdateAuthorDTO::firstName)
                .must(String -> String.length() > 2)
                .withMessage("Last name must be longer than 2 characters")
                .withFieldName("lastName")
                .withAttempedValue(UpdateAuthorDTO::lastName);

        ruleFor(UpdateAuthorDTO::nationality)
                .must(not(String::isEmpty))
                .withMessage("Nationality cannot be empty")
                .withFieldName("nationality")
                .withAttempedValue(UpdateAuthorDTO::nationality);

        ruleFor(UpdateAuthorDTO::nationality)
                .must(String -> String.length() > 2)
                .withMessage("Nationality must be longer than 2 characters")
                .withFieldName("nationality")
                .withAttempedValue(UpdateAuthorDTO::nationality);
    }
}
