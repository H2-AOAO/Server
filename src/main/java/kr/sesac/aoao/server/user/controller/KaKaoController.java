package kr.sesac.aoao.server.user.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import kr.sesac.aoao.server.global.controller.dto.response.ApplicationResponse;
import kr.sesac.aoao.server.user.controller.dto.request.ouath.AccessToken;
import kr.sesac.aoao.server.user.controller.dto.response.TokenResponse;
import kr.sesac.aoao.server.user.service.KaKaoLoginService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @author 이상민
 * @since 2024.01.26
 */
@RestController
@RequiredArgsConstructor
@Slf4j
public class KaKaoController {

	private final KaKaoLoginService kaKaoLoginService;

	/**
	 * 카카오 로그인
	 *
	 * @return TokenResponse
	 * @author 이상민
	 * @since 2024.01.22
	 */
	@PostMapping("/login/kakao")
	public ResponseEntity<ApplicationResponse<TokenResponse>> loginKakao(@RequestBody AccessToken accessToken) {
		return ResponseEntity.ok(ApplicationResponse.success(kaKaoLoginService.accessTokenLogin(accessToken)));
	}

}
