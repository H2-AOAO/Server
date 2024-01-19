package kr.sesac.aoao.server.dino.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import kr.sesac.aoao.server.dino.controller.dto.GetUserDinoResponse;
import kr.sesac.aoao.server.dino.service.DinoService;

/**
 * @since 2024.01.19
 * @author 김은서
 */

@RestController
public class DinoController {
	private final DinoService dinoService;
	@Autowired
	public DinoController(DinoService dinoService){
		this.dinoService = dinoService;
	}


	@GetMapping("/getUserDinoInfo")
	public ResponseEntity<GetUserDinoResponse> getDinoInfo(@RequestBody Long userId) {
		GetUserDinoResponse userDinoResponse = dinoService.getDinoInfo(userId);
		return ResponseEntity.ok(userDinoResponse);
	}
}
