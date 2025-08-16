package com.storedemo.librarysystem.Services;

import com.storedemo.librarysystem.DTOs.Author.AuthorDTO;
import com.storedemo.librarysystem.DTOs.Book.BookDTO;
import com.storedemo.librarysystem.DTOs.Book.CreateBookDTO;
import com.storedemo.librarysystem.DTOs.Book.UpdateBookDTO;
import com.storedemo.librarysystem.DTOs.Mappers.AuthorMapper;
import com.storedemo.librarysystem.DTOs.Mappers.BookMapper;
import com.storedemo.librarysystem.Entities.Author;
import com.storedemo.librarysystem.Entities.Book;
import com.storedemo.librarysystem.ExceptionHandler.AuthorNotFoundException;
import com.storedemo.librarysystem.ExceptionHandler.BookNotFoundException;
import com.storedemo.librarysystem.Repositories.AuthorRepository;
import com.storedemo.librarysystem.Repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private AuthorRepository authorRepository;

    private final BookMapper bookMapper;

    private final AuthorMapper authorMapper;

    public BookService(BookRepository bookRepository, AuthorRepository authorRepository, BookMapper bookMapper, AuthorMapper authorMapper) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
        this.bookMapper = bookMapper;
        this.authorMapper = authorMapper;
    }

    public List<BookDTO> getAllBooks(int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<Book> bookPage = bookRepository.findAll(pageable);
        List<Book> books = bookPage.getContent();
        for(Book book : books) {
            if(book.getAuthor() != null){
                AuthorDTO authorDTO = authorMapper.toDTO(book.getAuthor());
                book.setAuthor(authorMapper.toEntity(authorDTO));
            }

        }
        return books.stream().map(bookMapper::toDTO).collect(Collectors.toList());
    }


    public List<BookDTO> getBooksByAuthorId(Long authorId) {
        List <Book> books = bookRepository.findByAuthorId(authorId).stream().toList();
        return books.stream().map(bookMapper::toDTO).collect(Collectors.toList());
    }

    public List<BookDTO> getAllBooksSortedByPublicationDate(int pageNumber, int pageSize, String param) {
        if(param.equals("ascending")) {
            Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by("publicationYear").ascending());
            Page<Book> bookPage = bookRepository.findAll(pageable);
            List<Book> books = bookPage.getContent();
            return books.stream().map(bookMapper::toDTO).collect(Collectors.toList());
        }
        if(param.equals("descending")) {
            Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by("publicationYear").descending());
            Page<Book> bookPage = bookRepository.findAll(pageable);
            List<Book> books = bookPage.getContent();
            return books.stream().map(bookMapper::toDTO).collect(Collectors.toList());
        }
        throw new BookNotFoundException("Invalid parameter, please enter one of the two options: Ascending or descending");
    }

    public BookDTO getBookByTitle(String title){
       Book book = bookRepository.findByTitleIgnoreCase(title).orElse(null);
       if(book == null){
           throw new BookNotFoundException("Book with title " + title + " not found, please enter a valid title");
       }
        AuthorDTO authorDTO = authorMapper.toDTO(book.getAuthor());
        return new BookDTO(book.getId(),
                book.getTitle(),
                book.getPublicationYear(),
                book.getAvailableCopies(),
                book.getTotalCopies(),
                authorDTO);
    }

    public Long getTotalBooksCount() {
        return bookRepository.count();
    }

    public BookDTO createBook(CreateBookDTO createBookDTO) {
        Author author = authorRepository.findById(createBookDTO.authorId()).orElseThrow(() -> new AuthorNotFoundException("Author not found, please enter a valid id"));
        Book book = new Book();
        book.setAuthor(author);
        book.setTitle(createBookDTO.title());
        book.setTotalCopies(createBookDTO.totalCopies());
        book.setAvailableCopies(createBookDTO.availableCopies());
        book.setPublicationYear(createBookDTO.publicationYear());
        Book saved = bookRepository.save(book);
        AuthorDTO authorDTO = authorMapper.toDTO(saved.getAuthor());
        return new BookDTO(saved.getId(), saved.getTitle(), saved.getPublicationYear(), saved.getAvailableCopies(), saved.getTotalCopies(), authorDTO);
    }

    public BookDTO updateBook(Long bookId, UpdateBookDTO updateBookDTO) {
        Book book = bookRepository.findById(bookId).orElseThrow(() -> new BookNotFoundException("Book with id " + bookId + " not found, please enter a valid id"));
        Author author = authorRepository.findById(updateBookDTO.authorId()).orElseThrow(() -> new AuthorNotFoundException("Author with id " + updateBookDTO.authorId() + " not found, please enter a valid id"));

        book.setTitle(updateBookDTO.title());
        book.setPublicationYear(updateBookDTO.publicationYear());
        book.setAvailableCopies(updateBookDTO.availableCopies());
        book.setTotalCopies(book.getTotalCopies() + updateBookDTO.availableCopies());
        book.setAuthor(author);

        Book updatedBook = bookRepository.save(book);
        AuthorDTO authorDTO = authorMapper.toDTO(updatedBook.getAuthor());

        return new BookDTO(updatedBook.getId(), updatedBook.getTitle(), updatedBook.getPublicationYear(), updatedBook.getAvailableCopies(), updatedBook.getTotalCopies(), authorDTO);
    }

    public boolean deleteBook(Long bookId){
        Book book = bookRepository.findById(bookId).orElse(null);
        if(book == null){
            return false;
        }
        bookRepository.delete(book);
        return true;
    }
}

