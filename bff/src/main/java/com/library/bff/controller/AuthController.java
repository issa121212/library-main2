package com.library.bff.controller;

import com.library.bff.dto.ApiResponse;
import com.library.bff.dto.AuthResponse;
import com.library.bff.dto.LoginRequest;
import com.library.bff.dto.RegisterRequest;
import com.library.bff.service.GatewayService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

	private final GatewayService gatewayService;

	public AuthController(GatewayService gatewayService) {
		this.gatewayService = gatewayService;
	}

	@PostMapping("/register")
	public ResponseEntity<ApiResponse<AuthResponse>> register(@RequestBody RegisterRequest request) {
		return ResponseEntity.status(HttpStatus.CREATED).body(gatewayService.register(request));
	}

	@PostMapping("/login")
	public ResponseEntity<ApiResponse<AuthResponse>> login(@RequestBody LoginRequest request) {
		return ResponseEntity.ok(gatewayService.login(request));
	}
}
