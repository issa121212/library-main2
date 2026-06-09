package com.library.bff.config;

import com.library.bff.filter.AuthValidationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

	private final AuthValidationFilter authValidationFilter;

	public SecurityConfig(AuthValidationFilter authValidationFilter) {
		this.authValidationFilter = authValidationFilter;
	}

	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http
				.csrf(csrf -> csrf.disable())
				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.authorizeHttpRequests(auth -> auth
						.requestMatchers("/api/auth/login", "/api/auth/register", "/api/health").permitAll()
						.anyRequest().authenticated())
				.addFilterBefore(authValidationFilter, UsernamePasswordAuthenticationFilter.class);

		return http.build();
	}
}
