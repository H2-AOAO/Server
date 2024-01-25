package kr.sesac.aoao.server.dino.service;

import kr.sesac.aoao.server.dino.controller.dto.GetUserDinoResponse;

public interface DinoService {
	GetUserDinoResponse getDinoInfo(Long userId);

	GetUserDinoResponse renameDino(Long dinoId, String name);

	GetUserDinoResponse expChange(Long dinoId, Integer currLv, Integer currExp);

	GetUserDinoResponse usePoint(Long userId, Long itemId);
}
