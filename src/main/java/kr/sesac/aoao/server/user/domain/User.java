package kr.sesac.aoao.server.user.domain;

import org.springframework.security.crypto.password.PasswordEncoder;

import kr.sesac.aoao.server.user.controller.dto.request.SignUpRequest;
import kr.sesac.aoao.server.user.repository.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class User {

	private Long id;
	private final String email;
	private final String nickname;
	private String password;
	private String checkedPassword;

	public User(SignUpRequest signUpRequest) {
		this.id = null;
		this.email = signUpRequest.getEmail();
		this.nickname = signUpRequest.getNickname();
		this.password = signUpRequest.getPassword();
		this.checkedPassword = signUpRequest.getCheckedPassword();
	}

	public User(UserEntity userEntity) {
		this.id = userEntity.getId();
		this.email = userEntity.getEmail();
		this.nickname = userEntity.getNickname();
		this.password = userEntity.getPassword();
	}

	public User(KakaoInfo kakaoInfo) {
		this.email = kakaoInfo.getEmail();
		this.nickname = kakaoInfo.getNickname();
		this.password = "secret";
	}

	public User(KakaoInfo kakaoInfo) {
		this.email = kakaoInfo.getEmail();
		this.nickname = kakaoInfo.getNickname();
		this.profile = kakaoInfo.getProfileImage();
	}

	/**
	 * 비밀번호 암호화
	 *
	 * @author 이상민
	 * @since 2024.01.19
	 */
	public void encodePassword(String checkedPassword) {
		this.password = checkedPassword;
	}

	/**
	 * 비밀번호 확인
	 *
	 * @return boolean
	 * @author 이상민
	 * @since 2024.01.19
	 */
	public boolean checkPassword(PasswordEncoder passwordEncoder, String password) {
		return passwordEncoder.matches(password, this.password);
	}
}
