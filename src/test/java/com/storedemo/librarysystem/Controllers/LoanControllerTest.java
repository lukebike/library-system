package com.storedemo.librarysystem.Controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.storedemo.librarysystem.DTOs.Loan.CreateLoanDTO;
import com.storedemo.librarysystem.Entities.Book;
import com.storedemo.librarysystem.Entities.Loan;
import com.storedemo.librarysystem.Entities.User;
import com.storedemo.librarysystem.Repositories.BookRepository;
import com.storedemo.librarysystem.Repositories.LoanRepository;
import com.storedemo.librarysystem.Repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
public class LoanControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private LoanRepository loanRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void loanControllerCreateLoan_shouldReturnResponseEntityLoanDTO(){
        User user = new User(null, "Test1", "User", "1234@gmail.com", "test@gmail.com", LocalDateTime.now(), null);
        user = userRepository.save(user);

        Book book = new Book(null, "Test Book", 2020, 3, 3, null );
        book = bookRepository.save(book);

        CreateLoanDTO createLoanDTO = new CreateLoanDTO(book.getId(), user.getId());

        try {
            mockMvc.perform(post("/loans")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(createLoanDTO)))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.user.id").value(user.getId()))
                    .andExpect(jsonPath("$.book.id").value(book.getId()))
                    .andExpect(jsonPath("$.borrowedDate").isNotEmpty())
                    .andExpect(jsonPath("$.dueDate").isNotEmpty());

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void loanControllerCreateLoan_shouldReturnBadRequestWhenInvalidRequest() throws Exception {
        CreateLoanDTO createLoanDTO = new CreateLoanDTO(null, null);

        mockMvc.perform(post("/loans")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createLoanDTO)))
                .andExpect(status().isBadRequest());
    }
}
