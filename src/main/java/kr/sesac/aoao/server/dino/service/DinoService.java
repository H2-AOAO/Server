package kr.sesac.aoao.server.dino.service;

import kr.sesac.aoao.server.dino.controller.dto.GetUserDinoResponse;

public interface DinoService {
	GetUserDinoResponse getDinoInfo(Long userId);
	GetUserDinoResponse renameDino(UserCustomDetails userDetails, String name);
	GetUserDinoResponse expChange(UserCustomDetails userDetails, Integer currLv, Integer currExp);
	GetUserDinoResponse usePoint(UserCustomDetails userDetails, Long itemId);
}
