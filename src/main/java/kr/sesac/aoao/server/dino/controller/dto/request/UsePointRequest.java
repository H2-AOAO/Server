package kr.sesac.aoao.server.dino.controller.dto.request;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class UsePointRequest {
	private final Long itemId;
	private final String dummy; //파싱용 더미값
}
