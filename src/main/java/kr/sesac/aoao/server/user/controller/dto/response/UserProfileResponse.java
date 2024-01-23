package kr.sesac.aoao.server.user.controller.dto.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class UserProfileResponse {
	private final String nickname;
	private final String profile;
}
