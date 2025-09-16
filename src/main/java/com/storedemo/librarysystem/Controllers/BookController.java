package com.storedemo.librarysystem.Controllers;

import br.com.fluentvalidator.context.ValidationResult;
import com.storedemo.librarysystem.DTOs.Book.BookDTO;
import com.storedemo.librarysystem.DTOs.Book.CreateBookDTO;
import com.storedemo.librarysystem.DTOs.Book.PagedBooksResponse;
import com.storedemo.librarysystem.DTOs.Book.UpdateBookDTO;
import com.storedemo.librarysystem.ExceptionHandler.BookNotFoundException;
import com.storedemo.librarysystem.Services.BookService;
import com.storedemo.librarysystem.Validators.Books.BookValidator;
import com.storedemo.librarysystem.Validators.Books.UpdateBookValidator;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BookController {

    private final BookService bookService;
    private final BookValidator bookValidator;
    private final UpdateBookValidator updateBookValidator;

    public BookController(BookService bookService, BookValidator bookValidator, UpdateBookValidator updateBookValidator) {
        this.bookService = bookService;
        this.bookValidator = bookValidator;
        this.updateBookValidator = updateBookValidator;
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

    @PreAuthorize("hasRole('ADMIN')")
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

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<?> updateBook(@PathVariable long id, @RequestBody UpdateBookDTO book) {
        try {
            ValidationResult validationResult = updateBookValidator.validate(book);
            if (!validationResult.isValid()) {
                return ResponseEntity.badRequest().body(validationResult.getErrors());
            }
            BookDTO updatedBook = bookService.updateBook(id, book);
            return new ResponseEntity<>(updatedBook, HttpStatus.OK);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteBook(@PathVariable long id) {
        boolean deletedBook = bookService.deleteBook(id);
        if(deletedBook) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
