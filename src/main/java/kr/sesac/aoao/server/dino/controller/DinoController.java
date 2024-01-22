package kr.sesac.aoao.server.dino.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import kr.sesac.aoao.server.dino.controller.dto.GetUserDinoResponse;
import kr.sesac.aoao.server.dino.service.DinoService;
import lombok.RequiredArgsConstructor;

/**
 * @since 2024.01.19
 * @author 김은서
 */
@RestController
@RequiredArgsConstructor
public class DinoController {

	private final DinoService dinoService;

	/**
	 * 다이노 정보 조회
	 * @since 2024.01.19
	 * @return GetUserDinoResponse
	 * @author 김은서
	 */
	@GetMapping("/dinos/{userId}")
	public ResponseEntity<GetUserDinoResponse> getDinoInfo(@PathVariable Long userId) {
		GetUserDinoResponse userDinoResponse = dinoService.getDinoInfo(userId);
		return ResponseEntity.ok(userDinoResponse);
	}

	/**
	 * 다이노 이름 변경
	 * @since 2024.01.22
	 * @return GetUserDinoResponse
	 * @author 김은서
	 */
	@PostMapping("/dinos/rename")
	public ResponseEntity<GetUserDinoResponse> renameDino(Long dinoId, String name){
		GetUserDinoResponse userDinoResponse = dinoService.renameDino(dinoId, name);
		return ResponseEntity.ok(userDinoResponse);
	}

	/**
	 * 다이노 경헙치 변경
	 * @since 2024.01.22
	 * @return GetUserDinoResponse
	 * @author 김은서
	 */
	@PostMapping("/dinos/exp")
	public ResponseEntity<GetUserDinoResponse> expChange(Long userId, Long dinoId, Long itemId){
		GetUserDinoResponse userDinoResponse = dinoService.expChange(userId,dinoId,itemId);
		return ResponseEntity.ok(userDinoResponse);
	}

	/**
	 * 다이노 포인트 변경
	 * @since 2024.01.22
	 * @return GetUserDinoResponse
	 * @author 김은서
	 */
	@PostMapping("/dinos/point")
	public ResponseEntity<GetUserDinoResponse> usePoint(Long userId, Long itemId){
		GetUserDinoResponse userDinoResponse = dinoService.usePoint(userId, itemId);
		return ResponseEntity.ok(userDinoResponse);
	}
}
