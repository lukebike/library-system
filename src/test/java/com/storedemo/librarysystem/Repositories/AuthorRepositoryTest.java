package com.storedemo.librarysystem.Repositories;

import com.storedemo.librarysystem.Entities.Author;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class AuthorRepositoryTest {

    @Autowired
    private AuthorRepository authorRepository;

    @Test
    public void findAuthorByLastNameIgnoreCase_shouldReturnAuthor() {
        // Arrange
        String lastName = "kafka";
        // Act
        Optional<Author> author = authorRepository.findByLastNameIgnoreCase(lastName);
        // Assert
        Assertions.assertTrue(author.isPresent());
        Assertions.assertTrue(author.get().getLastName().equalsIgnoreCase(lastName));
    }
}
