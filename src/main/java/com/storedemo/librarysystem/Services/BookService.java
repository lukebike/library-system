package com.storedemo.librarysystem.Services;

import com.storedemo.librarysystem.DTOs.Author.AuthorDTO;
import com.storedemo.librarysystem.DTOs.Book.BookDTO;
import com.storedemo.librarysystem.DTOs.Book.CreateBookDTO;
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
import java.util.ArrayList;
import java.util.List;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private AuthorRepository authorRepository;

    private final BookMapper bookMapper;

    private final AuthorMapper authorMapper;

    public BookService() {
        bookMapper = new BookMapper();
        authorMapper = new AuthorMapper();
    }

    public List<BookDTO> getAllBooks(int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<Book> bookPage = bookRepository.findAll(pageable);
        List<Book> books = bookPage.getContent();
        List<BookDTO> bookDTOList = new ArrayList<>();
        return getBookDTOS(bookDTOList, books);
    }


    public List<BookDTO> getBooksByAuthorId(Long authorId) {
        List <BookDTO> bookDTOList = new ArrayList<>();
        List <Book> books = bookRepository.findByAuthorId(authorId).stream().toList();
        return getBookDTOS(bookDTOList, books);
    }

    public List<BookDTO> getAllBooksSortedByPublicationDate(int pageNumber, int pageSize, String param) {
        if(param.equals("ascending")) {
            Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by("publicationYear").ascending());
            Page<Book> bookPage = bookRepository.findAll(pageable);
            List<Book> books = bookPage.getContent();
            List<BookDTO> bookDTOList = new ArrayList<>();
            return getBookDTOS(bookDTOList, books);
        }
        if(param.equals("descending")) {
            Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by("publicationYear").descending());
            Page<Book> bookPage = bookRepository.findAll(pageable);
            List<Book> books = bookPage.getContent();
            List<BookDTO> bookDTOList = new ArrayList<>();
            return getBookDTOS(bookDTOList, books);
        }
        throw new BookNotFoundException("Invalid parameter, please enter one of the two options: Ascending or descending");
    }


    private List<BookDTO> getBookDTOS(List<BookDTO> bookDTOList, List<Book> books) {
        for(Book book : books) {
            AuthorDTO authorDTO = authorMapper.toDTO(book.getAuthor());
            BookDTO bookDTO = new BookDTO(book.getId(),
                    book.getTitle(),
                    book.getPublicationYear(),
                    book.getAvailableCopies(),
                    book.getTotalCopies(),
                    authorDTO);
            bookDTOList.add(bookDTO);
        }
        return bookDTOList;
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
}

