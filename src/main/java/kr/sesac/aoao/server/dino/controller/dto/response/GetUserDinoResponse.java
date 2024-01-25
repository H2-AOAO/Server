package kr.sesac.aoao.server.dino.controller.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class GetUserDinoResponse {

	private final long userId;
	private final String name;
	private final String color;
	private final int exp;
	private final int Lv;
	private final int point;
}
