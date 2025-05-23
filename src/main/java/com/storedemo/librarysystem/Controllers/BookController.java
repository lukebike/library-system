package com.storedemo.librarysystem.Controllers;

import br.com.fluentvalidator.context.ValidationResult;
import com.storedemo.librarysystem.DTOs.Book.BookDTO;
import com.storedemo.librarysystem.DTOs.Book.CreateBookDTO;
import com.storedemo.librarysystem.Services.BookService;
import com.storedemo.librarysystem.Validators.BookValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController {

    @Autowired
    private BookService bookService;

    @Autowired
    private BookValidator bookValidator;

    public BookController() {

    }

    @GetMapping()
    public ResponseEntity<List<BookDTO>> getBooks(){
        List<BookDTO> books = bookService.getAllBooks();
        if(books == null){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        else {
            return new ResponseEntity<>(books, HttpStatus.OK);
        }
    }

    @GetMapping("/search")
    public ResponseEntity<BookDTO> getBookByTitle(@RequestParam String title){
        BookDTO book = bookService.getBookByTitle(title);
        if(book == null){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(book, HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<?> createBook(@RequestBody CreateBookDTO book){
        try {
            ValidationResult validationResult = bookValidator.validate(book);
            if (!validationResult.isValid()) {
                return ResponseEntity.badRequest().body(validationResult.getErrors());
            }
            BookDTO createdBook = bookService.createBook(book);
            return new ResponseEntity<>(createdBook, HttpStatus.CREATED);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        }
    }
}
