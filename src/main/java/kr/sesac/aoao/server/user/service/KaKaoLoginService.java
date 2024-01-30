package kr.sesac.aoao.server.user.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.sesac.aoao.server.user.controller.client.KakaoApiClient;
import kr.sesac.aoao.server.user.controller.dto.request.ouath.AccessToken;
import kr.sesac.aoao.server.user.controller.dto.response.TokenResponse;
import kr.sesac.aoao.server.user.domain.User;
import kr.sesac.aoao.server.user.jwt.JwtTokenProvider;
import kr.sesac.aoao.server.user.repository.UserEntity;
import kr.sesac.aoao.server.user.repository.UserJpaRepository;
import lombok.RequiredArgsConstructor;

/**
 * @author 이상민
 * @since 2024.01.24
 */
@Service
@RequiredArgsConstructor
@Transactional
public class KaKaoLoginService {

	private final UserJpaRepository userJpaRepository;
	private final JwtTokenProvider jwtTokenProvider;
	private final KakaoApiClient kakaoApiClient;

	/**
	 * access Token 으로 카카오 로그인
	 *
	 * @param token
	 * @return TokenResponse
	 * @author 이상민
	 * @since 2024.01.26
	 */
	public TokenResponse accessTokenLogin(AccessToken token) {
		User kakaoInfo = kakaoApiClient.requestOauthInfo(token);
		User user = findOrCreateMember(kakaoInfo);

		System.out.println(user);
		return jwtTokenProvider.createTokensLogin(user);
	}

	/**
	 * users 테이블에 유저 찾아 로그인 (존재하지 않다면 생성)
	 *
	 * @param kakaoInfo
	 * @return TokenResponse
	 * @author 이상민
	 * @since 2024.01.26
	 */
	private User findOrCreateMember(User kakaoInfo) {
		return userJpaRepository.findByEmail(kakaoInfo.getEmail())
			.map(UserEntity::toModel)
			.orElseGet(() -> createUser(kakaoInfo));
	}

	private User createUser(User kakaoInfo) {
		return userJpaRepository.save(new UserEntity(kakaoInfo)).toModel();
	}
}
