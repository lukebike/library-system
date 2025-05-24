package com.storedemo.librarysystem.Repositories;

import com.storedemo.librarysystem.Entities.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

   Optional<Book> findByTitleIgnoreCase(String title);

   List<Book> findByAuthorId(Long authorId);


   Page<Book> findAll(Pageable pageable);
}
