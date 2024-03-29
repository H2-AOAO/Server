package kr.sesac.aoao.server.item.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.sesac.aoao.server.global.exception.ApplicationException;
import kr.sesac.aoao.server.item.controller.dto.response.GetItemInfoResponse;
import kr.sesac.aoao.server.item.controller.dto.request.ItemNumRequest;
import kr.sesac.aoao.server.item.controller.dto.response.UseItemNumResponse;
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
	public UseItemNumResponse calItemNum(UserCustomDetails userDetails, ItemNumRequest useItem) {
		Long userId = extractUserId(userDetails);
		Long itemId = useItem.getItemId();
		String status = useItem.getStatus();
		UserEntity user = userRepository.findById(userId)
			.orElseThrow(() -> new ApplicationException(UserErrorCode.NOT_FOUND_USER));
		UserItemEntity userItem = getUserItem(user, itemId);
		int currentItemNum = userItem.getItem_num();
		if(status.equals("구매")) {
			if(itemId == 5){
				for(Long i = 1L; i < 5; i++){
					userItem = getUserItem(user, i);
					userItem.changeItemNum(currentItemNum + 1);
				}
			}
			else userItem.changeItemNum(currentItemNum + 1);
		}
		if(status.equals("사용")) userItem.changeItemNum(currentItemNum - 1);
		return new UseItemNumResponse(
			userItem.getUser().getId(),
			userItem.getItem().getId(),
			userItem.getItem_num()
		);
	}

	private UserItemEntity getUserItem(UserEntity user, Long itemId){
		ItemEntity item = itemRepository.findById(itemId)
			.orElseThrow(() ->new ApplicationException(ItemErrorCode.NOT_FOUND_ITEM));
		return userItemRepository.findByUserAndItem(user,item)
			.orElseThrow(() ->new ApplicationException(ItemErrorCode.NOT_FOUND_ITEM));
	}
	private Long extractUserId(UserCustomDetails userCustomDetails) {
		return userCustomDetails.getUserEntity().getId();
	}
}
