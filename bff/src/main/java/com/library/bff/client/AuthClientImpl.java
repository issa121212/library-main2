package com.library.bff.client;

import com.library.bff.dto.ApiResponse;
import com.library.bff.dto.AuthResponse;
import com.library.bff.dto.LoginRequest;
import com.library.bff.dto.RegisterRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;

@Component
public class AuthClientImpl implements AuthClient {

	private final RestClient restClient;

	public AuthClientImpl(@Value("${auth.service.url}") String baseUrl) {
		this.restClient = RestClient.builder()
				.baseUrl(baseUrl)
				.build();
	}

	@Override
	public ApiResponse<AuthResponse> register(RegisterRequest request) {
		return restClient.post()
				.uri("/api/auth/register")
				.body(request)
				.retrieve()
				.body(new ParameterizedTypeReference<>() {});
	}

	@Override
	public ApiResponse<AuthResponse> login(LoginRequest request) {
		return restClient.post()
				.uri("/api/auth/login")
				.body(request)
				.retrieve()
				.body(new ParameterizedTypeReference<>() {});
	}

	@Override
	public boolean validateToken(String bearerToken) {
		try {
			ApiResponse<Boolean> response = restClient.get()
					.uri("/api/auth/validate")
					.header(HttpHeaders.AUTHORIZATION, bearerToken)
					.retrieve()
					.body(new ParameterizedTypeReference<>() {});

			return response != null && response.success() && Boolean.TRUE.equals(response.data());
		} catch (RestClientException ex) {
			return false;
		}
	}
}
