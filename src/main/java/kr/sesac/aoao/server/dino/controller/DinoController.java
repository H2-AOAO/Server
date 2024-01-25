package kr.sesac.aoao.server.dino.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kr.sesac.aoao.server.dino.controller.dto.GetUserDinoResponse;
import kr.sesac.aoao.server.dino.service.DinoService;
import kr.sesac.aoao.server.global.controller.dto.response.ApplicationResponse;
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
	@GetMapping("/{userId}")
	public ResponseEntity<ApplicationResponse<GetUserDinoResponse>> getDinoInfo(@PathVariable Long userId) {
		GetUserDinoResponse userDinoResponse = dinoService.getDinoInfo(userId);
		return ResponseEntity.ok(ApplicationResponse.success(userDinoResponse));
	}

	/**
	 * 다이노 이름 변경
	 * @since 2024.01.22
	 * @return GetUserDinoResponse
	 * @author 김은서
	 */
	@PostMapping("/rename")
	public ResponseEntity<ApplicationResponse<GetUserDinoResponse>> renameDino(Long dinoId, String name){
		GetUserDinoResponse userDinoResponse = dinoService.renameDino(dinoId, name);
		return ResponseEntity.ok(ApplicationResponse.success(userDinoResponse));
	}

	/**
	 * 다이노 경헙치 변경
	 * @since 2024.01.22
	 * @return GetUserDinoResponse
	 * @author 김은서
	 */
	@PostMapping("/exp")
	public ResponseEntity<ApplicationResponse<GetUserDinoResponse>> expChange(Long userId, Integer currLv, Integer currExp){
		GetUserDinoResponse userDinoResponse = dinoService.expChange(userId,currLv,currExp);
		return ResponseEntity.ok(ApplicationResponse.success(userDinoResponse));
	}

	/**
	 * 다이노 포인트 변경
	 * @since 2024.01.22
	 * @return GetUserDinoResponse
	 * @author 김은서
	 */
	@PostMapping("/point")
	public ResponseEntity<ApplicationResponse<GetUserDinoResponse>> usePoint(Long userId, Long itemId){
		GetUserDinoResponse userDinoResponse = dinoService.usePoint(userId, itemId);
		return ResponseEntity.ok(ApplicationResponse.success(userDinoResponse));
	}
}