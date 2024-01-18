package kr.sesac.aoao.server.user.service;

import static kr.sesac.aoao.server.user.exception.UserErrorCode.*;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.sesac.aoao.server.global.exception.ApplicationException;
import kr.sesac.aoao.server.user.controller.dto.request.SignUpRequest;
import kr.sesac.aoao.server.user.domain.User;
import kr.sesac.aoao.server.user.repository.UserEntity;
import kr.sesac.aoao.server.user.repository.UserJpaRepository;
import lombok.RequiredArgsConstructor;

/**
 * @since 2024.01.18
 * @author 이상민
 */
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class UserServiceImpl implements UserService {

	private final UserJpaRepository userRepository;
	private final PasswordEncoder passwordEncoder;

	/**
	 * 회원가입
	 * @since 2024.01.18
	 * @return User
	 * @author 이상민
	 */
	@Transactional
	@Override
	public User signUp(SignUpRequest signUpRequest) {
		if (userRepository.findByEmail(signUpRequest.getEmail()).isPresent()) {
			throw new ApplicationException(EXISTENT_EMAIL);
		}
		if(!signUpRequest.getPassword().equals(signUpRequest.getCheckedPassword())){  // 비밀번호 중복확인
			throw new ApplicationException(INVALID_PASSWORD);
		}
		User user = User.from(signUpRequest, passwordEncoder);
		return userRepository.save(UserEntity.from(user)).toModel();
	}

}
