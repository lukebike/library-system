package com.storedemo.librarysystem.Controllers;

import com.storedemo.librarysystem.DTOs.Author.AuthorDTO;
import com.storedemo.librarysystem.DTOs.Author.CreateAuthorDTO;

import com.storedemo.librarysystem.Services.AuthorService;
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
    public ResponseEntity<AuthorDTO> createAuthor(@RequestBody CreateAuthorDTO createAuthorDTO){
        AuthorDTO author = authorService.createAuthor(createAuthorDTO);
        return new ResponseEntity<>(author, HttpStatus.CREATED);
    }
}
