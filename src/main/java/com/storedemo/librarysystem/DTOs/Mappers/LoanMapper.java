package com.storedemo.librarysystem.DTOs.Mappers;

import com.storedemo.librarysystem.DTOs.Loan.LoanDTO;
import com.storedemo.librarysystem.Entities.Book;
import com.storedemo.librarysystem.Entities.Loan;
import com.storedemo.librarysystem.Entities.User;
import org.springframework.stereotype.Component;

@Component
public class LoanMapper {

    public LoanDTO toDTO(Loan loan) {
        return new LoanDTO(loan.getId(), null, null,
                loan.getLoanDate(),
                loan.getDueDate(),
                loan.getReturnDate());
    }

    public Loan toEntity(LoanDTO loanDTO) {
        Loan loan = new Loan();
        loan.setUser(new User());
        loan.setBook(new Book());
        loan.setLoanDate(loanDTO.borrowedDate());
        loan.setDueDate(loanDTO.dueDate());
        loan.setReturnDate(loanDTO.returnedDate());
        return loan;
    }
}
