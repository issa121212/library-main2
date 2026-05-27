package com.library.users.dto;

public record AuthResponse(
    String token,
    long expiresIn,
    String username

) {

}
