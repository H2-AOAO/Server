package kr.sesac.aoao.server.user.controller.dto.response;

import kr.sesac.aoao.server.user.domain.User;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class SignUpResponse {

	private final Long userId;
	private final String email;
	private final String nickname;
	private final String profile;

	public SignUpResponse(User user) {
		this.userId = user.getId();
		this.email = user.getEmail();
		this.nickname = user.getNickname();
		this.profile = user.getProfile();
	}
}
