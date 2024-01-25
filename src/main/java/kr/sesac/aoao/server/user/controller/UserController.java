package kr.sesac.aoao.server.user.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import kr.sesac.aoao.server.global.controller.dto.response.ApplicationResponse;
import kr.sesac.aoao.server.user.controller.dto.request.*;
import kr.sesac.aoao.server.user.controller.dto.response.UserProfileResponse;
import kr.sesac.aoao.server.user.jwt.UserCustomDetails;
import kr.sesac.aoao.server.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @author 이상민
 * @since 2024.01.24
 */
@RestController
@RequiredArgsConstructor
@Slf4j
public class UserController {

	private final UserService userService;

	/**
	 * 프로필 조회
	 *
	 * @return UserProfileResponse
	 * @author 이상민
	 * @since 2024.01.22
	 */
	@GetMapping("/user")
	public ResponseEntity<ApplicationResponse<UserProfileResponse>> getProfile(
		@AuthenticationPrincipal UserCustomDetails userDetails) {
		UserProfileResponse userProfileResponse = userService.getProfile(userDetails.getUserEntity().getEmail(),
			1L);
		return ResponseEntity.ok(ApplicationResponse.success(userProfileResponse));
	}

	/**
	 * 이메일 중복확인 API
	 *
	 * @return
	 * @author 이상민
	 * @since 2024.01.22
	 */
	@GetMapping("/duplicated/email")
	public ResponseEntity<ApplicationResponse<String>> duplicationEmail(@Validated @RequestBody DuplicatedEmailRequest duplicatedEmailRequest) {
		userService.duplicatedEmail(duplicatedEmailRequest.getEmail());
		return ResponseEntity.ok(ApplicationResponse.success("사용가능한 이메일입니다."));
	}

	/**
	 * 닉네임 중복확인 API
	 *
	 * @return
	 * @author 이상민
	 * @since 2024.01.22
	 */
	@GetMapping("/duplicated/nickname")
	public ResponseEntity<ApplicationResponse<String>> duplicationNickname(@Validated @RequestBody DuplicatedNicknameRequest duplicatedNicknameRequest) {
		userService.duplicationNickname(duplicatedNicknameRequest.getNickname());
		return ResponseEntity.ok(ApplicationResponse.success("사용가능한 넥네임입니다."));
	}

	/**
	 * 유저 탈퇴하기 API
	 *
	 * @return
	 * @author 이상민
	 * @since 2024.01.22
	 */
	@DeleteMapping("/user/delete")
	public ResponseEntity<ApplicationResponse<Void>> deleteUser(@AuthenticationPrincipal UserCustomDetails userDetails) {
		userService.deleteUser(userDetails.getUserEntity().getId());
		return ResponseEntity.ok().build();
	}


}
