package com.storedemo.librarysystem.DTOs.Loan;

public record CreateLoanDTO(
        Long bookId,
        Long userId
) {
}
