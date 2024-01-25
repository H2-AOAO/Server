package kr.sesac.aoao.server.item.service;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.sesac.aoao.server.global.exception.ApplicationException;
import kr.sesac.aoao.server.item.controller.dto.GetItemInfoResponse;
import kr.sesac.aoao.server.item.controller.dto.UseItemNumResponse;
import kr.sesac.aoao.server.item.exception.ItemErrorCode;
import kr.sesac.aoao.server.item.repository.ItemEntity;
import kr.sesac.aoao.server.item.repository.ItemJpaRepository;
import kr.sesac.aoao.server.item.repository.UserItemEntity;
import kr.sesac.aoao.server.item.repository.UserItemJpaRepository;
import kr.sesac.aoao.server.user.exception.UserErrorCode;
import kr.sesac.aoao.server.user.jwt.UserCustomDetails;
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

	/**
	 * 아이템 정보 조회
	 * @since 2024.01.22
	 * @return GetItemInfoResponse
	 * @author 김은서
	 */
	@Override
	public GetItemInfoResponse getItemInfo(Long id) {
		ItemEntity item = itemRepository.findById(id)
			.orElseThrow(() -> new ApplicationException(ItemErrorCode.NOT_FOUND_ITEM));
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
	public UseItemNumResponse calItemNum(UserCustomDetails userDetails, Long itemId, String status) {
		Long userId = extractUserId(userDetails);
		UserEntity user = userRepository.findById(userId)
			.orElseThrow(() -> new ApplicationException(UserErrorCode.NOT_FOUND_USER));
		ItemEntity item = itemRepository.findById(itemId)
			.orElseThrow(() -> new ApplicationException(ItemErrorCode.NOT_FOUND_ITEM));
		Optional<UserItemEntity> optionalUserItem = userItemRepository.findByUserAndItem(user, item);
		UserItemEntity userItem;
		if (optionalUserItem.isPresent())
			userItem = optionalUserItem.get();
		else {
			UserItemEntity newUserItem = new UserItemEntity(itemId, user, item, 0);
			userItem = userItemRepository.save(newUserItem);
		}

		int currentItemNum = userItem.getItem_num();
		if (status.equals("구매")) {
			if (itemId == 5) {
				for (Long i = 1L; i < 5; i++) {
					item = itemRepository.findById(i)
						.orElseThrow(() -> new ApplicationException(ItemErrorCode.NOT_FOUND_ITEM));
					optionalUserItem = userItemRepository.findByUserAndItem(user, item);
					if (optionalUserItem.isPresent())
						userItem = optionalUserItem.get();
					currentItemNum = userItem.getItem_num();
					userItem.changeItemNum(currentItemNum + 1);
				}
			} else
				userItem.changeItemNum(currentItemNum + 1);
		}
		if (status.equals("사용"))
			userItem.changeItemNum(currentItemNum - 1);
		return new UseItemNumResponse(
			userItem.getUser().getId(),
			userItem.getItem().getId(),
			userItem.getItem_num()
		);
	}
	private Long extractUserId(UserCustomDetails userCustomDetails) {
		return userCustomDetails.getUserEntity().getId();
	}
}
