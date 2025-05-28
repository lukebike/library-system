package com.storedemo.librarysystem.Repositories;

import com.storedemo.librarysystem.Entities.Book;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class BookRepositoryTest {

    @Autowired
    private BookRepository bookRepository;

    @Test
    public void findBookByTitleIgnoreCaseReturnsBook(){
        // Arrange
        String bookTitle = "pippi longstocking";
        // Act
        Optional<Book> book = bookRepository.findByTitleIgnoreCase(bookTitle);
        // Assert
        Assertions.assertTrue(book.isPresent());
        Assertions.assertTrue(book.get().getTitle().toLowerCase().contains(bookTitle));

    }

    @Test
    public void findBooksByAuthorIdReturnsBook(){
        // Arrange
        Long authorId = 1L;
        // Act
        List<Book> books = bookRepository.findByAuthorId(authorId);
        // Assert
        Assertions.assertFalse(books.isEmpty());
    }

    @Test
    public void findBooksByPageReturnsBook(){
        // Arrange
        int pageNumber = 1;
        int pageSize = 5;
        // Act
        Page<Book> books = bookRepository.findAll(PageRequest.of(pageNumber, pageSize));
        // Assert
        Assertions.assertFalse(books.isEmpty());
        Assertions.assertEquals(pageSize, books.getSize());
    }
}
