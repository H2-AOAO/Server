package kr.sesac.aoao.server.user.controller;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import kr.sesac.aoao.server.global.controller.dto.response.ApplicationResponse;
import kr.sesac.aoao.server.user.controller.dto.request.SignUpRequest;
import kr.sesac.aoao.server.user.controller.dto.response.JwtTokenResponse;
import kr.sesac.aoao.server.user.controller.dto.response.SignUpResponse;
import kr.sesac.aoao.server.user.domain.User;
import kr.sesac.aoao.server.user.service.UserService;
import lombok.RequiredArgsConstructor;

/**
 * @since 2024.01.18
 * @author 이상민
 */
@RestController
@RequiredArgsConstructor
public class UserController {

	private final UserService userService;

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
	public ResponseEntity<ApplicationResponse<JwtTokenResponse>> login(@RequestBody Map<String, String> user) {
		JwtTokenResponse token = new JwtTokenResponse(userService.login(user));
		return ResponseEntity.ok(ApplicationResponse.success(token));
	}
}
