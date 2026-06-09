package com.library.bff.client;

import com.library.bff.dto.BookDto;
import java.util.List;
import java.util.UUID;

public interface BookClient {

	List<BookDto> getBooks(String title, String author, String category, String isbn);

	BookDto getBookById(UUID id);

	BookDto createBook(BookDto bookDto);

	BookDto updateBook(UUID id, BookDto bookDto);

	void deleteBook(UUID id);
}
