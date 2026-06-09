package com.library.bff.client;

import com.library.bff.dto.BookDto;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class BookClientImpl implements BookClient {

	private final RestClient restClient;

	public BookClientImpl(@Value("${book.service.url}") String baseUrl) {
		this.restClient = RestClient.builder()
				.baseUrl(baseUrl)
				.build();
	}

	private record BooksResponse(String mensaje, List<BookDto> libros, Integer total) {}
	private record BookResponse(String mensaje, BookDto data) {}

	@Override
	public List<BookDto> getBooks(String title, String author, String category, String isbn) {
		ResponseEntity<BooksResponse> response = restClient.get()
				.uri(uriBuilder -> {
					var builder = uriBuilder.path("/api/books");
					if (title != null && !title.isBlank()) {
						builder.queryParam("title", title);
					}
					if (author != null && !author.isBlank()) {
						builder.queryParam("author", author);
					}
					if (category != null && !category.isBlank()) {
						builder.queryParam("category", category);
					}
					if (isbn != null && !isbn.isBlank()) {
						builder.queryParam("isbn", isbn);
					}
					return builder.build();
				})
				.retrieve()
				.toEntity(BooksResponse.class);

		BooksResponse body = response.getBody();
		if (response.getStatusCode().is2xxSuccessful() && body != null && body.libros() != null) {
			return body.libros();
		}
		throw new IllegalStateException("Books service returned an invalid books response");
	}

	@Override
	public BookDto getBookById(UUID id) {
		return restClient.get()
				.uri("/api/books/{id}", id)
				.retrieve()
				.body(BookDto.class);
	}

	@Override
	public BookDto createBook(BookDto bookDto) {
		ResponseEntity<BookResponse> response = restClient.post()
				.uri("/api/books")
				.body(bookDto)
				.retrieve()
				.toEntity(BookResponse.class);

		return extractBook(response);
	}

	@Override
	public BookDto updateBook(UUID id, BookDto bookDto) {
		ResponseEntity<BookResponse> response = restClient.put()
				.uri("/api/books/{id}", id)
				.body(bookDto)
				.retrieve()
				.toEntity(BookResponse.class);

		return extractBook(response);
	}

	@Override
	public void deleteBook(UUID id) {
		restClient.delete()
				.uri("/api/books/{id}", id)
				.retrieve()
				.toBodilessEntity();
	}

	private BookDto extractBook(ResponseEntity<BookResponse> response) {
		HttpStatusCode status = response.getStatusCode();
		BookResponse body = response.getBody();
		if ((status.is2xxSuccessful() || status.value() == HttpStatus.CREATED.value()) && body != null) {
			return body.data();
		}
		throw new IllegalStateException("Books service returned an invalid book response");
	}
}
