package kr.sesac.aoao.server.dino.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kr.sesac.aoao.server.dino.controller.dto.response.GetUserDinoResponse;
import kr.sesac.aoao.server.dino.controller.dto.request.ExpChangeRequest;
import kr.sesac.aoao.server.dino.controller.dto.request.NewDinoRequest;
import kr.sesac.aoao.server.dino.controller.dto.request.UsePointRequest;
import kr.sesac.aoao.server.dino.controller.dto.request.RenameRequest;
import kr.sesac.aoao.server.dino.controller.dto.response.DinoSimpleInfo;
import kr.sesac.aoao.server.dino.controller.dto.response.FriendDinoInfo;
import kr.sesac.aoao.server.dino.service.DinoService;
import kr.sesac.aoao.server.global.controller.dto.response.ApplicationResponse;
import kr.sesac.aoao.server.user.jwt.UserCustomDetails;
import lombok.RequiredArgsConstructor;

/**
 * @since 2024.01.19
 * @author 김은서
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/dinos")
public class DinoController {

	private final DinoService dinoService;

	/**
	 * 다이노 정보 조회
	 * @since 2024.01.19
	 * @return GetUserDinoResponse
	 * @author 김은서
	 */
	@GetMapping("/")
	public ResponseEntity<ApplicationResponse<GetUserDinoResponse>> getDinoInfo(
		@AuthenticationPrincipal UserCustomDetails userDetails) {
		GetUserDinoResponse userDinoResponse = dinoService.getDinoInfo(userDetails);
		return ResponseEntity.ok(ApplicationResponse.success(userDinoResponse));
	}

	/**
	 * 다이노 이름 변경
	 * @since 2024.01.22
	 * @return GetUserDinoResponse
	 * @author 김은서
	 */
	@PostMapping("/rename")
	public ResponseEntity<ApplicationResponse<String>> renameDino(
		@AuthenticationPrincipal UserCustomDetails userDetails,
		@RequestBody RenameRequest name){
		String userDinoResponse = dinoService.renameDino(userDetails, name);
		return ResponseEntity.ok(ApplicationResponse.success(userDinoResponse));
	}

	/**
	 * 다이노 경헙치 변경
	 * @since 2024.01.22
	 * @return GetUserDinoResponse
	 * @author 김은서
	 */
	@PostMapping("/exp")
	public ResponseEntity<ApplicationResponse<GetUserDinoResponse>> expChange(
		@AuthenticationPrincipal UserCustomDetails userDetails, @RequestBody ExpChangeRequest currentExp){
		GetUserDinoResponse userDinoResponse = dinoService.expChange(userDetails,currentExp);
		return ResponseEntity.ok(ApplicationResponse.success(userDinoResponse));
	}

	/**
	 * 다이노 포인트 변경
	 * @since 2024.01.22
	 * @return GetUserDinoResponse
	 * @author 김은서
	 */
	@PostMapping("/point")
	public ResponseEntity<ApplicationResponse<GetUserDinoResponse>> usePoint(
		@AuthenticationPrincipal UserCustomDetails userDetails, @RequestBody UsePointRequest useItem){
		GetUserDinoResponse userDinoResponse = dinoService.usePoint(userDetails, useItem);
		return ResponseEntity.ok(ApplicationResponse.success(userDinoResponse));
	}

	/**
	 * 새로운 다이노 생성
	 * @since 2024.01.25
	 * @return GetUserDinoResponse
	 * @author 김은서
	 */
	@PostMapping("/newdino")
	public ResponseEntity<ApplicationResponse<Boolean>> newDino(
		@AuthenticationPrincipal UserCustomDetails userDetails,@RequestBody NewDinoRequest newDino){
		Boolean isNewDinoSaved = dinoService.newDino(userDetails, newDino);
		return ResponseEntity.ok(ApplicationResponse.success(isNewDinoSaved));
	}

	/**
	 * 과거 다이노 조회
	 * @return List<DinoSimpleInfo>
	 * @author 김은서
	 * @since 2024.01.25
	 */
	@GetMapping("/past")
	public ResponseEntity<ApplicationResponse<List<DinoSimpleInfo>>> pastDino(
		@AuthenticationPrincipal UserCustomDetails userDetails){
		List<DinoSimpleInfo> pastDino = dinoService.userPastDino(userDetails);
		return ResponseEntity.ok(ApplicationResponse.success(pastDino));
	}

	/**
	 * 친구 다이노 조회
	 * @return List<DinoSimpleInfo>
	 * @author 김은서
	 * @since 2024.01.25
	 */
	@GetMapping("/friend/{friendId}")
	public ResponseEntity<ApplicationResponse<FriendDinoInfo>> firendDino(
		@PathVariable Long friendId) {
		FriendDinoInfo friendDino = dinoService.friendDino(friendId);
		return ResponseEntity.ok(ApplicationResponse.success(friendDino));
	}
}