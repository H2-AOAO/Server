package kr.sesac.aoao.server.dino.controller.dto.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class DinoSimpleInfo {
	private String name;
	private String color;

	public void saveName(String name){this.name = name;}
	public void saveColor(String color){this.color = color;}
}
