package com.library.books_users.service;

import java.util.List;
import java.util.UUID;

import com.library.books_users.dto.BookDto;

public interface BookService {
    
    List<BookDto> searchBooks(String title, String author, String category, String isbn);

    BookDto createBook(BookDto bookDto);

    BookDto getBookById(UUID id);

    BookDto updateBook(UUID id, BookDto bookDto);

    void deleteBook(UUID id);

    List<BookDto> getAllBooks();
} 
