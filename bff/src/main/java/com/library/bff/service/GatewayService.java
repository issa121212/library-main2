package com.library.bff.service;

import com.library.bff.client.AuthClient;
import com.library.bff.client.BookClient;
import com.library.bff.dto.ApiResponse;
import com.library.bff.dto.AuthResponse;
import com.library.bff.dto.BookDto;
import com.library.bff.dto.LoginRequest;
import com.library.bff.dto.RegisterRequest;
import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Service;

@Service
public class GatewayService {

	private final AuthClient authClient;
	private final BookClient bookClient;

	public GatewayService(AuthClient authClient, BookClient bookClient) {
		this.authClient = authClient;
		this.bookClient = bookClient;
	}

	public ApiResponse<AuthResponse> register(RegisterRequest request) {
		return authClient.register(request);
	}

	public ApiResponse<AuthResponse> login(LoginRequest request) {
		return authClient.login(request);
	}

	public List<BookDto> getBooks(String title, String author, String category, String isbn) {
		return bookClient.getBooks(title, author, category, isbn);
	}

	public BookDto getBookById(UUID id) {
		return bookClient.getBookById(id);
	}

	public BookDto createBook(BookDto bookDto) {
		return bookClient.createBook(bookDto);
	}

	public BookDto updateBook(UUID id, BookDto bookDto) {
		return bookClient.updateBook(id, bookDto);
	}

	public void deleteBook(UUID id) {
		bookClient.deleteBook(id);
	}
}
