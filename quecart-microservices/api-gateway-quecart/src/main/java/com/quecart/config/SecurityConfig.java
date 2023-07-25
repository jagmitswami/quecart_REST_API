package com.quecart.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.server.resource.authentication.ReactiveJwtAuthenticationConverter;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebFluxSecurity
class SecurityConfig {
	@Bean
	public SecurityWebFilterChain springSecurityWebFilterChain(ServerHttpSecurity http) {
		return http.csrf(csrf -> csrf.disable())
				.authorizeExchange(
						exchange -> exchange.pathMatchers("/eureka/**")
						.permitAll()
						.anyExchange()
						.authenticated())
				.oauth2ResourceServer(
						oauth2 -> oauth2.jwt(jwt -> jwt.jwtAuthenticationConverter(jwtAuthenticationConverter())))
				.build();
	}

	private ReactiveJwtAuthenticationConverter jwtAuthenticationConverter() {
		return new ReactiveJwtAuthenticationConverter();
	}
}
