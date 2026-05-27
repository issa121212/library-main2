package com.library.books_users.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.library.books_users.dto.BookDto;
import com.library.books_users.entity.Book;
import com.library.books_users.repository.BookRepository;

@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public BookDto getBookById(UUID id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Book not found with id: " + id));
        return mapToDto(book);
    }

    @Override
    public BookDto createBook(BookDto bookDto) {
        Book book = mapToEntity(bookDto);
        Book savedBook = bookRepository.save(book);
        return mapToDto(savedBook);
    }


    @Override
    public BookDto updateBook(UUID id, BookDto bookDto) {
        Book existingBook = bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Book not found with id: " + id));
        existingBook.setTitle(bookDto.title());
        existingBook.setAuthor(bookDto.author());
        existingBook.setCategory(bookDto.category());
        existingBook.setIsbn(bookDto.isbn());
        Book updatedBook = bookRepository.save(existingBook);
        return mapToDto(updatedBook);
    }

    @Override
    public void deleteBook(UUID id) {
        Book existingBook = bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Book not found with id: " + id));
        bookRepository.delete(existingBook);
    }

    @Override
    public List<BookDto> getAllBooks() {
        List<Book> books = bookRepository.findAll();
        return books.stream().map(this::mapToDto).toList();

    }

    private BookDto mapToDto(Book book) {
        return new BookDto(book.getId(), book.getTitle(), book.getAuthor(), book.getCategory(), book.getIsbn()
        );
    }

    private Book mapToEntity(BookDto dto) {
        Book book = new Book();
        book .setId(dto.id());
        book.setTitle(dto.title());
        book.setAuthor(dto.author());
        book.setCategory(dto.category());
        book.setIsbn(dto.isbn());
        return book;
    }

    @Override
    public List<BookDto> searchBooks(String title, String author, String category, String isbn) {
    boolean hasFilters = (title != null && !title.isBlank()) ||
                        (author != null && !author.isBlank()) ||
                        (category != null && !category.isBlank()) ||
                        (isbn != null && !isbn.isBlank());
    
    if (!hasFilters) {
        return getAllBooks();
    }
    return bookRepository.searchBooks(title, author, category, isbn)
            .stream()
            .map(this::mapToDto)
            .toList();
    }

}
