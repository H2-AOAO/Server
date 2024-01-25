package kr.sesac.aoao.server.dino.controller.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class GetUserDinoResponse {

	private final Long userId;
	private final String name;
	private final String color;
	private final Integer exp;
	private final int Lv;
}
