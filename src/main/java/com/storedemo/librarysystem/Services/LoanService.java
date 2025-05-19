package com.storedemo.librarysystem.Services;

import com.storedemo.librarysystem.DTOs.Book.BookDTO;
import com.storedemo.librarysystem.DTOs.Loan.LoanDTO;
import com.storedemo.librarysystem.DTOs.Mappers.AuthorMapper;
import com.storedemo.librarysystem.DTOs.Mappers.BookMapper;
import com.storedemo.librarysystem.DTOs.Mappers.LoanMapper;
import com.storedemo.librarysystem.DTOs.Mappers.UserMapper;
import com.storedemo.librarysystem.DTOs.User.UserDTO;
import com.storedemo.librarysystem.Entities.Loan;
import com.storedemo.librarysystem.Repositories.LoanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class LoanService {

    @Autowired
    private LoanRepository loanRepository;

    private LoanMapper loanMapper;
    private UserMapper userMapper;
    private BookMapper bookMapper;

    public LoanService() {
        this.loanMapper = new LoanMapper();
        this.userMapper = new UserMapper();
        this.bookMapper = new BookMapper();
    }

    public List<LoanDTO> getLoansByUserId(long userId) {
        Optional<List<Loan>> loans = loanRepository.findByUserId(userId);
        List<LoanDTO> loanDTOs = new ArrayList<>();
        for (Loan loan : loans.get()) {
            UserDTO userDTO = userMapper.toDTO(loan.getUser());
            BookDTO bookDTO = bookMapper.toDTO(loan.getBook());
            LoanDTO loanDTO = new LoanDTO(userDTO, bookDTO, loan.getLoanDate(), loan.getDueDate(), loan.getReturnDate());
            loanDTOs.add(loanDTO);
        }
        return loanDTOs;
    }
}
