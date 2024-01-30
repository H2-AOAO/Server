package kr.sesac.aoao.server.todo.domain;

import lombok.Getter;

/**
 * @since 2024.01.22
 * @author 김유빈
 */
@Getter
public enum Palette {

	YELLOW("yellow"),
	ORANGE("orange"),
	GREEN("green"),
	SKY_BLUE("sky_blue"),
	PINK("pink"),
	RED("red"),
	PURPLE("purple"),
	BLUE("blue"),
	LIGHT_GREEN("light_green"),
	MINT("mint"),
	;

	Palette(String colorCode) {
		this.colorCode = colorCode;
	}

	private final String colorCode;
}
