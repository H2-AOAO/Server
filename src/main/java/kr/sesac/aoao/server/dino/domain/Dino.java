package kr.sesac.aoao.server.dino.domain;

import lombok.Getter;

@Getter
public class Dino {
	private final String name;
	private final String color;
	private final int exp;
	private final Long userId;
	private final int Lv;
	private final int point;

	public Dino(String name, String color, int exp, Long userId, int Lv, int point) {
		this.name = name;
		this.color = color;
		this.exp = exp;
		this.userId = userId;
		this.Lv = Lv;
		this.point = point;
	}
}
