package kr.sesac.aoao.server.item.controller.dto.request;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ItemNumRequest {
	private final Long itemId;
	private final String status;
}
