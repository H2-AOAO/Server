package kr.sesac.aoao.server.user.domain;

import kr.sesac.aoao.server.user.controller.dto.request.SignUpRequest;
import kr.sesac.aoao.server.user.repository.Role;
import lombok.Builder;
import lombok.Getter;

@Getter
public class User {
	private final String email;
	private final String nickname;
	private final String password;
	private final String profile;
	private final Role role;

	@Builder
	public User(String email, String nickname, String password, String profile, Role role) {
		this.email = email;
		this.nickname = nickname;
		this.password = password;
		this.profile = profile;
		this.role = role;
	}

	public static User from(SignUpRequest signUpRequest){
		return User.builder()
			.nickname(signUpRequest.getNickname())
			.email(signUpRequest.getEmail())
			.password(signUpRequest.getPassword())
			.role(Role.USER)
			.build();
	}
}
