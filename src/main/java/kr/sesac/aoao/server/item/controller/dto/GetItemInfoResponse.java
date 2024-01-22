package kr.sesac.aoao.server.item.controller.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class GetItemInfoResponse {
	private final Long id;
	private final String name;
	private final int price;
	private final int exp;

	@Builder
	public GetItemInfoResponse(Long id, String name, int price, int exp){
		this.id = id;
		this.name = name;
		this.price = price;
		this.exp = exp;
	}
}
