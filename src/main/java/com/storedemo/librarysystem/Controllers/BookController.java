package com.storedemo.librarysystem.Controllers;

import br.com.fluentvalidator.context.ValidationResult;
import com.storedemo.librarysystem.DTOs.Book.BookDTO;
import com.storedemo.librarysystem.DTOs.Book.CreateBookDTO;
import com.storedemo.librarysystem.DTOs.Book.PagedBooksResponse;
import com.storedemo.librarysystem.ExceptionHandler.BookNotFoundException;
import com.storedemo.librarysystem.Services.BookService;
import com.storedemo.librarysystem.Validators.Books.BookValidator;
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
    public ResponseEntity<PagedBooksResponse> getBooks(@RequestParam int pageNumber, @RequestParam int pageSize) {
        List<BookDTO> books = bookService.getAllBooks(pageNumber, pageSize);
        if(books.isEmpty()) {
            throw new BookNotFoundException("Books not found, please enter a valid page or page size");
        }
        long totalCount = bookService.getTotalBooksCount();
        PagedBooksResponse response = new PagedBooksResponse(books, totalCount);
            return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/publication")
    public ResponseEntity<List<BookDTO>> getBooksByPublicationYear(@RequestParam int pageNumber, @RequestParam int pageSize, @RequestParam String param) {
        if(param == null || param.isEmpty()) {
            throw new BookNotFoundException("Books not found, please enter a valid parameter for publication year");
        }
        List<BookDTO> books = bookService.getAllBooksSortedByPublicationDate(pageNumber, pageSize, param);
        if(books.isEmpty()) {
            throw new BookNotFoundException("Books not found, please enter a valid page or page size or parameter.");
        } else {
            return new ResponseEntity<>(books, HttpStatus.OK);
        }
    }

    @GetMapping("/search")
    public ResponseEntity<BookDTO> getBookByTitle(@RequestParam String title){
        BookDTO book = bookService.getBookByTitle(title);
        if(book == null){
            throw new BookNotFoundException("Book not found, please enter a valid title");
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
