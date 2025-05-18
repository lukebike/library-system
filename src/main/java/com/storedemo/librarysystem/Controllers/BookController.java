package com.storedemo.librarysystem.Controllers;

import com.storedemo.librarysystem.DTOs.Book.BookDTO;
import com.storedemo.librarysystem.DTOs.Book.CreateBookDTO;
import com.storedemo.librarysystem.Services.BookService;
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
    public ResponseEntity<BookDTO> createBook(@RequestBody CreateBookDTO book){
        BookDTO createdBook = bookService.createBook(book);
        if(createdBook == null){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(createdBook, HttpStatus.CREATED);
    }
}
