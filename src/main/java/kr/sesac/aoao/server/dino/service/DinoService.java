package kr.sesac.aoao.server.dino.service;

import kr.sesac.aoao.server.dino.controller.dto.GetUserDinoResponse;

public interface DinoService {
	GetUserDinoResponse getDinoInfo(Long userId);
	GetUserDinoResponse renameDino(Long dinoId, String name);
	GetUserDinoResponse expChange(Long userId, Long dinoId, Long itemId);
	GetUserDinoResponse usePoint(Long userId, Long itemId);
}
