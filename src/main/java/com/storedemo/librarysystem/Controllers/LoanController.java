package com.storedemo.librarysystem.Controllers;


import com.storedemo.librarysystem.DTOs.Loan.CreateLoanDTO;
import com.storedemo.librarysystem.DTOs.Loan.LoanDTO;
import com.storedemo.librarysystem.Services.LoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping ("/loans")
public class LoanController {

    @Autowired
    private LoanService loanService;

    @GetMapping("/{userId}")
    public ResponseEntity<List<LoanDTO>> getLoanByUserId(@PathVariable long userId) {
        List<LoanDTO> loanDTOList = loanService.getLoansByUserId(userId);
        if (loanDTOList == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(loanDTOList);
    }

   @PostMapping
   public ResponseEntity<LoanDTO> createLoan(@RequestBody CreateLoanDTO createLoanDTO) {
        LoanDTO loanDTO = loanService.createLoan(createLoanDTO);
        if (loanDTO == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(loanDTO);
   }

   @PutMapping("/{id}/extend")
   public ResponseEntity<LoanDTO> extendLoan(@PathVariable long id){
        LoanDTO loanDTO = loanService.extendLoan(id);
        if (loanDTO == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(loanDTO);
   }

}
