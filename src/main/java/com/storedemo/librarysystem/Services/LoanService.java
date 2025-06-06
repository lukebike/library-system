package com.storedemo.librarysystem.Services;

import com.storedemo.librarysystem.DTOs.Book.BookDTO;
import com.storedemo.librarysystem.DTOs.Loan.CreateLoanDTO;
import com.storedemo.librarysystem.DTOs.Loan.LoanDTO;
import com.storedemo.librarysystem.DTOs.Mappers.AuthorMapper;
import com.storedemo.librarysystem.DTOs.Mappers.BookMapper;
import com.storedemo.librarysystem.DTOs.Mappers.LoanMapper;
import com.storedemo.librarysystem.DTOs.Mappers.UserMapper;
import com.storedemo.librarysystem.DTOs.User.UserDTO;
import com.storedemo.librarysystem.Entities.Book;
import com.storedemo.librarysystem.Entities.Loan;
import com.storedemo.librarysystem.Entities.User;
import com.storedemo.librarysystem.ExceptionHandler.LoanNotFoundException;
import com.storedemo.librarysystem.Repositories.BookRepository;
import com.storedemo.librarysystem.Repositories.LoanRepository;
import com.storedemo.librarysystem.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LoanService {

    @Autowired
    private LoanRepository loanRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private LoanMapper loanMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private BookMapper bookMapper;

    @Autowired
    private AuthorMapper authorMapper;

    public LoanService() {
    }

    @Transactional(readOnly = true)
    public List<LoanDTO> getLoansByUserId(long userId) {
        Optional<List<Loan>> loans = loanRepository.findByUserId(userId);
        return loans
                .map(list -> list.stream().map(loan -> new LoanDTO(loan.getId(),
                                userMapper.toDTO(loan.getUser()),
                                bookMapper.toDTO(loan.getBook()),
                                loan.getLoanDate(),
                                loan.getDueDate(),
                                loan.getReturnDate()))
                        .collect(Collectors.toList()))
                .orElse(Collections.emptyList());
//        List<LoanDTO> loanDTOs = new ArrayList<>();
//        for (Loan loan : loans.get()) {
//            UserDTO userDTO = userMapper.toDTO(loan.getUser());
//            BookDTO bookDTO = bookMapper.toDTO(loan.getBook());
//            LoanDTO loanDTO = new LoanDTO(userDTO, bookDTO, loan.getLoanDate(), loan.getDueDate(), loan.getReturnDate());
//            loanDTOs.add(loanDTO);
//        }
//        return loanDTOs;
    }

    @Transactional
    public LoanDTO createLoan(CreateLoanDTO createLoanDTO) {
        Loan loan = new Loan();

        Book book = bookRepository.findById(createLoanDTO.bookId()).orElse(null);
        if (book != null) {
            if (book.getAvailableCopies() == 0) {
                throw new RuntimeException("Can not loan without available copies");
            }
            if (book.getAvailableCopies() > 0) {
                book.setAvailableCopies(book.getAvailableCopies() - 1);
            }

        }

        User user = userRepository.findById(createLoanDTO.userId()).orElse(null);
        loan.setUser(user);
        loan.setBook(book);
        loan.setLoanDate(LocalDateTime.now());
        loan.setDueDate(LocalDateTime.now().plusDays(14));
        loan.setReturnDate(null);

        Loan saved = loanRepository.save(loan);
        UserDTO userDTO = userMapper.toDTO(saved.getUser());
        BookDTO bookDTO = bookMapper.toDTO(saved.getBook());

        return new LoanDTO(saved.getId(), userDTO, bookDTO, saved.getLoanDate(), saved.getDueDate(), saved.getReturnDate());
    }

    public LoanDTO extendLoan(Long loanId) {
        Loan loan = loanRepository.findById(loanId).orElse(null);
        if (loan == null) {
            return null;
        }
        loan.setDueDate(loan.getDueDate().plusDays(14));
        loanRepository.save(loan);
        return loanMapper.toDTO(loan);
    }

    public LoanDTO returnLoan(Long loanId) {
        Loan loan = loanRepository.findById(loanId).orElse(null);
        if (loan == null) {
            return null;
        }
        if (loan.getReturnDate() != null) {
            throw new LoanNotFoundException("Book has already been returned, book return date: " + loan.getReturnDate());
        }
        if (loan.getBook().getAvailableCopies() < loan.getBook().getTotalCopies()) {
            loan.getBook().setAvailableCopies(loan.getBook().getAvailableCopies() + 1);
        }

        loan.setReturnDate(LocalDateTime.now());
        Loan saved = loanRepository.save(loan);
        UserDTO userDTO = userMapper.toDTO(saved.getUser());
        BookDTO bookDTO = new BookDTO(saved.getBook().getId(), saved.getBook().getTitle(),
                saved.getBook().getPublicationYear(),
                saved.getBook().getAvailableCopies(),
                saved.getBook().getTotalCopies(),
                authorMapper.toDTO(saved.getBook().getAuthor()));

        return new LoanDTO(saved.getId(), userDTO, bookDTO, saved.getLoanDate(), saved.getDueDate(), saved.getReturnDate());
    }
}
