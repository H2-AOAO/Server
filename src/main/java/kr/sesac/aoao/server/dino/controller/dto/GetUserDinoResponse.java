package kr.sesac.aoao.server.dino.controller.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class GetUserDinoResponse {

	private final Long userId;
	private final String name;
	private final String color;
	private final Integer exp;
	private final Integer Lv;
	private final Integer point;

	@Builder
	public GetUserDinoResponse(Long userId, String name, String color, Integer exp, Integer lv, Integer point) {
		this.userId = userId;
		this.name = name;
		this.color = color;
		this.exp = exp;
		this.Lv = lv;
		this.point = point;
	}
}
