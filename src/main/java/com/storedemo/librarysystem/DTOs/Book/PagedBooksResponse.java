package com.storedemo.librarysystem.DTOs.Book;

import java.util.List;

public record PagedBooksResponse(
        List<BookDTO> books,
        Long totalCount
) {
}
