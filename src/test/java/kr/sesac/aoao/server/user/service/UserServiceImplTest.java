package kr.sesac.aoao.server.user.service;

import static kr.sesac.aoao.server.user.exception.UserErrorCode.*;
import static org.assertj.core.api.AssertionsForClassTypes.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import kr.sesac.aoao.server.global.exception.ApplicationException;
import kr.sesac.aoao.server.user.controller.dto.request.SignUpRequest;
import kr.sesac.aoao.server.user.repository.UserEntity;
import kr.sesac.aoao.server.user.repository.UserJpaRepository;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

	@InjectMocks  // 가짜 객체 주입을 위한 것
	private UserServiceImpl userService;

	@Mock
	private UserJpaRepository userJpaRepository;

	@Spy
	private BCryptPasswordEncoder passwordEncoder;

	@Test
	void signUp은_이메일이_이미_존재하면_에러를_발생시킨다() {
		// given
		SignUpRequest signUpRequest = SignUpRequest.builder()
			.email("test1234@gmail.com")
			.nickname("테스터")
			.password("test1234")
			.checkedPassword("test1234")
			.build();

		// when
		UserEntity user = mock(UserEntity.class);
		when(userJpaRepository.findByEmail(signUpRequest.getEmail()))
			.thenReturn(Optional.of(user));

		// then
		assertThatThrownBy(() -> userService.signUp(signUpRequest))
			.isInstanceOf(ApplicationException.class)
			.hasMessageContaining(EXISTENT_EMAIL.getMessage());

	}

	@Test
	void signUp은_비밀번호와_확인비밀번호가_일치하지않으면_에러를_발생시킨다() {
		// given
		SignUpRequest signUpRequest = SignUpRequest.builder()
			.email("test1234@gmail.com")
			.nickname("테스터1")
			.password("test1234")
			.checkedPassword("111111aa")
			.build();

		// when
		// then
		assertThatThrownBy(() -> userService.signUp(signUpRequest))
			.isInstanceOf(ApplicationException.class)
			.hasMessageContaining(INVALID_PASSWORD.getMessage());

	}

}