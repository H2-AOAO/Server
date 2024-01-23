package kr.sesac.aoao.server.item.controller.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class UseItemNumResponse {
	private final Long itemId;
	private final Long userId;
	private final int itemNum;

	@Builder
	public UseItemNumResponse(Long item_id, Long user_id, int item_num) {
		this.itemId = item_id;
		this.userId = user_id;
		this.itemNum = item_num;
	}

}
