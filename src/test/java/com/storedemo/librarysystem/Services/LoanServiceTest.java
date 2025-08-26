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
import com.storedemo.librarysystem.Repositories.BookRepository;
import com.storedemo.librarysystem.Repositories.LoanRepository;
import com.storedemo.librarysystem.Repositories.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.time.LocalDateTime;
import java.util.Optional;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ExtendWith(MockitoExtension.class)
public class LoanServiceTest {

    @Autowired
    private LoanService loanServiceMock;

    @MockitoBean
    private LoanRepository loanRepositoryMock;

    @MockitoBean
    private BookRepository bookRepositoryMock;

    @MockitoBean
    private UserRepository userRepositoryMock;

    @MockitoBean
    private LoanMapper loanMapperMock;

    @MockitoBean
    private UserMapper userMapperMock;

    @MockitoBean
    private BookMapper bookMapperMock;

    @MockitoBean
    private AuthorMapper authorMapperMock;


    @Test
    public void createLoanShouldReturnCreatedLoan(){
        // Arrange
        Long userId = 2L;
        Long bookId = 12L;
        CreateLoanDTO createLoanDTO = new CreateLoanDTO(userId, bookId);


        User user = new User(userId, "John", "Doe", "password", "dog@gmail.com", LocalDateTime.now(), null,null);
        Mockito.when(userRepositoryMock.findById(userId)).thenReturn(Optional.of(user));

        Book book = new Book(bookId, "New Book", 2020, 2, 3,
                null);
        Mockito.when(bookRepositoryMock.findById(bookId)).thenReturn(Optional.of(book));

        Loan loan = new Loan();
        loan.setId(1L);
        loan.setUser(user);
        loan.setBook(book);
        loan.setLoanDate(LocalDateTime.now());
        loan.setDueDate(LocalDateTime.now().plusDays(14));
        loan.setReturnDate(null);
            UserDTO userDTO = new UserDTO(user.getId() ,
                    user.getFirstName(),
                    user.getLastName(),
                    user.getEmail(),
                    user.getRegistrationDate());

        BookDTO bookDTO = new BookDTO(book.getId(),
                book.getTitle(),
                book.getPublicationYear(),
                book.getAvailableCopies(),
                book.getTotalCopies(),
                authorMapperMock.toDTO(book.getAuthor()));

        LoanDTO loanDTO = new LoanDTO(loan.getId(), userDTO, bookDTO, loan.getLoanDate(), loan.getDueDate(), null);
        // Act
        Mockito.when(loanRepositoryMock.save(Mockito.any(Loan.class))).thenAnswer(invocation -> {
            Loan newLoan = invocation.getArgument(0);
            newLoan.setId(1L);
            newLoan.setUser(user);
            book.setAvailableCopies(book.getAvailableCopies() - 1);
            newLoan.setBook(book);
            newLoan.setLoanDate(LocalDateTime.now());
            newLoan.setDueDate(LocalDateTime.now().plusDays(14));
            newLoan.setReturnDate(null);

            return newLoan;
        });
        Mockito.when(userMapperMock.toDTO(user)).thenReturn(userDTO);
        Mockito.when(bookMapperMock.toDTO(book)).thenReturn(bookDTO);

       LoanDTO createdLoan = loanServiceMock.createLoan(createLoanDTO);
        // Assert
       Assertions.assertNotNull(createdLoan);
       Assertions.assertEquals(userDTO, createdLoan.user());
       Assertions.assertEquals(bookDTO, createdLoan.book());
       Assertions.assertNotNull(createdLoan.borrowedDate());
       Assertions.assertTrue(createdLoan.dueDate().isAfter(createdLoan.borrowedDate()));
       Assertions.assertEquals(1, book.getAvailableCopies());
       Mockito.verify(loanRepositoryMock).save(Mockito.any(Loan.class));
    }
}
