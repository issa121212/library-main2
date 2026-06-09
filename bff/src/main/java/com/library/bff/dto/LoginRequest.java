package com.library.bff.dto;

public record LoginRequest(
		String username,
		String password
) {
}
