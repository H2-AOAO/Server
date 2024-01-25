package kr.sesac.aoao.server.dino.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Dino {

	private final String name;
	private final String color;
	private final int exp;
	private final Long userId;
	private final int Lv;
	private final int point;
}
