package com.storedemo.librarysystem.DTOs.Mappers;

import com.storedemo.librarysystem.DTOs.Book.BookDTO;
import com.storedemo.librarysystem.Entities.Book;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class BookMapper {

    public BookDTO toDTO(Book book) {
        return new BookDTO(
                book.getId(),
                book.getTitle(),
                book.getPublicationYear(),
                book.getAvailableCopies(),
                book.getTotalCopies(),
                null);
    }


    public Book toEntity(BookDTO bookDTO) {
        Book book = new Book();
        book.setId(bookDTO.id());
        book.setTitle(bookDTO.title());
        book.setPublicationYear(bookDTO.publicationYear());
        book.setAvailableCopies(bookDTO.availableCopies());
        book.setTotalCopies(bookDTO.totalCopies());
        return book;
    }

    public List<Book> toEntityList(List<BookDTO> books) {

        List<Book> bookList = new ArrayList<>();
        for(BookDTO bookDTO : books) {
            bookList.add(this.toEntity(bookDTO));
        }
        return bookList;
    }

    public List<BookDTO> toDTOList(List<Book> books) {
        List<BookDTO> bookDTOList = new ArrayList<>();
        for(Book book : books) {
            bookDTOList.add(this.toDTO(book));
        }
        return bookDTOList;
    }



}
