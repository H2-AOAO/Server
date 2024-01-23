package kr.sesac.aoao.server.todo.domain;

import lombok.Getter;

@Getter
public enum Palette {

	YELLOW("#F3F705"),
	ORANGE("#FA602F"),
	GREEN("#8EF705"),
	PURPLE("#B92FFA"),
	HOT_PINK("#FA2F7D"),
	MINT("#31F5D7"),
	SKY_BLUE("#31D2F5"),
	BLUE("#3194F5"),
	;

	Palette(String colorCode) {
		this.colorCode = colorCode;
	}

	private final String colorCode;
}
