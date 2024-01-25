package kr.sesac.aoao.server.item.controller.dto.request;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class GetItemInfoRequest {
	private final long itemId;
	private final String dummy; //파싱용 더미값
}
