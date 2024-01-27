package kr.sesac.aoao.server.user.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import kr.sesac.aoao.server.user.repository.UserType;
import lombok.Getter;

/**
 * Access Token 으로 요청한 프로필 응답 값 변환
 *
 * @author 이상민
 * @since 2024.01.26
 */
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class KakaoInfo {

	@JsonProperty("kakao_account")
	private KakaoAccount kakaoAccount;

	@Getter
	@JsonIgnoreProperties(ignoreUnknown = true)
	static class KakaoAccount {
		private KakaoProfile profile;
		private String email;
	}

	@Getter
	@JsonIgnoreProperties(ignoreUnknown = true)
	static class KakaoProfile {
		private String nickname;
		@JsonProperty("profile_image_url")
		private String profileImageUrl; // 추가된 부분
	}

	public String getEmail() {
		return kakaoAccount != null ? kakaoAccount.email : null;
	}

	public String getNickname() {
		return kakaoAccount != null && kakaoAccount.profile != null ? kakaoAccount.profile.nickname : null;
	}

	public String getProfileImage() {
		return kakaoAccount != null && kakaoAccount.profile != null ? kakaoAccount.profile.profileImageUrl : null;
	}

	public UserType getOAuthProvider() {
		return UserType.KAKAO;
	}
}
