package kr.sesac.aoao.server.dino.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import kr.sesac.aoao.server.dino.controller.dto.GetFriendDinoResponse;
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
}
