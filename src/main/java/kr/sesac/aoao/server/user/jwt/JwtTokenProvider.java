package kr.sesac.aoao.server.user.jwt;

import static kr.sesac.aoao.server.user.exception.UserErrorCode.*;

import java.util.Base64;
import java.util.Date;
import java.util.Objects;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.annotation.PostConstruct;
import kr.sesac.aoao.server.global.exception.ApplicationException;
import kr.sesac.aoao.server.user.controller.dto.response.TokenResponse;
import kr.sesac.aoao.server.user.domain.User;
import kr.sesac.aoao.server.user.repository.RefreshTokenEntity;
import kr.sesac.aoao.server.user.repository.TokenJpaRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class JwtTokenProvider {

	// @Value("${security.jwt.secret.key}")
	private String secretKey =
		"c2lsdmVybmluZS10ZWNoLXNwcmluZy1ib290LWp3dC10dXRvcmlhbC1zZWNyZXQtc2lsdmVybmluZS10ZWNoLXNwcmluZy1ib290LWp3dC10dXRvcmlhbC1zZWNyZXQK";

	private final long accessTokenValidTime = 60000;
	private final long refreshTokenValidTime = 300000;

	private final ObjectMapper objectMapper;
	private final TokenJpaRepository tokenJpaRepository;

	/**
	 * 객체 초기화, secretKey 를 Base64로 인코딩함
	 * @since 2024.01.19
	 * @author 이상민
	 */
	@PostConstruct
	protected void init() {
		secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
	}

	/**
	 * 로그인 시 token 생성
	 * @since 2024.01.22
	 * @return TokenResponse
	 * @author 이상민
	 */
	public TokenResponse createTokensLogin(User user) {
		Subject atkSubject = Subject.atk(user.getId(), user.getEmail(), user.getNickname());
		Subject rtkSubject = Subject.rtk(user.getId(), user.getEmail(), user.getNickname());

		String accessToken = createToken(atkSubject, accessTokenValidTime);
		String refreshToken = createToken(rtkSubject, refreshTokenValidTime);

		// 리프레시 토큰 저장소에 저장
		tokenJpaRepository.save(new RefreshTokenEntity(user.getEmail(), refreshToken));
		return new TokenResponse(accessToken, refreshToken);
	}

	/**
	 * Token 토큰 생성
	 * @since 2024.01.22
	 * @return String
	 * @author 이상민
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
	 * @since 2024.01.22
	 * @return String
	 * @author 이상민
	 */
	public Subject getSubject(String accessToken) throws JsonProcessingException {
		String subjectStr = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(accessToken).getBody().getSubject();
		return objectMapper.readValue(subjectStr, Subject.class);
	}

	/**
	 * Refresh Token 토큰 생성
	 * @since 2024.01.22
	 * @return String
	 * @author 이상민
	 */
	public TokenResponse reissueAccessToken(User user) {
		RefreshTokenEntity refreshToken = tokenJpaRepository.findByEmail(user.getEmail());
		if (Objects.isNull(refreshToken)) {
			throw new ApplicationException(JWT_FORBIDDEN_EXCEPTION);
		}
		Subject accessTokenSubject = Subject.atk(user.getId(), user.getEmail(), user.getNickname());
		String accessToken = createToken(accessTokenSubject, accessTokenValidTime);

		Subject refreshTokenSubject = Subject.rtk(user.getId(), user.getEmail(), user.getNickname());
		String newRefreshToken = createToken(refreshTokenSubject, refreshTokenValidTime);

		tokenJpaRepository.delete(refreshToken);
		tokenJpaRepository.save(new RefreshTokenEntity(user.getEmail(), newRefreshToken));

		return new TokenResponse(accessToken, newRefreshToken);
	}

	/**
	 * 토큰의 유효성 + 만료일자 확인
	 * @since 2024.01.19
	 * @return String
	 * @author 이상민
	 */
	public boolean validateToken(String jwtToken) {
		try {
			Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(jwtToken);
			return !claims.getBody().getExpiration().before(new Date());
		} catch (Exception e) {
			return false;
		}
	}

	// /**
	//  * AccessToken 토큰 생성
	//  * @since 2024.01.19
	//  * @return String
	//  * @author 이상민
	//  */
	// public String createAccessToken(String userPK, List<String> roles) {
	// 	return this.createToken(userPK, roles, accessTokenValidTime);
	// }
	//
	// /**
	//  * RefreshToken 토큰 생성
	//  * @since 2024.01.19
	//  * @return String
	//  * @author 이상민
	//  */
	// public String createRefreshToken(String userPK, List<String> roles) {
	// 	return this.createToken(userPK, roles, refreshTokenValidTime);
	// }
	//
	// /**
	//  * Token 토큰 생성
	//  * @since 2024.01.19
	//  * @return String
	//  * @author 이상민
	//  */
	// public String createToken(String userPK, List<String> roles,long tokenValid) {
	// 	Claims claims = Jwts.claims().setSubject(userPK);
	// 	claims.put("roles", roles);
	// 	Date now = new Date();
	//
	// 	return Jwts.builder()
	// 		.setClaims(claims) // 정보 저장
	// 		.setIssuedAt(now) // 토큰 발행 시간 정보
	// 		.setExpiration(new Date(now.getTime() + tokenValid)) // set Expire Time
	// 		.signWith(SignatureAlgorithm.HS256, secretKey) // 사용할 암호화 알고리즘
	// 		.compact();
	// }
	//
	// /**
	//  * JWT 토큰에서 인증정보 조회
	//  * @since 2024.01.19
	//  * @return String
	//  * @author 이상민
	//  */
	// public Authentication getAuthentication(String token) {
	// 	UserDetails userDetails = userDetailsService.loadUserByUsername(this.getUserEmail(token));
	// 	return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
	// }
	//
	// /**
	//  * 토큰에서 회원 정보 추출
	//  * @since 2024.01.19
	//  * @return String
	//  * @author 이상민
	//  */
	// public String getUserEmail(String token) {
	// 	return Jwts.parser().setSigningKey(secretKey).parseClaimsJwt(token).getBody().getSubject();
	// }
	//
	// /**
	//  * Request의 Header에서 accessToken 값을 가져옴 "authorization" : "TOKEN값'
	//  * @since 2024.01.19
	//  * @return String
	//  * @author 이상민
	//  */
	// public String resolveAccessToken(HttpServletRequest request) {
	// 	if(request.getHeader("authorization") != null)
	// 		return request.getHeader("authorization").substring(7);
	// 	return null;
	// }
	//
	// /**
	//  * Request의 Header에서 refreshToken 값을 가져옴 "refreshToken" : "TOKEN값'
	//  * @since 2024.01.19
	//  * @return String
	//  * @author 이상민
	//  */
	// public String resolveRefreshToken(HttpServletRequest request) {
	// 	if(request.getHeader("refreshToken") != null)
	// 		return request.getHeader("authorization").substring(7);
	// 	return null;
	// }
	//
	// // 어세스 토큰 헤더 설정
	// public void setHeaderAccessToken(HttpServletResponse response, String accessToken) {
	// 	response.setHeader("authorization", "bearer "+ accessToken);
	// }
	//
	// // 리프레시 토큰 헤더 설정
	// public void setHeaderRefreshToken(HttpServletResponse response, String refreshToken) {
	// 	response.setHeader("refreshToken", "bearer "+ refreshToken);
	// }
	//
	// // RefreshToken 존재유무 확인
	// public boolean existsRefreshToken(String refreshToken) {
	// 	return tokenJpaRepository.existsByRefreshToken(refreshToken);
	// }
	//
	// // Email로 권한 정보 가져오기
	// public List<String> getRole(String email) {
	// 	List<String> roles = new ArrayList<>();
	// 	roles.add(userJpaRepository.findByEmail(email).get().getRole().name());
	// 	return roles;
	// }
}
