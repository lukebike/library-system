package com.storedemo.librarysystem.Services;

import com.storedemo.librarysystem.DTOs.Author.AuthorDTO;
import com.storedemo.librarysystem.DTOs.Author.CreateAuthorDTO;
import com.storedemo.librarysystem.DTOs.Author.UpdateAuthorDTO;
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

    private final AuthorRepository authorRepository;

    private final BookRepository bookRepository;

    private final AuthorMapper authorMapper;
    private final BookMapper bookMapper;

    @Autowired
    public AuthorService(
            AuthorRepository authorRepository,
            BookRepository bookRepository,
            AuthorMapper authorMapper,
            BookMapper bookMapper) {
        this.authorRepository = authorRepository;
        this.bookRepository = bookRepository;
        this.authorMapper = authorMapper;
        this.bookMapper = bookMapper;
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

    public AuthorDTO updateAuthor(Long id, UpdateAuthorDTO updateAuthorDTO) {
        Optional<Author> authorOptional = authorRepository.findById(id);
        if(authorOptional.isEmpty()){
            throw new AuthorNotFoundException("Author with the id: " + id + " not found");
        }
        Author author = authorOptional.get();
        author.setFirstName(updateAuthorDTO.firstName());
        author.setLastName(updateAuthorDTO.lastName());
        author.setNationality(updateAuthorDTO.nationality());
        Author savedAuthor = authorRepository.save(author);
        AuthorDTO authorDTO = authorMapper.toDTO(savedAuthor);
        return authorDTO;
    }
}
