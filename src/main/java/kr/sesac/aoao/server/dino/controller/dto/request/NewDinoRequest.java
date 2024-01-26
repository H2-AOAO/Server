package kr.sesac.aoao.server.dino.controller.dto.request;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
@Getter
@RequiredArgsConstructor
public class NewDinoRequest {
	private final String dinoName;
	private final String dinoColor;
}
