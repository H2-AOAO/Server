package kr.sesac.aoao.server.user.jwt;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @author 이상민
 * @since 2024.01.22
 */
@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	private final JwtTokenProvider jwtTokenProvider;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
		FilterChain filterChain) throws ServletException, IOException {
		String token = jwtTokenProvider.resolveToken(request);
		try {
			// StringUtils.hasText() : 'null'이 아니면서, 하나 이상의 공백 포함 x
			if (StringUtils.hasText(token) && jwtTokenProvider.validateToken(token)) {
				Subject subject = jwtTokenProvider.getSubject(token);
				Authentication authentication = jwtTokenProvider.getAuthentication(subject);
				SecurityContextHolder.getContext().setAuthentication(authentication);
			}
			filterChain.doFilter(request, response);
		} catch (Exception e) {
			log.error(e.getMessage());
			handleAuthenticationException(response, e.getMessage());
		}
	}

	/**
	 * Jwt 에러 반환 함수
	 *
	 * @return void
	 * @author 이상민
	 * @since 2024.01.23
	 */
	private void handleAuthenticationException(HttpServletResponse response, String message) throws IOException {
		response.setStatus(HttpServletResponse.SC_OK);
		response.setContentType("application/json;charset=UTF-8");

		Map<String, Object> responseBody = new HashMap<>();
		responseBody.put("success", false);
		responseBody.put("message", message);
		responseBody.put("date", null);

		PrintWriter writer = response.getWriter();
		writer.write(new ObjectMapper().writeValueAsString(responseBody));
		writer.flush();
		writer.close();
	}
}
