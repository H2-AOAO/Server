package kr.sesac.aoao.server.user.jwt;

import lombok.Getter;

@Getter
public class Subject {

	private final Long userId;
	private final String email;
	private final String nickname;
	private final String type;

	private Subject(Long userId, String email, String nickname, String type) {
		this.userId = userId;
		this.email = email;
		this.nickname = nickname;
		this.type = type;
	}

	public static Subject atk(Long userId, String email, String nickname) {
		return new Subject(userId, email, nickname, "AccessToken");
	}

	public static Subject rtk(Long userId, String email, String nickname) {
		return new Subject(userId, email, nickname, "RefreshToken");
	}
}