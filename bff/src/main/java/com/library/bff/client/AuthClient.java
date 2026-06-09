package com.library.bff.client;

import com.library.bff.dto.ApiResponse;
import com.library.bff.dto.AuthResponse;
import com.library.bff.dto.LoginRequest;
import com.library.bff.dto.RegisterRequest;

public interface AuthClient {

	ApiResponse<AuthResponse> register(RegisterRequest request);

	ApiResponse<AuthResponse> login(LoginRequest request);

	boolean validateToken(String bearerToken);
}
