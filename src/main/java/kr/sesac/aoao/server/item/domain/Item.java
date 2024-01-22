package kr.sesac.aoao.server.item.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Item {
	private final Long id;
	private final String name;
	private final int price;
	private final int exp;


}
