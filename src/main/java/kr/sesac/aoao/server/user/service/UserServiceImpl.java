package kr.sesac.aoao.server.user.service;

import static kr.sesac.aoao.server.user.exception.UserErrorCode.*;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.sesac.aoao.server.global.exception.ApplicationException;
import kr.sesac.aoao.server.user.controller.dto.request.LoginRequest;
import kr.sesac.aoao.server.user.controller.dto.request.SignUpRequest;
import kr.sesac.aoao.server.user.controller.dto.response.UserProfileResponse;
import kr.sesac.aoao.server.user.domain.User;
import kr.sesac.aoao.server.user.repository.UserEntity;
import kr.sesac.aoao.server.user.repository.UserJpaRepository;
import lombok.RequiredArgsConstructor;

/**
 * @author 이상민
 * @since 2024.01.18
 */
@RequiredArgsConstructor
@Transactional
@Service
public class UserServiceImpl implements UserService {

	private final UserJpaRepository userRepository;
	private final PasswordEncoder passwordEncoder;

	/**
	 * 회원가입
	 *
	 * @return User
	 * @author 이상민
	 * @since 2024.01.18
	 */
	@Override
	public User signUp(SignUpRequest signUpRequest) {
		// signUpRequest -> user
		User user = new User(signUpRequest);
		if (userRepository.findByEmail(user.getEmail()).isPresent()) {
			throw new ApplicationException(EXISTENT_EMAIL);
		}
		if (!user.getPassword().equals(user.getCheckedPassword())) {  // 비밀번호 중복확인
			throw new ApplicationException(INVALID_PASSWORD);
		}

		String encodePassword = passwordEncoder.encode(user.getPassword());
		user.encodePassword(encodePassword);
		return userRepository.save(new UserEntity(user)).toModel();
	}

	/**
	 * 로그인
	 *
	 * @return String
	 * @author 이상민
	 * @since 2024.01.19
	 */
	@Override
	@Transactional(readOnly = true)
	public User login(LoginRequest loginRequest) {
		User user = userRepository.findByEmail(loginRequest.getEmail())
			.orElseThrow(() -> new ApplicationException(NOT_EXISTENT_EMAIL)).toModel();
		if (!user.checkPassword(passwordEncoder, loginRequest.getPassword())) {
			throw new ApplicationException(NOT_CORRECTED_PASSWORD);
		}
		return user;
	}

	/**
	 * 프로필 조회
	 *
	 * @return UserProfileResponse
	 * @author 이상민
	 * @since 2024.01.22
	 */
	@Override
	public UserProfileResponse getProfile(String username, Long userId) {
		User user = userRepository.findByEmail(username)
			.orElseThrow(() -> new ApplicationException(NOT_EXISTENT_EMAIL)).toModel();
		return new UserProfileResponse(user.getNickname(), user.getProfile());
	}

}
