package com.storedemo.librarysystem.DTOs.Mappers;

import com.storedemo.librarysystem.DTOs.Author.AuthorDTO;
import com.storedemo.librarysystem.DTOs.Book.BookDTO;
import com.storedemo.librarysystem.Entities.Author;
import com.storedemo.librarysystem.Entities.Book;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class AuthorMapper {

    public AuthorMapper() {
    }

    public AuthorDTO toDTO(Author author) {
        return new AuthorDTO(
                author.getId(),
                author.getFirstName(),
                author.getLastName(),
                author.getNationality(),
                new ArrayList<BookDTO>()
        );
    }

    public Author toEntity(AuthorDTO AuthorDTO) {
        Author author = new Author();
        author.setId(AuthorDTO.id());
        author.setFirstName(AuthorDTO.firstName());
        author.setLastName(AuthorDTO.lastName());
        author.setNationality(AuthorDTO.nationality());
        author.setBooks(new ArrayList<Book>());
        return author;
    }
}
