package kr.sesac.aoao.server.dino.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.sesac.aoao.server.dino.controller.dto.GetUserDinoResponse;
import kr.sesac.aoao.server.dino.exception.DinoErrorCode;
import kr.sesac.aoao.server.dino.repository.DinoEntity;
import kr.sesac.aoao.server.dino.repository.DinoInfoEntity;
import kr.sesac.aoao.server.dino.repository.DinoJpaRepository;
import kr.sesac.aoao.server.global.exception.ApplicationException;
import kr.sesac.aoao.server.item.exception.ItemErrorCode;
import kr.sesac.aoao.server.item.repository.ItemEntity;
import kr.sesac.aoao.server.item.repository.ItemJpaRepository;
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
	private final ItemJpaRepository itemRepository;

	private GetUserDinoResponse result(DinoEntity dino){
		return new GetUserDinoResponse(
			dino.getUser().getId(),
			dino.getName(),
			dino.getColor(),
			dino.getExp(),
			dino.getDino().getLv(),
			dino.getUser().getPoint().getPoint()
		);
	}

	/**
	 * 다이노 정보 가져오기
	 * @since 2024.01.18
	 * @parameter userId
	 * @return GetUserDinoResponse
	 * @author 김은서
	 */
	@Override
	public GetUserDinoResponse getDinoInfo(Long userId) {
		UserEntity user = userRepository.findById(userId)
			.orElseThrow(() -> new ApplicationException(UserErrorCode.NOT_FOUND_USER));;
		DinoEntity dino = dinoRepository.findByUser(user)
			.orElseThrow(() -> new ApplicationException(DinoErrorCode.NO_DINO));
		return result(dino);
	}

	/**
	 * 다이노 이름 변경
	 * @since 2024.01.22
	 * @return GetUserDinoResponse
	 * @author 김은서
	 */
	@Override
	public GetUserDinoResponse renameDino(UserCustomDetails userDetails, String name) {
		Long userId = extractUserId(userDetails);
		UserEntity user = userRepository.findById(userId)
			.orElseThrow(() -> new ApplicationException(UserErrorCode.NOT_FOUND_USER));;
		DinoEntity dino = dinoRepository.findByUser(user)
			.orElseThrow(() -> new ApplicationException(DinoErrorCode.NO_DINO));
		dino.changeName(name);
		dinoRepository.save(dino);
		return result(dino);
	}

	/**
	 * 다이노 경험치 변경 - 레벨업 포함
	 * @since 2024.01.22
	 * @return GetUserDinoResponse
	 * @author 김은서
	 */
	@Override
	public GetUserDinoResponse expChange(UserCustomDetails userDetails, Integer currLv, Integer currExp) {
		Long userId = extractUserId(userDetails);
		UserEntity user = userRepository.findById(userId)
		.orElseThrow(() -> new ApplicationException(UserErrorCode.NOT_FOUND_USER));
		DinoEntity dino = dinoRepository.findByUserId(user.getId())
			.orElseThrow(() -> new ApplicationException(DinoErrorCode.NO_DINO));

		dino.changeExp(currExp);
		DinoInfoEntity dinoInfoEntity = dino.getDino();
		dinoInfoEntity.changeLv(currLv);

		return result(dino);
	}

	/**
	 * 다이노 포인트 변경
	 * @since 2024.01.22
	 * @return GetUserDinoResponse
	 * @author 김은서
	 */
	@Override
	public GetUserDinoResponse usePoint(UserCustomDetails userDetails, Long itemId) {
		Long userId = extractUserId(userDetails);
		UserEntity user = userRepository.findById(userId)
			.orElseThrow(() -> new ApplicationException(UserErrorCode.NOT_FOUND_USER));
		ItemEntity item = itemRepository.findById(itemId)
			.orElseThrow(() ->new ApplicationException(ItemErrorCode.NOT_FOUND_ITEM));
		DinoEntity dino = dinoRepository.findByUserId(user.getId())
			.orElseThrow(() -> new ApplicationException(DinoErrorCode.NO_DINO));

		int itemPrice = item.getPrice();
		int point = dino.getPoint();
		if (itemPrice > point)
			throw new ApplicationException(DinoErrorCode.NOT_ENOUGH_POINT);
		else{
			dino.changePoint(point - itemPrice);
		}

		return result(dino);
	}
	private Long extractUserId(UserCustomDetails userCustomDetails) {
		return userCustomDetails.getUserEntity().getId();
	}
}
