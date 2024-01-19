package kr.sesac.aoao.server.user.controller.dto.response;

import lombok.Builder;

@Builder
public class JwtTokenResponse {

	private String accessToken;

	public static JwtTokenResponse from(String accessToken) {
		return JwtTokenResponse.builder()
			.accessToken(accessToken)
			.build();
	}

}
