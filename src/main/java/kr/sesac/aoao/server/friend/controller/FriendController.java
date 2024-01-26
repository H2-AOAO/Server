package kr.sesac.aoao.server.friend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kr.sesac.aoao.server.friend.controller.dto.request.FriendAddAcceptRequest;
import kr.sesac.aoao.server.friend.controller.dto.request.FriendAddDenyRequest;
import kr.sesac.aoao.server.friend.controller.dto.request.FriendAddRequest;
import kr.sesac.aoao.server.friend.controller.dto.response.GetFriendResponse;
import kr.sesac.aoao.server.friend.service.FriendService;
import kr.sesac.aoao.server.global.controller.dto.response.ApplicationResponse;
import kr.sesac.aoao.server.user.jwt.UserCustomDetails;
import lombok.RequiredArgsConstructor;

/**
 * @since 2024.01.25
 * @author 최정윤
 */
@RestController
@RequestMapping("/friends")
@RequiredArgsConstructor
public class FriendController {

	private final FriendService friendService;

	/**
	 * 친구목록 조회
	 * @since 2024.01.23
	 * @return getFriendInfo
	 * @author 최정윤
	 */
	@GetMapping("/")
	public ResponseEntity<ApplicationResponse<GetFriendResponse>> getFriendInfo(@AuthenticationPrincipal UserCustomDetails userDetails) {
		GetFriendResponse userFriendResponse = friendService.getFriendInfo(userDetails);
		return ResponseEntity.ok(ApplicationResponse.success(userFriendResponse));
	}

	/**
	 * 친구추가
	 * @since 2024.01.23
	 * @return addFriend
	 * @author 최정윤
	 */
	@PostMapping("/{friendId}/request")
	public ResponseEntity<ApplicationResponse<String>> addFriend(
		@AuthenticationPrincipal UserCustomDetails userDetails,
		@PathVariable Long friendId,
		@RequestBody FriendAddRequest request) {
		friendService.addFriend(userDetails.getUserEntity().getId(), friendId, request);
		return ResponseEntity.ok(ApplicationResponse.success("친구추가 완료되었습니다."));
	}

	/**
	 * 친구추가 취소
	 * @since 2024.01.23
	 * @return addFriendCancel
	 * @author 최정윤
	 */
	@DeleteMapping("/{friendId}/request")
	public ResponseEntity<ApplicationResponse<String>> addFriendCancel(
		@AuthenticationPrincipal UserCustomDetails userDetails, @PathVariable Long friendId) {
		friendService.addFriendCancel(userDetails.getUserEntity().getId(), friendId);
		return ResponseEntity.ok(ApplicationResponse.success("친구추가 취소되었습니다."));
	}

	/**
	 * 친구신청 수락
	 * @since 2024.01.23
	 * @return addFriendAccept
	 * @author 최정윤
	 */
	@PostMapping("/{friendId}/accept")
	public ResponseEntity<ApplicationResponse<String>> addFriendAccept(
		@AuthenticationPrincipal UserCustomDetails userDetails,
		@PathVariable Long friendId,
		@RequestBody FriendAddAcceptRequest request) {
		friendService.addFriendAccept(userDetails.getUserEntity().getId(), friendId, request);
		return ResponseEntity.ok(ApplicationResponse.success("친구신청을 수락하였습니다."));
	}

	/**
	 * 친구신청 거절
	 * @since 2024.01.23
	 * @return addFriendDeny
	 * @author 최정윤
	 */
	@PostMapping("/{friendId}/deny")
	public ResponseEntity<ApplicationResponse<String>> addFriendDeny(
		@AuthenticationPrincipal UserCustomDetails userDetails,
		@PathVariable Long friendId,
		@RequestBody FriendAddDenyRequest request) {
		friendService.addFriendDeny(userDetails.getUserEntity().getId(), friendId, request);
		return ResponseEntity.ok(ApplicationResponse.success("친구신청을 거절하였습니다."));
	}

	/**
	 * 친구삭제
	 * @since 2024.01.23
	 * @return deleteFriend
	 * @author 최정윤
	 */
	@DeleteMapping("/{friendId}")
	public ResponseEntity<ApplicationResponse<String>> deleteFriend(
		@AuthenticationPrincipal UserCustomDetails userDetails, @PathVariable Long friendId) {
		friendService.deleteFriend(userDetails.getUserEntity().getId(), friendId);
		return ResponseEntity.ok(ApplicationResponse.success("친구가 삭제되었습니다."));
	}

}
