package kr.sesac.aoao.server.dino.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;

import kr.sesac.aoao.server.dino.controller.dto.GetUserDinoResponse;
import kr.sesac.aoao.server.dino.domain.Dino;
import kr.sesac.aoao.server.dino.repository.DinoEntity;
import kr.sesac.aoao.server.dino.repository.DinoInfoEntity;
import kr.sesac.aoao.server.dino.repository.DinoJpaRepository;
import kr.sesac.aoao.server.user.repository.UserEntity;
import kr.sesac.aoao.server.user.repository.UserJpaRepository;

@ExtendWith(MockitoExtension.class)
public class DinoServiceImplTest {
	private DinoService dinoService;

	@Mock
	private DinoJpaRepository dinoRepository;

	@Mock
	private UserJpaRepository userRepository;

	@BeforeEach
	void setUp(){
		dinoService = new DinoServiceImpl(
			dinoRepository,userRepository
		);
	}

	@DisplayName("다이노 조회 테스트")
	@Nested
	class getDinoInfo{
		@DisplayName("정보 조회 성공")
		@Test
		void success(){
			long userId = 1L;
			long dinoId = 2L;
			UserEntity user = mock(UserEntity.class);

			//mock
			DinoEntity dino = mock(DinoEntity.class);
			DinoInfoEntity dinoInfo = mock(DinoInfoEntity.class);
			UserEntity user_ = new UserEntity(userId, "nickname", "email", "password", "profile", null, dino);
			when(userRepository.findById(userId))
				.thenReturn(Optional.of(user_));
			when(dinoRepository.findByUser(user_))
				.thenReturn(Optional.of(new DinoEntity(dinoId, user, "name", "green",234,30,dinoInfo)));

			// when
			GetUserDinoResponse res = dinoService.getDinoInfo(userId);

			// then
			assertThat(dinoId).isNotNull();



		}

		@DisplayName("유저 조회 시 오류 발생")
		@Test
		void getDinoInfo_user(){
			//given
			long notExistUserId = 0L;

			//mock
			when(userRepository.findById(notExistUserId))
				.thenReturn(Optional.empty());

			// when & then
			assertThatThrownBy(() -> dinoService.getDinoInfo(notExistUserId));
		}

		@DisplayName("다이노 조회 시 오류 발생")
		@Test
		void getDinoInfo_dino(){
			long notExistDinoId = 0L;
			long userId = 1L;

			//mock
			DinoEntity dino = mock(DinoEntity.class);
			when(userRepository.findById(userId))
				.thenReturn(Optional.of(new UserEntity(userId, "nickname", "email", "password", "profile", null, dino)));
			when(dinoRepository.findById(notExistDinoId))
				.thenReturn(Optional.empty());

			// when & then
			assertThatThrownBy(() -> dinoService.getDinoInfo(notExistDinoId));

		}
	}

}