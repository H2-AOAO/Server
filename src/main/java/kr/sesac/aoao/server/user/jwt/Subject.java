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

	/**
	 * accessToken user 객체 생성
	 *
	 * @return TokenResponse
	 * @author 이상민
	 * @since 2024.01.22
	 */
	public static Subject accessToken(Long userId, String email, String nickname) {
		return new Subject(userId, email, nickname, "AccessToken");
	}

	/**
	 * refreshToken user 객체 생성
	 *
	 * @return TokenResponse
	 * @author 이상민
	 * @since 2024.01.22
	 */
	public static Subject refreshToken(Long userId, String email, String nickname) {
		return new Subject(userId, email, nickname, "RefreshToken");
	}
}