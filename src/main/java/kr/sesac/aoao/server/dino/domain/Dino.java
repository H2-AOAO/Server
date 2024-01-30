package kr.sesac.aoao.server.dino.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
/**
 * 다이노
 * @since 2024.01.19
 * @author 김은서
 */
@Getter
@AllArgsConstructor
public class Dino {

	private final String name;
	private final String color;
	private final int exp;
	private final Long userId;
	private final int Lv;
}
