package kr.sesac.aoao.server.global.config;

import static org.springframework.boot.autoconfigure.security.servlet.PathRequest.*;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import kr.sesac.aoao.server.user.jwt.JwtAuthenticationEntryPoint;
import kr.sesac.aoao.server.user.jwt.JwtAuthenticationFilter;
import kr.sesac.aoao.server.user.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;

/**
 * @author 이상민
 * @since 2024.01.18
 */
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor

public class SecurityConfig {

	private final JwtTokenProvider jwtTokenProvider;
	private final JwtAuthenticationEntryPoint authenticationEntryPoint;

	@Bean
	public WebSecurityCustomizer configure() {
		return (web -> web.ignoring()
			.requestMatchers(toH2Console())
			.requestMatchers("/static/**"));
	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		return http
			.csrf(AbstractHttpConfigurer::disable)// CSRF 토큰 비활성화
			.authorizeHttpRequests(request -> request
				.requestMatchers("/login", "/signup", "/user/reissue", "/palettes").permitAll()
				.anyRequest().authenticated() //어떠한 요청이라도 인증 필요
			)
			.exceptionHandling(exception -> exception.authenticationEntryPoint(authenticationEntryPoint))
			.addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class)
			.build();
	}

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
