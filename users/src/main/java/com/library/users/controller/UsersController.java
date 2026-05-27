package com.library.users.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.library.users.client.BookClient;
import com.library.users.dto.ApiResponse;
import com.library.users.dto.BookDto;
import com.library.users.entity.User;
import com.library.users.repository.UserRepository;


@RestController
@RequestMapping("/api/users")
public class UsersController {

    final BookClient bookClient;
    final UserRepository userRepository;

    public UsersController(BookClient bookClient, UserRepository userRepository) {
        this.bookClient = bookClient;
        this.userRepository = userRepository;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<User>>> getAllUsers() {
        List<User> users = userRepository.findAll();
        ApiResponse<List<User>> response = new ApiResponse<>(true, users);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/books")
    public ResponseEntity<ApiResponse<List<BookDto>>> getAllBooks(
        @RequestParam(required = false) String category,
        @RequestParam(required = false) String author,
        @RequestParam(required = false) String title,
        @RequestParam(required = false) String isbn) {

        List<BookDto> books;
        boolean hasFilters = (category != null && !category.isBlank()) || (author != null && !author.isBlank())
                || (title != null && !title.isBlank()) || (isbn != null && !isbn.isBlank());
        if (hasFilters) {
            books = bookClient.searchBooks(title, author, category, isbn);
        } else {
            books = bookClient.getAllBooks();
        }
        ApiResponse<List<BookDto>> response = new ApiResponse<>(true, books);
        return ResponseEntity.ok(response);

    }
}
