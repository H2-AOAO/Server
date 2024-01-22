package kr.sesac.aoao.server.item.controller.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class UseItemNumResponse {
	private final Long itemId;
	private final Long userId;
	private final int itemNum;

	@Builder
	public UseItemNumResponse(Long item_id, Long user_id, int item_num){
		this.item_id = item_id;
		this.user_id = user_id;
		this.item_num = item_num;
	}

}
