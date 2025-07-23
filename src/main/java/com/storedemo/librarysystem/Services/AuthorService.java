package com.storedemo.librarysystem.Services;

import com.storedemo.librarysystem.DTOs.Author.AuthorDTO;
import com.storedemo.librarysystem.DTOs.Author.CreateAuthorDTO;
import com.storedemo.librarysystem.DTOs.Book.BookDTO;
import com.storedemo.librarysystem.DTOs.Mappers.AuthorMapper;
import com.storedemo.librarysystem.DTOs.Mappers.BookMapper;
import com.storedemo.librarysystem.Entities.Author;
import com.storedemo.librarysystem.Entities.Book;
import com.storedemo.librarysystem.ExceptionHandler.AuthorNotFoundException;
import com.storedemo.librarysystem.Repositories.AuthorRepository;
import com.storedemo.librarysystem.Repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AuthorService {

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private BookRepository bookRepository;

    private final AuthorMapper authorMapper;
    private final BookMapper bookMapper;

    public AuthorService() {
        this.authorMapper = new AuthorMapper();
        this.bookMapper = new BookMapper();
    }



    public AuthorDTO createAuthor(CreateAuthorDTO createAuthorDTO){
        Author author = new Author();
        author.setFirstName(createAuthorDTO.firstName());
        author.setLastName(createAuthorDTO.lastName());
        author.setNationality(createAuthorDTO.nationality());
        author.setBooks(null);
        Author saved = authorRepository.save(author);
        return authorMapper.toDTO(saved);
    }

    public List<AuthorDTO> getAllAuthors(){
        List<Author> authors = authorRepository.findAll();
        List<AuthorDTO> authorDTOs = new ArrayList<>();
        for(Author author : authors){
            AuthorDTO authorDTO = authorMapper.toDTO(author);
            if(author.getBooks() != null){
                List<BookDTO> books = bookMapper.toDTOList(author.getBooks());
                authorDTO.books().addAll(books);
            }
            authorDTOs.add(authorDTO);
        }

        return authorDTOs;
    }

    public AuthorDTO getAuthorByLastName(String lastName){
        Optional<Author> author = authorRepository.findByLastNameIgnoreCase(lastName);
        if(author.isEmpty()){
            throw new AuthorNotFoundException("Author with the last name: " + lastName + " not found");
        }
        AuthorDTO authorDTO = authorMapper.toDTO(author.get());
        if(author.get().getBooks() != null){
            List<BookDTO> books = bookMapper.toDTOList(author.get().getBooks());
            authorDTO.books().addAll(books);
        }
        return authorDTO;
    }

    public AuthorDTO findAuthorById(Long id) {
        Optional<Author> author = authorRepository.findById(id);
        if (author.isEmpty()) {
            throw new AuthorNotFoundException("Author with the id: " + id + " not found");
        }
        AuthorDTO authorDTO = authorMapper.toDTO(author.get());
        if (author.get().getBooks() != null) {
            List<BookDTO> books = bookMapper.toDTOList(author.get().getBooks());
            authorDTO.books().addAll(books);
        }
        return authorDTO;
    }

    public boolean deleteAuthor(Long id) {
        Optional<Author> authorOptional = authorRepository.findById(id);
        if(authorOptional.isEmpty()){
            throw new AuthorNotFoundException("Author with the id: " + id + " not found");
        }
        Author author = authorOptional.get();
        if(author.getBooks() != null){
            for(Book book : author.getBooks()) {
                book.setAuthor(null);
                bookRepository.save(book);
            }
        }
        authorRepository.delete(author);
        return true;
    }
}
