package kr.sesac.aoao.server.user.controller.dto.request.ouath;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class AccessToken {
	private String accessToken;
}
