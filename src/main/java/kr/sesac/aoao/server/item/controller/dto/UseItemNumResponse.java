package kr.sesac.aoao.server.item.controller.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class UseItemNumResponse {

	private final Long itemId;
	private final Long userId;
	private final int itemNum;

	public UseItemNumResponse(Long itemId, Long userId, int itemNum){
		this.itemId = itemId;
		this.userId = userId;
		this.itemNum = itemNum;
	}
}
