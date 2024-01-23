package kr.sesac.aoao.server.dino.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.sesac.aoao.server.dino.controller.dto.GetUserDinoResponse;
import kr.sesac.aoao.server.dino.exception.DinoErrorCode;
import kr.sesac.aoao.server.dino.repository.DinoEntity;
import kr.sesac.aoao.server.dino.repository.DinoJpaRepository;
import kr.sesac.aoao.server.global.exception.ApplicationException;
import kr.sesac.aoao.server.user.exception.UserErrorCode;
import kr.sesac.aoao.server.user.repository.UserEntity;
import kr.sesac.aoao.server.user.repository.UserJpaRepository;
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
	private final UserJpaRepository userRepository;

	/**
	 * 댓글 등록
	 * @since 2024.01.18
	 * @parameter userId
	 * @return GetUserDinoResponse
	 * @author 김은서
	 */
	@Override
	public GetUserDinoResponse getDinoInfo(Long userId) {
		UserEntity user = userRepository.findById(userId)
			.orElseThrow(() -> new ApplicationException(UserErrorCode.NOT_FOUND_USER));
		;
		DinoEntity dino = dinoRepository.findByUserId(user.getId())
			.orElseThrow(() -> new ApplicationException(DinoErrorCode.NO_DINO));
		return new GetUserDinoResponse(
			dino.getUser().getId(),
			dino.getName(),
			dino.getColor(),
			dino.getExp(),
			dino.getDino().getLv(),
			dino.getPoint()
		);
	}

}
