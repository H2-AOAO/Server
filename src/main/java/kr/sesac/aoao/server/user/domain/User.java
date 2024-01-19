package kr.sesac.aoao.server.user.domain;

import org.springframework.security.crypto.password.PasswordEncoder;

import kr.sesac.aoao.server.user.controller.dto.request.SignUpRequest;
import kr.sesac.aoao.server.user.repository.Role;
import lombok.Builder;
import lombok.Getter;

@Getter
public class User {
	private final Long userId;
	private final String email;
	private final String nickname;
	private final String password;
	private final String profile;
	private final Role role;

	@Builder
	public User(Long userId, String email, String nickname, String password, String profile, Role role) {
		this.userId = userId;
		this.email = email;
		this.nickname = nickname;
		this.password = password;
		this.profile = profile;
		this.role = role;
	}

	public static User from(SignUpRequest signUpRequest, PasswordEncoder passwordEncoder) {
		return User.builder()
			.nickname(signUpRequest.getNickname())
			.email(signUpRequest.getEmail())
			.password(passwordEncoder.encode(signUpRequest.getPassword()))
			.role(Role.USER)
			.build();
	}

	/**
	 * 비밀번호 확인
	 * @since 2024.01.19
	 * @return boolean
	 * @author 이상민
	 */
	public boolean checkPassword(PasswordEncoder passwordEncoder, String password) {
		return passwordEncoder.matches(password, this.password);
	}

}
