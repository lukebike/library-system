package com.storedemo.librarysystem.Validators.Books;

import br.com.fluentvalidator.AbstractValidator;
import com.storedemo.librarysystem.DTOs.Book.CreateBookDTO;
import com.storedemo.librarysystem.Repositories.AuthorRepository;
import org.springframework.stereotype.Component;

import static br.com.fluentvalidator.predicate.ComparablePredicate.greaterThanOrEqual;
import static java.util.function.Predicate.not;

@Component
public class BookValidator extends AbstractValidator<CreateBookDTO> {

    private final AuthorRepository authorRepository;

    public BookValidator(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Override
    public void rules() {

        ruleFor(CreateBookDTO::title).
                must(not(String::isEmpty))
                .withMessage("Title should not be empty")
                .withFieldName("title")
                .withAttempedValue(CreateBookDTO::title)
                        .must(string -> string.length() > 5)
                        .withMessage("Title should contain at least 5 characters")
                        .withFieldName("title").withAttempedValue(CreateBookDTO::title);

        ruleFor(CreateBookDTO::publicationYear).
                must(greaterThanOrEqual(1000))
                .withMessage("Publication year must be greater than or equal to 1000")
                .withFieldName("publicationYear")
                .withAttempedValue(CreateBookDTO::publicationYear);

        ruleFor(CreateBookDTO::availableCopies).
                must(available -> available != null && available >= 1)
                .withMessage("Available copies must be greater than or equal to 1")
                .withFieldName("availableCopies")
                .withAttempedValue(CreateBookDTO::availableCopies);


        ruleFor(CreateBookDTO::totalCopies)
                .must(totalCopies -> totalCopies != null && totalCopies >= 1)
                .withMessage("Total copies must be greater than or equal to 1")
                .withFieldName("totalCopies")
                .withAttempedValue(CreateBookDTO::availableCopies);

        ruleFor(CreateBookDTO::authorId)
                .must(authorId -> authorId != null && authorRepository.findById(authorId).isPresent())
                .withMessage("Author ID does not exist")
                .withFieldName("authorId").
                withAttempedValue(CreateBookDTO::authorId);

        ruleFor(CreateBookDTO::authorId)
                .must(authorId -> authorId != null && authorId >= 1)
                .withMessage("Author ID must be greater than or equal to 1")
                .withFieldName("authorId")
                .withAttempedValue(CreateBookDTO::authorId);


    }
}
