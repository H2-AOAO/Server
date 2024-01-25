package kr.sesac.aoao.server.dino.controller.dto.request;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class RenameRequest {
	private final String name;
	private final String dummy; //파싱용 더미값
}
