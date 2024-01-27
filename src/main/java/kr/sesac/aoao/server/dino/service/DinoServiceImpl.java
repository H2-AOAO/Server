package kr.sesac.aoao.server.dino.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.sesac.aoao.server.dino.controller.dto.response.GetUserDinoResponse;
import kr.sesac.aoao.server.dino.controller.dto.request.ExpChangeRequest;
import kr.sesac.aoao.server.dino.controller.dto.request.NewDinoRequest;
import kr.sesac.aoao.server.dino.controller.dto.request.UsePointRequest;
import kr.sesac.aoao.server.dino.controller.dto.request.RenameRequest;
import kr.sesac.aoao.server.dino.controller.dto.response.DinoSimpleInfo;
import kr.sesac.aoao.server.dino.controller.dto.response.FriendDinoInfo;
import kr.sesac.aoao.server.dino.exception.DinoErrorCode;
import kr.sesac.aoao.server.dino.repository.DinoEntity;
import kr.sesac.aoao.server.dino.repository.DinoInfoEntity;
import kr.sesac.aoao.server.dino.repository.DinoInfoJpaRepository;
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
	private final DinoInfoJpaRepository dinoInfoRepository;

	private GetUserDinoResponse result(DinoEntity dino, int user_point){
		return new GetUserDinoResponse(
			dino.getUser().getId(),
			dino.getName(),
			dino.getColor(),
			dino.getExp(),
			dino.getDino().getStep(),
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
		UserEntity user = getUserEntitiy(userId);
		DinoEntity dino = getDinoEntity(user);
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
		dino.getDino().changeLv(currentExp.getCurrLv());

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

		if (itemPrice > user_point) throw new ApplicationException(DinoErrorCode.NOT_ENOUGH_POINT);
		else point.changePoint(user_point - itemPrice);

		return result((dino), user_point- itemPrice);
	}

	/**
	 * 새로운 다이노 만들기
	 * @since 2024.01.25
	 * @return Boolean
	 * @author 김은서
	 */
	@Override
	public Boolean newDino(UserCustomDetails userDetails, NewDinoRequest newDino) {
		String name = newDino.getDinoName();
		String color = newDino.getDinoColor();
		Long userId = extractUserId(userDetails);
		UserEntity user = getUserEntitiy(userId);
		DinoEntity dino = dinoRepository.findByUserAndFlag(user, true).orElse(null);
		if(dino != null) {
			dino.saveFlag(false); //현재 true인 공룡을 완성 상태로 변환
			dinoRepository.save(dino);
		}
		DinoInfoEntity dinoInfo = dinoInfoRepository.findById(1L)
			.orElseThrow(()->new ApplicationException(DinoErrorCode.NO_DINO_INFO));
		dinoInfo.changeLv(1);
		DinoEntity nDino = new DinoEntity(user, name, color, 0, true, dinoInfo); //새롭게 객체 저장
		dinoRepository.save(nDino);
		return true;
	}

	/**
	 * 과거 다이노 조회
	 * @return List<DinoSimpleInfo>
	 * @author 김은서
	 * @since 2024.01.25
	 */
	@Override
	public List<DinoSimpleInfo> userPastDino(UserCustomDetails userDetails){
		Long userId = extractUserId(userDetails);
		UserEntity user = getUserEntitiy(userId);
		List<DinoEntity> allDinos = dinoRepository.findAllByUser(user); // 모든 다이노를 조회

		List<DinoSimpleInfo> allPastDino = allDinos.stream()
			.filter(dino -> !dino.isFlag()) //flag==false인 값만. True는 현재 공룡 값
			.map(this::dinoToSimpleInfo)
			.collect(Collectors.toList());

		return allPastDino;
	}

	/**
	 * 친구 다이노 조회
	 * @return List<DinoSimpleInfo>
	 * @author 김은서
	 * @since 2024.01.25
	 */
	@Override
	public FriendDinoInfo friendDino(Long friendId) {
		FriendDinoInfo friendDinoInfo = new FriendDinoInfo();
		UserEntity user = getUserEntitiy(friendId);
		DinoEntity dino = getDinoEntity(user);
		friendDinoInfo.saveColor(dino.getColor());
		friendDinoInfo.saveName(dino.getName());
		friendDinoInfo.saveLevel(dino.getDino().getStep());
		return friendDinoInfo;
	}

	private DinoSimpleInfo dinoToSimpleInfo(DinoEntity dino){
		DinoSimpleInfo simpleDino = new DinoSimpleInfo();
		simpleDino.saveName(dino.getName());
		simpleDino.saveColor(dino.getColor());
		return simpleDino;
	}
	private Long extractUserId(UserCustomDetails userCustomDetails) {
		return userCustomDetails.getUserEntity().getId();
	}
	private UserEntity getUserEntitiy(Long userId){
		return userRepository.findById(userId)
			.orElseThrow(() -> new ApplicationException(UserErrorCode.NOT_FOUND_USER));
	}
	private DinoEntity getDinoEntity(UserEntity user){
		return dinoRepository.findByUserAndFlag(user, true)
			.orElseThrow(() -> new ApplicationException(UserErrorCode.NOT_FOUND_USER));
	}
	private PointEntity getPointEntity(UserEntity user){
		return pointRepository.findByUser(user)
			.orElseThrow(() -> new ApplicationException(DinoErrorCode.NO_DINO));
	}
}
