package com.storedemo.librarysystem.Validators.Books;

import br.com.fluentvalidator.AbstractValidator;
import com.storedemo.librarysystem.DTOs.Book.CreateBookDTO;
import com.storedemo.librarysystem.DTOs.Book.UpdateBookDTO;
import com.storedemo.librarysystem.Repositories.AuthorRepository;
import org.springframework.stereotype.Component;

import static br.com.fluentvalidator.predicate.ComparablePredicate.greaterThanOrEqual;
import static java.util.function.Predicate.not;


@Component
public class UpdateBookValidator extends AbstractValidator<UpdateBookDTO> {

    private final AuthorRepository authorRepository;

    public UpdateBookValidator(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }
    @Override
    public void rules() {
        ruleFor(UpdateBookDTO::title).
                must(not(String::isEmpty))
                .withMessage("Title should not be empty")
                .withFieldName("title")
                .withAttempedValue(UpdateBookDTO::title)
                .must(string -> string.length() > 5)
                .withMessage("Title should contain at least 5 characters")
                .withFieldName("title").withAttempedValue(UpdateBookDTO::title);

        ruleFor(UpdateBookDTO::publicationYear).
                must(greaterThanOrEqual(1000))
                .withMessage("Publication year must be greater than or equal to 1000")
                .withFieldName("publicationYear")
                .withAttempedValue(UpdateBookDTO::publicationYear);

        ruleFor(UpdateBookDTO::availableCopies).
                must(available -> available != null && available >= 1)
                .withMessage("Available copies must be greater than or equal to 1")
                .withFieldName("availableCopies")
                .withAttempedValue(UpdateBookDTO::availableCopies);

        ruleFor(UpdateBookDTO::authorId)
                .must(authorId -> authorId != null && authorRepository.findById(authorId).isPresent())
                .withMessage("Author ID does not exist")
                .withFieldName("authorId").
                withAttempedValue(UpdateBookDTO::authorId);

        ruleFor(UpdateBookDTO::authorId)
                .must(authorId -> authorId != null && authorId >= 1)
                .withMessage("Author ID must be greater than or equal to 1")
                .withFieldName("authorId")
                .withAttempedValue(UpdateBookDTO::authorId);
    }
}
