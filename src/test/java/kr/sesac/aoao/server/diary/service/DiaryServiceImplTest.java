package kr.sesac.aoao.server.diary.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import kr.sesac.aoao.server.diary.repository.DiaryJpaRepository;
import kr.sesac.aoao.server.user.domain.User;
import kr.sesac.aoao.server.user.repository.UserJpaRepository;

@ExtendWith(MockitoExtension.class)
class DiaryServiceImplTest {

	private DiaryService diaryService;

	@Mock
	private UserJpaRepository userRepository;

	@Mock
	private DiaryJpaRepository diaryRepository;

	@DisplayName("user가 존재하지 않으면 에러 발생")
	@Test
	void getDiaryInfo_notfoundUser() {
		// given
		Long userId = 1L;

		// when
		// User user = new User();
		// when(userRepository.findById(userId))
		// 	.thenReturn(Optional.of(new UserEntity(user)));

		// then
	}

	@Test
	void createDiary() {
		// given
		long date = 20240124;
		User user = new User(1L, "email", "nickname", "password", "password2", "profile");
		// DiaryCreateRequest diaryCreateRequest = new DiaryCreateRequest(1L, date, "hi");

		// when
		// DiaryEntity diaryEntity = new DiaryEntity(diaryCreateRequest);
		// when(diaryEntity.getId()).thenReturn(diaryEntity);
		// when(diaryEntity.save(any()))
		// 	.thenReturn(savedTodoFolder);

		// then
	}
}