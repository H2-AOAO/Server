package kr.sesac.aoao.server.dino.controller.dto.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class FriendDinoInfo {
	private String name;
	private String color;
	private int level;

	public void saveName(String name){this.name = name;}
	public void saveColor(String color){this.color = color;}
	public void saveLevel(int lv){this.level = lv;}
}