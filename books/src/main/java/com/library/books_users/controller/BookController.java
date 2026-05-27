package com.library.books_users.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.library.books_users.dto.BookDto;
import com.library.books_users.service.BookService;


@RestController
@RequestMapping("/api/books")
public class BookController {

    final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }
    
    @GetMapping
    public ResponseEntity<?> getAllBooks(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String author,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String isbn
    ) {
        Map<String, Object> response = new HashMap<>();
        var books = bookService.searchBooks(title, author, category, isbn);
        
        response.put("mensaje", "Libros obtenidos");
        response.put("libros", books);
        response.put("total", books.size());
        
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookDto> getBookById(@PathVariable UUID id) {
        BookDto bookDto = bookService.getBookById(id);
        return ResponseEntity.ok(bookDto);
    }
    

    @PostMapping
    public ResponseEntity<?> createBook(@RequestBody BookDto bookDto) {
        BookDto createdBook = bookService.createBook(bookDto);
        
        Map<String, Object> response = new HashMap<>();
        response.put("mensaje", "Libro creado exitosamente");
        response.put("data", createdBook);
        
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateBook(@PathVariable UUID id, @RequestBody BookDto bookDto) {
        BookDto updatedBook = bookService.updateBook(id, bookDto);
        
        Map<String, Object> response = new HashMap<>();
        response.put("mensaje", "Libro actualizado correctamente");
        response.put("data", updatedBook);
        
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteBook(@PathVariable UUID id) {
        bookService.deleteBook(id);
        
        Map<String, Object> response = new HashMap<>();
        response.put("mensaje", "Libro eliminado exitosamente");
        response.put("id", id);
        
        return ResponseEntity.ok(response);
    }
}
