package kr.sesac.aoao.server.item.controller.dto.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class UseItemNumResponse {
	private final Long itemId;
	private final Long userId;
	private final int itemNum;

}
