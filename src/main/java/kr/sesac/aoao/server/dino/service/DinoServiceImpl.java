package kr.sesac.aoao.server.dino.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.sesac.aoao.server.dino.controller.dto.request.ExpChangeRequest;
import kr.sesac.aoao.server.dino.controller.dto.request.UsePointRequest;
import kr.sesac.aoao.server.dino.controller.dto.response.GetUserDinoResponse;
import kr.sesac.aoao.server.dino.controller.dto.request.RenameRequest;
import kr.sesac.aoao.server.dino.exception.DinoErrorCode;
import kr.sesac.aoao.server.dino.repository.DinoEntity;
import kr.sesac.aoao.server.dino.repository.DinoInfoEntity;
import kr.sesac.aoao.server.dino.repository.DinoJpaRepository;
import kr.sesac.aoao.server.global.exception.ApplicationException;
import kr.sesac.aoao.server.item.exception.ItemErrorCode;
import kr.sesac.aoao.server.item.repository.ItemEntity;
import kr.sesac.aoao.server.item.repository.ItemJpaRepository;
import kr.sesac.aoao.server.point.repository.PointEntity;
import kr.sesac.aoao.server.point.repository.PointJpaRepository;
import kr.sesac.aoao.server.user.exception.UserErrorCode;
import kr.sesac.aoao.server.user.jwt.UserCustomDetails;
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
	private final PointJpaRepository pointRepository;

	private GetUserDinoResponse result(DinoEntity dino, int user_point){
		return new GetUserDinoResponse(
			dino.getUser().getId(),
			dino.getName(),
			dino.getColor(),
			dino.getExp(),
			dino.getDino().getLv(),
			user_point
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
	public GetUserDinoResponse getDinoInfo(UserCustomDetails userDetails) {
		Long userId = extractUserId(userDetails);
		System.out.println(userId);
		UserEntity user = getUserEntitiy(userId);
		DinoEntity dino = getDinoEntity(user);
		//PointEntity point = getPointEntity(user);
		int user_point = user.getPoint().getPoint();

		return result((dino), user_point);
	}

	/**
	 * 다이노 이름 변경
	 * @since 2024.01.22
	 * @return GetUserDinoResponse
	 * @author 김은서
	 */
	@Override
	public String renameDino(UserCustomDetails userDetails, RenameRequest name) {
		Long userId = extractUserId(userDetails);
		UserEntity user = getUserEntitiy(userId);
		DinoEntity dino = getDinoEntity(user);
		String new_name = name.getName();
		dino.changeName(new_name);
		dinoRepository.save(dino);
		return new_name;
	}

	/**
	 * 다이노 경험치 변경 - 레벨업 포함
	 * @since 2024.01.22
	 * @return GetUserDinoResponse
	 * @author 김은서
	 */
	@Override
	public GetUserDinoResponse expChange(UserCustomDetails userDetails, ExpChangeRequest currentExp) {
		Long userId = extractUserId(userDetails);
		UserEntity user = getUserEntitiy(userId);
		DinoEntity dino = getDinoEntity(user);
		dino.changeExp(currentExp.getCurrExp());
		DinoInfoEntity dinoInfoEntity = dino.getDino();
		dinoInfoEntity.changeLv(currentExp.getCurrLv());

		int user_point = user.getPoint().getPoint();
		return result((dino), user_point);
	}

	/**
	 * 다이노 포인트 변경
	 * @since 2024.01.22
	 * @return GetUserDinoResponse
	 * @author 김은서
	 */
	@Override
	public GetUserDinoResponse usePoint(UserCustomDetails userDetails, UsePointRequest useItem) {
		Long userId = extractUserId(userDetails);
		UserEntity user = getUserEntitiy(userId);
		DinoEntity dino = getDinoEntity(user);
		ItemEntity item = itemRepository.findById(useItem.getItemId())
			.orElseThrow(() ->new ApplicationException(ItemErrorCode.NOT_FOUND_ITEM));
		PointEntity point = getPointEntity(user);
		int user_point = point.getPoint();
		int itemPrice = item.getPrice();

		if (itemPrice > user_point)
			throw new ApplicationException(DinoErrorCode.NOT_ENOUGH_POINT);
		else{
			point.changePoint(user_point - itemPrice);
		}
		return result((dino), user_point- itemPrice);
	}
	private Long extractUserId(UserCustomDetails userCustomDetails) {
		return userCustomDetails.getUserEntity().getId();
	}
	private UserEntity getUserEntitiy(Long userId){
		return userRepository.findById(userId)
			.orElseThrow(() -> new ApplicationException(UserErrorCode.NOT_FOUND_USER));
	}
	private DinoEntity getDinoEntity(UserEntity user){
		return dinoRepository.findByUser(user)
			.orElseThrow(() -> new ApplicationException(DinoErrorCode.NO_DINO));
	}
	private PointEntity getPointEntity(UserEntity user){
		return pointRepository.findByUser(user)
			.orElseThrow(() -> new ApplicationException(DinoErrorCode.NO_DINO));
	}
}
