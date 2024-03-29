package kr.sesac.aoao.server.user.controller.dto.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class TokenResponse {

	private final Long userId;
	private final String accessToken;
	private final String refreshToken;
}
