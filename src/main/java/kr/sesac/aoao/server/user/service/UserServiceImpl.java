package kr.sesac.aoao.server.user.service;

import static kr.sesac.aoao.server.user.exception.UserErrorCode.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.sesac.aoao.server.global.exception.ApplicationException;
import kr.sesac.aoao.server.user.controller.dto.request.SignUpRequest;
import kr.sesac.aoao.server.user.domain.User;
import kr.sesac.aoao.server.user.jwt.JwtTokenProvider;
import kr.sesac.aoao.server.user.repository.UserEntity;
import kr.sesac.aoao.server.user.repository.UserJpaRepository;
import lombok.RequiredArgsConstructor;

/**
 * @since 2024.01.18
 * @author 이상민
 */
@RequiredArgsConstructor
@Transactional
@Service
public class UserServiceImpl implements UserService {

	private final UserJpaRepository userRepository;
	private final JwtTokenProvider jwtTokenProvider;
	private final PasswordEncoder passwordEncoder;

	/**
	 * 회원가입
	 * @since 2024.01.18
	 * @return User
	 * @author 이상민
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
	 * @since 2024.01.19
	 * @return String
	 * @author 이상민
	 */
	@Override
	@Transactional(readOnly = true)
	public String login(Map<String, String> users) {
		User user = userRepository.findByEmail(users.get("email"))
			.orElseThrow(() -> new ApplicationException(NOT_EXISTENT_EMAIL)).toModel();
		String password = users.get("password");
		if (!user.checkPassword(passwordEncoder, password)) {
			throw new ApplicationException(NOT_CORRECTED_PASSWORD);
		}
		List<String> roles = new ArrayList<>();
		roles.add(user.getRole().name());
		return jwtTokenProvider.createToken(user.getEmail(), roles);
	}
}
