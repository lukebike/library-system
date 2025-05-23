package com.storedemo.librarysystem.Controllers;


import br.com.fluentvalidator.context.ValidationResult;
import com.storedemo.librarysystem.DTOs.Loan.CreateLoanDTO;
import com.storedemo.librarysystem.DTOs.Loan.LoanDTO;
import com.storedemo.librarysystem.Services.LoanService;
import com.storedemo.librarysystem.Validators.LoanValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping ("/loans")
public class LoanController {

    @Autowired
    private LoanService loanService;

    @Autowired
    private LoanValidator loanValidator;

    @GetMapping("/{userId}")
    public ResponseEntity<List<LoanDTO>> getLoanByUserId(@PathVariable long userId) {
        List<LoanDTO> loanDTOList = loanService.getLoansByUserId(userId);
        if (loanDTOList == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(loanDTOList);
    }

   @PostMapping
   public ResponseEntity<?> createLoan(@RequestBody CreateLoanDTO createLoanDTO) {
       ValidationResult validationResult = loanValidator.validate(createLoanDTO);
       if (!validationResult.isValid()) {
           return ResponseEntity.badRequest().body(validationResult.getErrors());
       }
        LoanDTO loanDTO = loanService.createLoan(createLoanDTO);
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

   @PutMapping("/{id}/return")
    public ResponseEntity<LoanDTO> returnLoan(@PathVariable long id){
        LoanDTO loanDTO = loanService.returnLoan(id);
        if (loanDTO == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(loanDTO);
   }
}
