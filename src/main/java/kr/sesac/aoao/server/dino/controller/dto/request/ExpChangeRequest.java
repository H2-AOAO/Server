package kr.sesac.aoao.server.dino.controller.dto.request;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ExpChangeRequest {
	private final Integer currLv;
	private final Integer currExp;
}
