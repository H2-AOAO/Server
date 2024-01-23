package kr.sesac.aoao.server.user.jwt;

import static kr.sesac.aoao.server.user.exception.UserErrorCode.*;

import java.io.IOException;
import java.util.Objects;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kr.sesac.aoao.server.global.exception.ApplicationException;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	private final JwtTokenProvider jwtTokenProvider;
	private final UserDetailsService userDetailsService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
		FilterChain filterChain) throws ServletException, IOException {
		String authorization = request.getHeader("Authorization");

		if (!Objects.isNull(authorization)) {
			String accessToken = authorization.substring(7);
			try {
				Subject subject = jwtTokenProvider.getSubject(accessToken);
				String requestURI = request.getRequestURI();
				if (subject.getType().equals("RefreshToken") && !requestURI.equals("/user/reissue")) {
					throw new ApplicationException(CHECK_TOKEN);
				}

				UserDetails userDetails = userDetailsService.loadUserByUsername(subject.getEmail());
				Authentication token = new UsernamePasswordAuthenticationToken(userDetails, "",
					userDetails.getAuthorities());
				SecurityContextHolder.getContext().setAuthentication(token);
			} catch (JwtException e) {
				request.setAttribute("exception", e.getMessage());
			}
		}

		// 헤더에서 jwt 받아옴
		// String accessToken = jwtTokenProvider.resolveAccessToken(request);
		// String refreshToken = jwtTokenProvider.resolveRefreshToken(request);
		//
		// // 유효 토큰인지 확인
		// if(accessToken != null){
		//
		// 	// accessToken이 유효한 상황
		// 	if(jwtTokenProvider.validateToken(accessToken)){
		// 		this.setAuthentication(accessToken);
		// 	}else if(!jwtTokenProvider.validateToken(accessToken) && refreshToken != null){
		// 		// 재발급 후, 컨텍스트에 다시 넣기
		// 		/// refresh token 검증
		// 		boolean validateRefreshToken = jwtTokenProvider.validateToken(refreshToken);
		// 		/// refresh token 저장소 존재유무 확인
		// 		boolean isRefreshToken = jwtTokenProvider.existsRefreshToken(refreshToken);
		//
		// 		if(validateRefreshToken && isRefreshToken){
		// 			// 1) refresh token 으로 이메일 정보 가져오기
		// 			String email = jwtTokenProvider.getUserEmail(refreshToken);
		// 			// 2) 이메일로 권환정보 받아오기
		// 			List<String> role = jwtTokenProvider.getRole(email);
		// 			// 3) token 발급
		// 			String newAccessToken = jwtTokenProvider.createAccessToken(email, role);
		// 			// 4) 헤더에 AccessToken 추가
		// 			jwtTokenProvider.setHeaderAccessToken(response, newAccessToken);
		// 			// 5) 컨텍스트에 넣기
		// 			this.setAuthentication(newAccessToken);
		// 		}
		// 	}
		// }
		filterChain.doFilter(request, response);
	}

	// public void setAuthentication(String token){
	// 	// 1) 토큰으로부터 유저 정보 받아옴
	// 	Authentication authentication = jwtTokenProvider.getAuthentication(token);
	// 	// 2) SecurityContext 에 Authentication 객체를 저장함
	// 	SecurityContextHolder.getContext().setAuthentication(authentication);
	// }

}
