package kr.sesac.aoao.server.dino.service;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.sesac.aoao.server.dino.controller.dto.GetUserDinoResponse;
import kr.sesac.aoao.server.dino.repository.DinoJpaRepository;
import lombok.RequiredArgsConstructor;

/**
 * @since 2024.01.19
 * @author 김은서
 */

@Service
@Transactional
@RequiredArgsConstructor
public class DinoServiceImpl implements DinoService {

	private final DinoJpaRepository dinoRepository;

	@Override
	public Optional<GetUserDinoResponse> getDinoInfo(Long userId) {
		return dinoRepository.findByUserID(userId);
	}

}
