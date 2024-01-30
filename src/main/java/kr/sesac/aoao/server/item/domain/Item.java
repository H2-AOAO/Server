package kr.sesac.aoao.server.item.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
/**
 * @since 2024.01.22
 * @author 김은서
 */

@Getter
@AllArgsConstructor
public class Item {

	private final Long id;
	private final String name;
	private final int price;
	private final int exp;
}

