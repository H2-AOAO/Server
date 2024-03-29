package kr.sesac.aoao.server.user.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.Valid;
import kr.sesac.aoao.server.global.controller.dto.response.ApplicationResponse;
import kr.sesac.aoao.server.user.controller.dto.request.UserNicknameUpdateRequest;
import kr.sesac.aoao.server.user.controller.dto.request.UserPasswordUpdateRequest;
import kr.sesac.aoao.server.user.controller.dto.response.MyPageResponse;
import kr.sesac.aoao.server.user.controller.dto.response.UserProfileUpdateResponse;
import kr.sesac.aoao.server.user.jwt.UserCustomDetails;
import kr.sesac.aoao.server.user.service.UserService;
import lombok.RequiredArgsConstructor;
import oracle.jdbc.proxy.annotation.Post;

/**
 * @author 이상민
 * @since 2024.01.24
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

	private final UserService userService;

	/**
	 * 프로필 조회
	 *
	 * @parameter UserCustomDetails
	 * @return UserProfileResponse
	 * @author 이상민
	 * @since 2024.01.22
	 */
	@GetMapping("/{userId}")
	public ResponseEntity<ApplicationResponse<MyPageResponse>> getMypage(
		@AuthenticationPrincipal UserCustomDetails userDetails, @PathVariable Long userId) {
		MyPageResponse myPageResponse = userService.getProfile(userDetails.getUserEntity().getEmail(),
			userId);
		return ResponseEntity.ok(ApplicationResponse.success(myPageResponse));
	}

	/**
	 * 닉네임 수정 API
	 * @since 2024.01.26
	 * @parameter UserCustomDetails, UserNicknameUpdateRequest
	 * @return ResponseEntity<ApplicationResponse<Void>>
	 * @author 김유빈
	 */
	@PostMapping("/nickname")
	public ResponseEntity<ApplicationResponse<Void>> updateNickname(
		@AuthenticationPrincipal UserCustomDetails userDetails,
		@RequestBody UserNicknameUpdateRequest request) {
		userService.updateNickname(userDetails, request);
		return ResponseEntity.ok(ApplicationResponse.success(null));
	}

	/**
	 * 비밀번호 수정 API
	 * @since 2024.01.26
	 * @parameter UserCustomDetails, UserPasswordUpdateRequest
	 * @return ResponseEntity<ApplicationResponse<Void>>
	 * @author 김유빈
	 */
	@PostMapping("/password")
	public ResponseEntity<ApplicationResponse<Void>> updatePassword(
		@AuthenticationPrincipal UserCustomDetails userDetails,
		@RequestBody @Valid UserPasswordUpdateRequest request) {
		userService.updatePassword(userDetails, request);
		return ResponseEntity.ok(ApplicationResponse.success(null));
	}

	/**
	 * 프로필 수정 API
	 * @since 2024.01.26
	 * @parameter UserCustomDetails, MultipartFile
	 * @return ResponseEntity<ApplicationResponse<UserProfileUpdateResponse>>
	 * @author 김유빈
	 */
	@PostMapping("/profile")
	public ResponseEntity<ApplicationResponse<UserProfileUpdateResponse>> updateProfile(
		@AuthenticationPrincipal UserCustomDetails userDetails,
		@RequestParam("image") MultipartFile newProfile
	) {
		UserProfileUpdateResponse response = userService.updateProfile(userDetails, newProfile);
		return ResponseEntity.ok(ApplicationResponse.success(response));
	}

	/**
	 * 프로필 초기화 API
	 * @since 2024.01.26
	 * @parameter UserCustomDetails
	 * @return ResponseEntity<ApplicationResponse<Void>>
	 * @author 김유빈
	 */
	@DeleteMapping("/profile")
	public ResponseEntity<ApplicationResponse<Void>> initProfile(
		@AuthenticationPrincipal UserCustomDetails userDetails) {
		userService.initProfile(userDetails);
		return ResponseEntity.ok(ApplicationResponse.success(null));
	}

	/**
	 * 유저 탈퇴하기 API
	 *
	 * @parameter UserCustomDetails
	 * @return String
	 * @author 이상민
	 * @since 2024.01.22
	 */
	@DeleteMapping("/delete")
	public ResponseEntity<ApplicationResponse<String>> deleteUser(
		@AuthenticationPrincipal UserCustomDetails userDetails) {
		userService.deleteUser(userDetails.getUserEntity().getId());
		return ResponseEntity.ok().body(ApplicationResponse.success("탈퇴에 성공하였습니다."));
	}

	/**
	 * 로그아웃 API
	 *
	 * @parameter String
	 * @return String
	 * @author 이상민
	 * @since 2024.01.28
	 */
	@PostMapping("/logout")
	public ResponseEntity<ApplicationResponse<String>> logout(
		@AuthenticationPrincipal UserCustomDetails userDetails) {
		userService.logout(userDetails.getUserEntity());
		return ResponseEntity.ok().body(ApplicationResponse.success("로그아웃되었습니다."));
	}
}
