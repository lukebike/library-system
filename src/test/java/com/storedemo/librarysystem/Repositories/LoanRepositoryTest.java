package com.storedemo.librarysystem.Repositories;

import com.storedemo.librarysystem.Entities.Loan;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class LoanRepositoryTest {

    @Autowired
    private LoanRepository loanRepository;

    @Test
    public void findLoansByUserId_shouldReturnListOfLoans() {
        // Arrange
        Long userId = 1L;
        Long expectedLoanSize = 6L;
        // Act
        Optional<List<Loan>> loansList = loanRepository.findByUserId(userId);
        // Assert
        Assertions.assertTrue(loansList.isPresent());
        Assertions.assertEquals(expectedLoanSize, loansList.get().size());
    }
}
