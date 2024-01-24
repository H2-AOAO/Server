package kr.sesac.aoao.server.user.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import kr.sesac.aoao.server.global.controller.dto.response.ApplicationResponse;
import kr.sesac.aoao.server.user.controller.dto.request.LoginRequest;
import kr.sesac.aoao.server.user.controller.dto.request.SignUpRequest;
import kr.sesac.aoao.server.user.controller.dto.response.SignUpResponse;
import kr.sesac.aoao.server.user.controller.dto.response.TokenResponse;
import kr.sesac.aoao.server.user.controller.dto.response.UserProfileResponse;
import kr.sesac.aoao.server.user.domain.User;
import kr.sesac.aoao.server.user.jwt.JwtTokenProvider;
import kr.sesac.aoao.server.user.jwt.UserCustomDetails;
import kr.sesac.aoao.server.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @author 이상민
 * @since 2024.01.18
 */
@RestController
@RequiredArgsConstructor
@Slf4j
public class UserController {

	private final UserService userService;
	private final JwtTokenProvider jwtTokenProvider;

	/**
	 * 회원가입
	 *
	 * @param signUpRequest
	 * @return User
	 * @author 이상민
	 * @since 2024.01.18
	 */
	@PostMapping("/signup")
	public ResponseEntity<ApplicationResponse<SignUpResponse>> signup(@Valid @RequestBody SignUpRequest signUpRequest) {
		User user = userService.signUp(signUpRequest);
		return ResponseEntity.ok(ApplicationResponse.success(new SignUpResponse(user)));
	}

	/**
	 * 로그인
	 *
	 * @return JwtTokenResponse
	 * @author 이상민
	 * @since 2024.01.19
	 */
	@PostMapping("/login")
	public ResponseEntity<ApplicationResponse<TokenResponse>> login(@Valid @RequestBody LoginRequest loginRequest) {
		User user = userService.login(loginRequest);
		TokenResponse tokenResponse = jwtTokenProvider.createTokensLogin(user);
		return ResponseEntity.ok(ApplicationResponse.success(tokenResponse));
	}

	/**
	 * 토큰 재발급
	 *
	 * @return TokenResponse
	 * @author 이상민
	 * @since 2024.01.22
	 */
	@PostMapping("/user/reissue")
	public ResponseEntity<ApplicationResponse<TokenResponse>> reissue(
		@AuthenticationPrincipal UserCustomDetails userCustomDetails) {
		User user = new User(userCustomDetails.getUserEntity());
		TokenResponse tokenResponse = jwtTokenProvider.reissueAccessToken(user);
		return ResponseEntity.ok(ApplicationResponse.success(tokenResponse));
	}

	/**
	 * 프로필 조회
	 *
	 * @return UserProfileResponse
	 * @author 이상민
	 * @since 2024.01.22
	 */
	@GetMapping("/user/{userId}")
	public ResponseEntity<ApplicationResponse<UserProfileResponse>> getProfile(
		@AuthenticationPrincipal UserCustomDetails userDetails, @PathVariable Long userId) {
		UserProfileResponse userProfileResponse = userService.getProfile(userDetails.getUserEntity().getEmail(),
			userId);
		return ResponseEntity.ok(ApplicationResponse.success(userProfileResponse));
	}

}
