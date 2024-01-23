package kr.sesac.aoao.server.user.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;

import jakarta.validation.Valid;
import kr.sesac.aoao.server.global.controller.dto.response.ApplicationResponse;
import kr.sesac.aoao.server.user.controller.dto.request.LoginRequest;
import kr.sesac.aoao.server.user.controller.dto.request.SignUpRequest;
import kr.sesac.aoao.server.user.controller.dto.response.SignUpResponse;
import kr.sesac.aoao.server.user.controller.dto.response.TokenResponse;
import kr.sesac.aoao.server.user.domain.User;
import kr.sesac.aoao.server.user.jwt.JwtTokenProvider;
import kr.sesac.aoao.server.user.jwt.UserDetails;
import kr.sesac.aoao.server.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @since 2024.01.18
 * @author 이상민
 */
@RestController
@RequiredArgsConstructor
@Slf4j
public class UserController {

	private final UserService userService;
	private final JwtTokenProvider jwtTokenProvider;

	/**
	 * 회원가입
	 * @since 2024.01.18
	 * @param signUpRequest
	 * @return User
	 * @author 이상민
	 */
	@PostMapping("/signup")
	public ResponseEntity<ApplicationResponse<SignUpResponse>> signup(@Valid @RequestBody SignUpRequest signUpRequest) {
		User user = userService.signUp(signUpRequest);
		return ResponseEntity.ok(ApplicationResponse.success(new SignUpResponse(user)));
	}

	/**
	 * 로그인
	 * @since 2024.01.19
	 * @return JwtTokenResponse
	 * @author 이상민
	 */
	@PostMapping("/login")
	public ResponseEntity<ApplicationResponse<TokenResponse>> login(@RequestBody LoginRequest loginRequest) throws
		JsonProcessingException {
		User user = userService.login(loginRequest);
		TokenResponse tokenResponse = jwtTokenProvider.createTokensLogin(user);
		System.out.println(jwtTokenProvider.getSubject(tokenResponse.getAccessToken()));
		return ResponseEntity.ok(ApplicationResponse.success(tokenResponse));
	}

	/**
	 * 토큰 재발급
	 * @since 2024.01.22
	 * @return TokenResponse
	 * @author 이상민
	 */
	@PostMapping("/user/reissue")
	public ResponseEntity<ApplicationResponse<TokenResponse>> reissue(
		@AuthenticationPrincipal UserDetails userDetails) {
		User user = new User(userDetails.getUserEntity());
		TokenResponse tokenResponse = jwtTokenProvider.reissueAccessToken(user);
		return ResponseEntity.ok(ApplicationResponse.success(tokenResponse));
	}

}
