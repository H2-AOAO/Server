package kr.sesac.aoao.server.dino.service;

import kr.sesac.aoao.server.dino.controller.dto.GetUserDinoResponse;

public interface DinoService {
	GetUserDinoResponse getDinoInfo(Long userId);
}
