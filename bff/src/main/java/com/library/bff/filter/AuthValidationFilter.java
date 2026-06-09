package com.library.bff.filter;

import com.library.bff.client.AuthClient;
import com.library.bff.dto.ApiResponse;
import com.library.bff.dto.ErrorDetails;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class AuthValidationFilter extends OncePerRequestFilter {

	private final AuthClient authClient;
	private final ObjectMapper objectMapper;

	public AuthValidationFilter(AuthClient authClient, ObjectMapper objectMapper) {
		this.authClient = authClient;
		this.objectMapper = objectMapper;
	}

	@Override
	protected boolean shouldNotFilter(HttpServletRequest request) {
		String path = request.getRequestURI();
		return path.equals("/api/auth/login")
				|| path.equals("/api/auth/register")
				|| path.equals("/api/health");
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
		if (authHeader == null || !authHeader.startsWith("Bearer ")) {
			writeUnauthorized(response, "Missing or invalid Authorization header");
			return;
		}

		if (!authClient.validateToken(authHeader)) {
			writeUnauthorized(response, "Invalid or expired token");
			return;
		}

		UsernamePasswordAuthenticationToken authentication =
				new UsernamePasswordAuthenticationToken("validated-user", null, null);
		authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
		SecurityContextHolder.getContext().setAuthentication(authentication);

		filterChain.doFilter(request, response);
	}

	private void writeUnauthorized(HttpServletResponse response, String message) throws IOException {
		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		response.setContentType(MediaType.APPLICATION_JSON_VALUE);
		ApiResponse<Void> body = new ApiResponse<>(false, new ErrorDetails("UNAUTHORIZED", message));
		objectMapper.writeValue(response.getOutputStream(), body);
	}
}
