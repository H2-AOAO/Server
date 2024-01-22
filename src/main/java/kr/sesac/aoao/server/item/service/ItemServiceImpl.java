package kr.sesac.aoao.server.item.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.sesac.aoao.server.dino.exception.DinoErrorCode;
import kr.sesac.aoao.server.dino.repository.DinoEntity;
import kr.sesac.aoao.server.dino.repository.DinoJpaRepository;
import kr.sesac.aoao.server.global.exception.ApplicationException;
import kr.sesac.aoao.server.item.controller.dto.GetItemInfoResponse;
import kr.sesac.aoao.server.item.controller.dto.UseItemNumResponse;
import kr.sesac.aoao.server.item.exception.ItemErrorCode;
import kr.sesac.aoao.server.item.repository.ItemEntity;
import kr.sesac.aoao.server.item.repository.ItemJpaRepository;
import kr.sesac.aoao.server.item.repository.UserItemEntity;
import kr.sesac.aoao.server.item.repository.UserItemJpaRepository;
import kr.sesac.aoao.server.user.exception.UserErrorCode;
import kr.sesac.aoao.server.user.repository.UserEntity;
import kr.sesac.aoao.server.user.repository.UserJpaRepository;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {
	private final ItemJpaRepository itemRepository;
	private final UserJpaRepository userRepository;
	private final UserItemJpaRepository userItemRepository;
	private final DinoJpaRepository dinoRepository;

	/**
	 * 아이템 정보 조회
	 * @since 2024.01.22
	 * @return GetItemInfoResponse
	 * @author 김은서
	 */
	@Override
	public GetItemInfoResponse getItemInfo(Long id) {
		ItemEntity item = itemRepository.findById(id)
			.orElseThrow(()-> new ApplicationException(ItemErrorCode.NOT_FOUND_ITEM));
		return new GetItemInfoResponse(
			item.getId(),
			item.getName(),
			item.getPrice(),
			item.getExp()
		);
	}

	/**
	 * 아이템 사용 - 개수 조절
	 * @since 2024.01.22
	 * @return UseItemNumResponse
	 * @author 김은서
	 */
	@Override
	@Transactional
	public UseItemNumResponse calItemNum(Long userId, Long itemId, String status) {
		UserEntity user = userRepository.findById(userId)
			.orElseThrow(() -> new ApplicationException(UserErrorCode.NOT_FOUND_USER));
		ItemEntity item = itemRepository.findById(itemId)
			.orElseThrow(() ->new ApplicationException(ItemErrorCode.NOT_FOUND_ITEM));
		UserItemEntity userItem = userItemRepository.findByUserAndItem(user,item)
			.orElseThrow(() -> new ApplicationException(ItemErrorCode.NOT_FOUND_USER_ITEM));
		DinoEntity dino = dinoRepository.findByUserId(user.getId())
			.orElseThrow(() -> new ApplicationException(DinoErrorCode.NO_DINO));
		int currentItemNum = userItem.getItem_num();
		if(status.equals("구매")) userItem.setItem_num(currentItemNum + 1);
		if(status.equals("사용") && dino.getPoint() >= item.getPrice()) userItem.setItem_num(currentItemNum - 1);
		userItemRepository.save(userItem);

		return new UseItemNumResponse(
			userItem.getUser().getId(),
			userItem.getItem().getId(),
			userItem.getItem_num()
		);

	}
}
