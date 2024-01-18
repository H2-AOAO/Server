package kr.sesac.aoao.server.user.service;

import static kr.sesac.aoao.server.user.exception.UserErrorCode.*;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.sesac.aoao.server.global.exception.ApplicationException;
import kr.sesac.aoao.server.user.controller.dto.request.SignUpRequest;
import kr.sesac.aoao.server.user.infrastructure.UserEntityRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class UserServiceImpl implements UserService {

	private final UserEntityRepository userRepository;

	@Transactional
	@Override
	public Long signUp(SignUpRequest signUpRequest) {
		if (userRepository.findByEmail(signUpRequest.getEmail()).isPresent()) {
			throw new ApplicationException(EXISTENT_EMAIL);
		}
		return null;
	}
}
