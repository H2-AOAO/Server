package kr.sesac.aoao.server.user.jwt;

import java.util.Base64;
import java.util.Date;
import java.util.List;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class JwtTokenProvider {

	private String secretKey =
		"c2lsdmVybmluZS10ZWNoLXNwcmluZy1ib290LWp3dC10dXRvcmlhbC1zZWNyZXQtc2lsdmVybmluZS10ZWNoLXNwcmluZy1ib290LWp3dC10dXRvcmlhbC1zZWNyZXQK";

	// 토큰 유효시간 168 시간(7일)
	private long tokenValidTime = 1440 * 60 * 7 * 1000L;
	private final UserDetailsService userDetailsService;

	// 객체 초기화, secretKey 를 Base64로 인코딩합니다.
	@PostConstruct
	protected void init() {
		secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
	}

	/**
	 * 토큰 생성
	 * @since 2024.01.19
	 * @return String
	 * @author 이상민
	 */
	public String createToken(String userPK, List<String> roles){
		Claims claims = Jwts.claims().setSubject(userPK);
		claims.put("roles", roles);
		Date now = new Date();

		return Jwts.builder()
			.setClaims(claims) // 정보 저장
			.setIssuedAt(now) // 토큰 발행 시간 정보
			.setExpiration(new Date(now.getTime() + tokenValidTime)) // set Expire Time
			.signWith(SignatureAlgorithm.HS256, secretKey) // 사용할 암호화 알고리즘
			.compact();
	}

	/**
	 * JWT 토큰에서 인증정보 조회
	 * @since 2024.01.19
	 * @return String
	 * @author 이상민
	 */
	public Authentication getAuthentication(String token){
		UserDetails userDetails = userDetailsService.loadUserByUsername(this.getUserPK(token));
		return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
	}

	// 토큰에서 회원 정보 추출
	private String getUserPK(String token) {
		return Jwts.parser().setSigningKey(secretKey).parseClaimsJwt(token).getBody().getSubject();
	}

	// Request의 Header에서 token 값을 가져옴 "X-AUTH-TOKEN" : "TOKEN값'
	public String resolveToken(HttpServletRequest request){
		return request.getHeader("X-AUTH-TOKEN");
	}

	// 토큰의 유효성 + 만료일자 확인
	public boolean validateToken(String jwtToken){
		try {
			Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(jwtToken);
			return !claims.getBody().getExpiration().before(new Date());
		}catch (Exception e){
			return false;
		}
	}

}
