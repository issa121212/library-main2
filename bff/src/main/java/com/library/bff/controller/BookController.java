package com.library.bff.controller;

import com.library.bff.dto.ApiResponse;
import com.library.bff.dto.BookDto;
import com.library.bff.service.GatewayService;
import java.util.List;
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

@RestController
@RequestMapping("/api/books")
public class BookController {

	private final GatewayService gatewayService;

	public BookController(GatewayService gatewayService) {
		this.gatewayService = gatewayService;
	}

	@GetMapping
	public ResponseEntity<ApiResponse<List<BookDto>>> getBooks(
			@RequestParam(required = false) String title,
			@RequestParam(required = false) String author,
			@RequestParam(required = false) String category,
			@RequestParam(required = false) String isbn) {

		List<BookDto> books = gatewayService.getBooks(title, author, category, isbn);
		return ResponseEntity.ok(new ApiResponse<>(true, books));
	}

	@GetMapping("/{id}")
	public ResponseEntity<ApiResponse<BookDto>> getBookById(@PathVariable UUID id) {
		return ResponseEntity.ok(new ApiResponse<>(true, gatewayService.getBookById(id)));
	}

	@PostMapping
	public ResponseEntity<ApiResponse<BookDto>> createBook(@RequestBody BookDto bookDto) {
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(new ApiResponse<>(true, gatewayService.createBook(bookDto)));
	}

	@PutMapping("/{id}")
	public ResponseEntity<ApiResponse<BookDto>> updateBook(@PathVariable UUID id, @RequestBody BookDto bookDto) {
		return ResponseEntity.ok(new ApiResponse<>(true, gatewayService.updateBook(id, bookDto)));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<ApiResponse<UUID>> deleteBook(@PathVariable UUID id) {
		gatewayService.deleteBook(id);
		return ResponseEntity.ok(new ApiResponse<>(true, id));
	}
}
