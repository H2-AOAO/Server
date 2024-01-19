package kr.sesac.aoao.server.dino.service;

import java.util.Optional;

import kr.sesac.aoao.server.dino.controller.dto.GetUserDinoResponse;

public interface DinoService {
	Optional<GetUserDinoResponse> getDinoInfo(Long userId);
}
