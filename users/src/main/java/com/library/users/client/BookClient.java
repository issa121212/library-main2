package com.library.users.client;

import java.util.List;
import java.util.UUID;

import com.library.users.dto.BookDto;

public interface BookClient {


    List<BookDto> getAllBooks();

    List<BookDto> searchBooks(String title, String author, String category, String isbn);

    BookDto getBookById(UUID id);

    BookDto createBook(BookDto bookDto);

    BookDto updateBook(UUID bookId, BookDto bookDto);
    
    void deleteBook(UUID id);

}
