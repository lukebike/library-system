package com.storedemo.librarysystem.Controllers;

import br.com.fluentvalidator.context.ValidationResult;
import com.storedemo.librarysystem.DTOs.Author.AuthorDTO;
import com.storedemo.librarysystem.DTOs.Author.CreateAuthorDTO;

import com.storedemo.librarysystem.Services.AuthorService;
import com.storedemo.librarysystem.Validators.AuthorValidator;
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
}
