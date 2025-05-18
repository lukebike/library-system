package com.storedemo.librarysystem.Repositories;

import com.storedemo.librarysystem.Entities.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {

        Optional<Author> findByLastNameIgnoreCase(String lastName);
}
