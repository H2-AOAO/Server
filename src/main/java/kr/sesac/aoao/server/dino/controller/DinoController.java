package kr.sesac.aoao.server.dino.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kr.sesac.aoao.server.dino.controller.dto.request.ExpChangeRequest;
import kr.sesac.aoao.server.dino.controller.dto.request.UsePointRequest;
import kr.sesac.aoao.server.dino.controller.dto.response.GetUserDinoResponse;
import kr.sesac.aoao.server.dino.controller.dto.request.RenameRequest;
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
		@AuthenticationPrincipal UserCustomDetails userDetails, ExpChangeRequest currentExp){
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
		@AuthenticationPrincipal UserCustomDetails userDetails, UsePointRequest useItem){
		GetUserDinoResponse userDinoResponse = dinoService.usePoint(userDetails, useItem);
		return ResponseEntity.ok(ApplicationResponse.success(userDinoResponse));
	}
}