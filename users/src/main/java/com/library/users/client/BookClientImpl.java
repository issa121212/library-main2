package com.library.users.client;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import com.library.users.dto.BookDto;

@Component
public class BookClientImpl implements BookClient {

    private final RestClient restClient;

    public BookClientImpl(@Value("${book.service.url}") String baseurl) {
        this.restClient = RestClient.builder()
                .baseUrl(baseurl)
                .build();
    }

    // --- Records auxiliares para mapear las respuestas estructuradas del Books Service ---
    private record BooksResponse(String mensaje, List<BookDto> libros, Integer total) {}
    private record BookResponse(String mensaje, BookDto data) {}

    @Override
    public List<BookDto> getAllBooks() {
        ResponseEntity<BooksResponse> response = restClient.get()
                .uri("/api/books")
                .retrieve()
                .toEntity(BooksResponse.class);

        BooksResponse body = response.getBody();
        HttpStatusCode status = response.getStatusCode();
        if (status.is2xxSuccessful() && body != null) {
            return body.libros(); // Extraemos la lista de la propiedad "libros"
        } else {
            throw new RuntimeException("Failed to fetch books: " + status);
        }
    }

    @Override
    public List<BookDto> searchBooks(String title, String author, String category, String isbn) {
        ResponseEntity<BooksResponse> response = restClient.get()
                .uri(uriBuilder -> {
                    var builder = uriBuilder.path("/api/books"); // Ruta corregida sin "/search"

                    if (title != null && !title.isBlank()) {
                        builder = builder.queryParam("title", title); // Corregido "tittle" a "title"
                    }
                    if (author != null && !author.isBlank()) {
                        builder = builder.queryParam("author", author);
                    }
                    if (category != null && !category.isBlank()) {
                        builder = builder.queryParam("category", category);
                    }
                    if (isbn != null && !isbn.isBlank()) {
                        builder = builder.queryParam("isbn", isbn);
                    }
                    return builder.build();
                })
                .retrieve()
                .toEntity(BooksResponse.class);

        BooksResponse body = response.getBody();
        HttpStatusCode status = response.getStatusCode();
        if (status.is2xxSuccessful() && body != null) {
            return body.libros(); // Extraemos la lista de la propiedad "libros"
        } else {
            throw new RuntimeException("Failed to search books: " + status);
        }
    }

    @Override
    public BookDto getBookById(UUID id) {
        // GET /api/books/{id} retorna el BookDto directo en este endpoint
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

        BookResponse body = response.getBody();
        HttpStatusCode status = response.getStatusCode();
        if ((status.is2xxSuccessful() || status.value() == HttpStatus.CREATED.value()) && body != null) {
            return body.data(); // Extraemos el libro de la propiedad "data"
        }
        throw new RuntimeException("Failed to create book: " + status);
    }

    @Override
    public BookDto updateBook(UUID bookId, BookDto bookDto) {
        ResponseEntity<BookResponse> response = restClient.put()
                .uri("/api/books/{id}", bookId)
                .body(bookDto)
                .retrieve()
                .toEntity(BookResponse.class);

        BookResponse body = response.getBody();
        HttpStatusCode status = response.getStatusCode();
        if (status.is2xxSuccessful() && body != null) {
            return body.data(); // Extraemos el libro actualizado de "data"
        }
        throw new RuntimeException("Failed to update book: " + status);
    }

    @Override
    public void deleteBook(UUID id) {
        restClient.delete()
                .uri("/api/books/{id}", id)
                .retrieve()
                .toBodilessEntity(); // Ejecuta el borrado de forma segura
    }
}