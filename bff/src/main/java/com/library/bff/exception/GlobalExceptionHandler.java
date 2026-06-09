package com.library.bff.exception;

import com.library.bff.dto.ApiResponse;
import com.library.bff.dto.ErrorDetails;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.RestClientResponseException;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(RestClientResponseException.class)
	public ResponseEntity<ApiResponse<Void>> handleDownstreamError(RestClientResponseException ex) {
		HttpStatus status = HttpStatus.resolve(ex.getStatusCode().value());
		if (status == null) {
			status = HttpStatus.BAD_GATEWAY;
		}
		ErrorDetails error = new ErrorDetails("DOWNSTREAM_ERROR", ex.getResponseBodyAsString());
		return ResponseEntity.status(status).body(new ApiResponse<>(false, error));
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ApiResponse<Void>> handleError(Exception ex) {
		ErrorDetails error = new ErrorDetails("BFF_ERROR", ex.getMessage());
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse<>(false, error));
	}
}
