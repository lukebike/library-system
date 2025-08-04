package com.storedemo.librarysystem.Validators.Loans;

import br.com.fluentvalidator.AbstractValidator;
import com.storedemo.librarysystem.DTOs.Loan.CreateLoanDTO;
import com.storedemo.librarysystem.Repositories.UserRepository;
import com.storedemo.librarysystem.Repositories.BookRepository;
import org.springframework.stereotype.Component;


@Component
public class LoanValidator extends AbstractValidator<CreateLoanDTO> {


    private final UserRepository userRepository;
    private final BookRepository bookRepository;

    public LoanValidator(UserRepository userRepository, BookRepository bookRepository) {
        this.userRepository = userRepository;
        this.bookRepository = bookRepository;
    }

    @Override
    public void rules(){

        ruleFor(CreateLoanDTO::bookId)
                .must(bookId -> bookId != null && bookRepository.findById(bookId).isPresent())
                .withMessage("Book ID is null or not found");

        ruleFor(CreateLoanDTO::userId)
        .must(userId -> userId != null && userRepository.findById(userId).isPresent())
                .withMessage("User ID is null or not found");


    }
}
