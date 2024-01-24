package kr.sesac.aoao.server.item.controller.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class GetItemInfoResponse {

	private final Long id;
	private final String name;
	private final int price;
	private final int exp;

	public GetItemInfoResponse(Long id, String name, int price, int exp) {
		this.id = id;
		this.name = name;
		this.price = price;
		this.exp = exp;
	}
}
