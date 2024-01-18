package kr.sesac.aoao.server.user.controller.dto.response;

import kr.sesac.aoao.server.user.domain.User;
import kr.sesac.aoao.server.user.repository.Role;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class SignUpResponse {
	private Long userId;
	private String email;
	private String nickname;
	private String profile;
	private Role role;

	public static SignUpResponse from(User user){
		return SignUpResponse.builder()
			.userId(user.getUserId())
			.email(user.getEmail())
			.nickname(user.getNickname())
			.profile(user.getProfile())
			.role(user.getRole())
			.build();
	}
}
