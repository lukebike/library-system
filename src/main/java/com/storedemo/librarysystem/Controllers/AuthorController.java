package com.storedemo.librarysystem.Controllers;

import br.com.fluentvalidator.context.ValidationResult;
import com.storedemo.librarysystem.DTOs.Author.AuthorDTO;
import com.storedemo.librarysystem.DTOs.Author.CreateAuthorDTO;

import com.storedemo.librarysystem.DTOs.Author.UpdateAuthorDTO;
import com.storedemo.librarysystem.Services.AuthorService;
import com.storedemo.librarysystem.Validators.Authors.AuthorValidator;
import com.storedemo.librarysystem.Validators.Authors.UpdateAuthorValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/authors")
public class AuthorController {

    @Autowired
    private AuthorService authorService;

    @Autowired
    private AuthorValidator authorValidator;

    @Autowired
    private UpdateAuthorValidator updateAuthorValidator;

    @GetMapping()
    public ResponseEntity<List<AuthorDTO>> getAuthors(){
        return ResponseEntity.ok().body((authorService.getAllAuthors()));
    }

    @GetMapping("/name/{lastname}")
    public ResponseEntity<AuthorDTO> getAuthorByLastName(@PathVariable String lastname){
        AuthorDTO author = authorService.getAuthorByLastName(lastname);
        if(author == null){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(author, HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<?> createAuthor(@RequestBody CreateAuthorDTO createAuthorDTO){
        ValidationResult validationResult = authorValidator.validate(createAuthorDTO);
        if(!validationResult.isValid()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(validationResult.getErrors());
        }
        AuthorDTO author = authorService.createAuthor(createAuthorDTO);
        return new ResponseEntity<>(author, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateAuthor(@PathVariable Long id, @RequestBody UpdateAuthorDTO updateAuthorDTO){
        ValidationResult validationResult = updateAuthorValidator.validate(updateAuthorDTO);
        if(!validationResult.isValid()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(validationResult.getErrors());
        }
        AuthorDTO updatedAuthor = authorService.updateAuthor(id, updateAuthorDTO);
        if (updatedAuthor == null) {
            return new ResponseEntity<>("Author not found", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(updatedAuthor, HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteAuthor(@PathVariable Long id) {
        boolean isDeleted = authorService.deleteAuthor(id);
        if (isDeleted) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>("Author not found", HttpStatus.NOT_FOUND);
        }
    }

}
