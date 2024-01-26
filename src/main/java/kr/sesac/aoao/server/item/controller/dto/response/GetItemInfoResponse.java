package kr.sesac.aoao.server.item.controller.dto.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class GetItemInfoResponse {

	private final long itemId;
	private final String name;
	private final int price;
	private final int exp;
}
