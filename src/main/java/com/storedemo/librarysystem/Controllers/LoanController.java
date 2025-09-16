package com.storedemo.librarysystem.Controllers;


import br.com.fluentvalidator.context.ValidationResult;
import com.storedemo.librarysystem.DTOs.Loan.CreateLoanDTO;
import com.storedemo.librarysystem.DTOs.Loan.LoanDTO;
import com.storedemo.librarysystem.Services.LoanService;
import com.storedemo.librarysystem.Services.UserService;
import com.storedemo.librarysystem.Validators.Loans.LoanValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping ("/api/loans")
public class LoanController {

    @Autowired
    private LoanService loanService;

    @Autowired
    private LoanValidator loanValidator;

    @Autowired
    private UserService userService;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<List<LoanDTO>> getAllLoans(Authentication authentication) {
        boolean isAdmin = authentication.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
        if(!isAdmin){
            String userEmail = authentication.getName();
            Long userId = userService.getUserByEmail(userEmail).id();
            List<LoanDTO> loanDTOList = loanService.getLoansByUserId(userId);
            return ResponseEntity.ok(loanDTOList);
        }
        if(isAdmin){

            List<LoanDTO> loanDTOList = loanService.getAllLoans();
            if (loanDTOList.isEmpty()) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok(loanDTOList);
        }
        return ResponseEntity.status(403).build();
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @GetMapping("/{userId}")
    public ResponseEntity<List<LoanDTO>> getLoanByUserId(@PathVariable long userId, Authentication authentication) {
        List<LoanDTO> loanDTOList = loanService.getLoansByUserId(userId);
        if (loanDTOList == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(loanDTOList);
    }

    @PreAuthorize("hasRole('ADMIN')")
   @PostMapping
   public ResponseEntity<?> createLoan(@RequestBody CreateLoanDTO createLoanDTO) {
       ValidationResult validationResult = loanValidator.validate(createLoanDTO);
       if (!validationResult.isValid()) {
           return ResponseEntity.badRequest().body(validationResult.getErrors());
       }
        LoanDTO loanDTO = loanService.createLoan(createLoanDTO);
        return ResponseEntity.ok(loanDTO);
   }

   @PreAuthorize("hasRole('ADMIN')")
   @PutMapping("/{id}/extend")
   public ResponseEntity<LoanDTO> extendLoan(@PathVariable long id){
        LoanDTO loanDTO = loanService.extendLoan(id);
        if (loanDTO == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(loanDTO);
   }

    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
   @PutMapping("/{id}/return")
    public ResponseEntity<LoanDTO> returnLoan(@PathVariable long id, Authentication authentication){

        LoanDTO loanDTO = loanService.returnLoan(id);
        if (loanDTO == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(loanDTO);
   }

   @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteLoan(@PathVariable long id){
         boolean isDeleted = loanService.deleteLoan(id);
         if (!isDeleted) {
              return ResponseEntity.notFound().build();
         }
         return ResponseEntity.ok(true);
   }
}
