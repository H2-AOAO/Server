package kr.sesac.aoao.server.user.jwt;

import static kr.sesac.aoao.server.user.exception.UserErrorCode.*;

import java.util.Base64;
import java.util.Date;
import java.util.Optional;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import kr.sesac.aoao.server.global.exception.ApplicationException;
import kr.sesac.aoao.server.user.controller.dto.response.TokenResponse;
import kr.sesac.aoao.server.user.domain.User;
import kr.sesac.aoao.server.user.repository.RefreshTokenEntity;
import kr.sesac.aoao.server.user.repository.TokenJpaRepository;
import kr.sesac.aoao.server.user.service.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * jwt 관련 함수
 *
 * @author 이상민
 * @since 2024.01.18
 */
@RequiredArgsConstructor
@Component
@Slf4j
public class JwtTokenProvider {

	// @Value("${security.jwt.secret.key}")
	private String secretKey =
		"c2lsdmVybmluZS10ZWNoLXNwcmluZy1ib290LWp3dC10dXRvcmlhbC1zZWNyZXQtc2lsdmVybmluZS10ZWNoLXNwcmluZy1ib290LWp3dC10dXRvcmlhbC1zZWNyZXQK";

	private final long ACCESS_TOKEN_VALID_TIME = 60 * 60 * 24 * 7 * 1000L;
	private final long REFRESH_TOKEN_VALID_TIME = 60 * 60 * 24 * 7 * 1000L;   // 1 달

	private final ObjectMapper objectMapper;
	private final TokenJpaRepository tokenJpaRepository;
	private final CustomUserDetailsService userDetailsService;

	/**
	 * 객체 초기화, secretKey 를 Base64로 인코딩함
	 *
	 * @author 이상민
	 * @since 2024.01.19
	 */
	@PostConstruct
	protected void init() {
		secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
	}

	/**
	 * 로그인 시 accessToken, refreshToken 생성
	 *
	 * @return TokenResponse
	 * @author 이상민
	 * @since 2024.01.22
	 */
	@Transactional
	public TokenResponse createTokensLogin(User user) {
		Subject atkSubject = Subject.accessToken(user.getId(), user.getEmail(), user.getNickname());
		String accessToken = createToken(atkSubject, ACCESS_TOKEN_VALID_TIME);
		Subject rtkSubject = Subject.refreshToken(user.getId(), user.getEmail(), user.getNickname());
		String refreshToken = createToken(rtkSubject, REFRESH_TOKEN_VALID_TIME);

		// 이미 토큰이 존재한다면
		Optional<RefreshTokenEntity> refreshTokenEntity = tokenJpaRepository.findByEmail(user.getEmail());
		refreshTokenEntity.ifPresent(tokenJpaRepository::delete);

		tokenJpaRepository.save(new RefreshTokenEntity(user.getEmail(), refreshToken)); // 리프레시 토큰 저장소에 저장
		return new TokenResponse(user.getId(), accessToken, refreshToken);
	}

	/**
	 * Token 토큰 생성 (token에 유저 정보를 다 담음)
	 *
	 * @return String
	 * @author 이상민
	 * @since 2024.01.22
	 */
	private String createToken(Subject subject, Long tokenLive) {
		try {
			String subjectStr = objectMapper.writeValueAsString(subject);
			Claims claims = Jwts.claims().setSubject(subjectStr);
			Date date = new Date();

			return Jwts.builder()
				.setClaims(claims)
				.setIssuedAt(date)
				.setExpiration(new Date(date.getTime() + tokenLive))
				.signWith(SignatureAlgorithm.HS256, secretKey)
				.compact();
		} catch (Exception e) {
			throw new ApplicationException(NOT_CORRECTED_PASSWORD);
		}
	}

	/**
	 * Token 에서 회원정보 추출
	 *
	 * @return String
	 * @author 이상민
	 * @since 2024.01.22
	 */
	public Subject getSubject(String accessToken) throws JsonProcessingException {
		String subjectStr = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(accessToken).getBody().getSubject();
		return objectMapper.readValue(subjectStr, Subject.class);
	}

	/**
	 * Refresh Token 토큰으로 AccessToken 생성
	 *
	 * @return String
	 * @author 이상민
	 * @since 2024.01.22
	 */
	public TokenResponse reissueAccessToken(User user) {
		return this.createTokensLogin(user);
	}

	/**
	 * 유효한 Token 인지 검증
	 *
	 * @return boolean
	 * @author 이상민
	 * @since 2024.01.19
	 */
	public boolean validateToken(String token) {
		try {
			Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
			return true;
		} catch (SecurityException | MalformedJwtException e) {
			log.info("잘못된 JWT 시그니처, 유효하지 않은 JWT 토큰");
			log.info(token);
			throw new ApplicationException(INVALID_TOKEN);
		} catch (ExpiredJwtException e) {
			log.info("만료된 token");
			throw new ApplicationException(EXPIRE_TOKEN);
		} catch (UnsupportedJwtException e) {
			log.info("지원되지 않는 token");
			throw new ApplicationException(UNSUPPORTED_TOKEN);
		} catch (IllegalArgumentException e) {
			log.info("JWT claims is empty, 잘못된 토큰입니다.");
			throw new ApplicationException(WRONG_TOKEN);
		}
	}

	/**
	 * Header 인증 정보 설정
	 *
	 * @return Authentication
	 * @author 이상민
	 * @since 2024.01.19
	 */
	public Authentication getAuthentication(Subject subject) {
		UserDetails userDetails = userDetailsService.loadUserByUsername(subject.getEmail());
		return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
	}

	/**
	 * Request의 Header에서 accessToken 값을 가져옴 "authorization" : "TOKEN값'
	 *
	 * @return String
	 * @author 이상민
	 * @since 2024.01.19
	 */
	public String resolveToken(HttpServletRequest request) {
		String bearerToken = request.getHeader("Authorization");
		if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
			return bearerToken.substring(7);
		}
		// Bearer 토큰이 존재하지 않거나 올바르지 않은 경우 null 반환
		return null;
	}

}
