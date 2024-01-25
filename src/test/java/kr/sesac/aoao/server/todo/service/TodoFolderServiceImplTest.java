// package kr.sesac.aoao.server.todo.service;
//
// import static org.assertj.core.api.Assertions.*;
// import static org.mockito.ArgumentMatchers.*;
// import static org.mockito.Mockito.*;
//
// import java.time.LocalDate;
// import java.util.Optional;
//
// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.DisplayName;
// import org.junit.jupiter.api.Nested;
// import org.junit.jupiter.api.Test;
// import org.junit.jupiter.api.extension.ExtendWith;
// import org.mockito.Mock;
// import org.mockito.junit.jupiter.MockitoExtension;
//
// import kr.sesac.aoao.server.todo.controller.dto.request.TodoFolderSaveRequest;
// import kr.sesac.aoao.server.todo.exception.PaletteErrorCode;
// import kr.sesac.aoao.server.todo.repository.PaletteEntity;
// import kr.sesac.aoao.server.todo.repository.PaletteJpaRepository;
// import kr.sesac.aoao.server.todo.repository.TodoFolderEntity;
// import kr.sesac.aoao.server.todo.repository.TodoFolderJpaRepository;
// import kr.sesac.aoao.server.user.domain.User;
// import kr.sesac.aoao.server.user.exception.UserErrorCode;
// import kr.sesac.aoao.server.user.repository.UserEntity;
// import kr.sesac.aoao.server.user.repository.UserJpaRepository;
//
// @ExtendWith(MockitoExtension.class)
// class TodoFolderServiceImplTest {
//
// 	private TodoFolderService todoFolderService;
//
// 	@Mock
// 	private TodoFolderJpaRepository todoFolderJpaRepository;
//
// 	@Mock
// 	private UserJpaRepository userJpaRepository;
//
// 	@Mock
// 	private PaletteJpaRepository paletteJpaRepository;
//
// 	@BeforeEach
// 	void setUp() {
// 		todoFolderService = new TodoFolderServiceImpl(
// 			todoFolderJpaRepository, userJpaRepository, paletteJpaRepository
// 		);
// 	}
//
// 	@DisplayName("투두 폴더 생성 테스트")
// 	@Nested
// 	class save {
//
// 		@DisplayName("투두의 폴더를 생성한다")
// 		@Test
// 		void success() {
// 			// given
// 			long userId = 1L;
// 			long paletteId = 2L;
// 			long todoFolderId = 3L;
// 			TodoFolderSaveRequest request = new TodoFolderSaveRequest("content", LocalDate.now(), paletteId);
//
// 			// mock
// 			User user = new User(userId, "email", "nickname", "password", "password2", "profile");
// 			when(userJpaRepository.findById(userId))
// 				.thenReturn(Optional.of(new UserEntity(user)));
// 			when(paletteJpaRepository.findById(paletteId))
// 				.thenReturn(Optional.of(new PaletteEntity("blue")));
//
// 			TodoFolderEntity savedTodoFolder = mock(TodoFolderEntity.class);
// 			when(savedTodoFolder.getId()).thenReturn(todoFolderId);
// 			when(todoFolderJpaRepository.save(any()))
// 				.thenReturn(savedTodoFolder);
//
// 			// when
// 			// Long savedTodoFolderId = todoFolderService.save(userId, request);
//
// 			// then
// 			assertThat(savedTodoFolderId).isNotNull();
// 		}
//
// 		@DisplayName("사용자가 존재하지 않을 경우 예외가 발생한다")
// 		@Test
// 		void userIsNotExist() {
// 			// given
// 			long notExistUserId = 0L;
// 			TodoFolderSaveRequest request = new TodoFolderSaveRequest("content", LocalDate.now(), 1L);
//
// 			// mock
// 			when(userJpaRepository.findById(notExistUserId))
// 				.thenReturn(Optional.empty());
//
// 			// when & then
// 			assertThatThrownBy(() -> todoFolderService.save(notExistUserId, request))
// 				.hasMessage(UserErrorCode.NOT_EXIST.getMessage());
// 		}
//
// 		@DisplayName("팔레트가 존재하지 않을 경우 예외가 발생한다")
// 		@Test
// 		void paletteIsNotExist() {
// 			// given
// 			long userId = 1L;
// 			long notExistPaletteId = 0L;
// 			TodoFolderSaveRequest request = new TodoFolderSaveRequest("content", LocalDate.now(), notExistPaletteId);
//
// 			// mock
// 			User user = new User(userId, "email", "nickname", "password", "password2", "profile");
// 			when(userJpaRepository.findById(userId))
// 				.thenReturn(Optional.of(new UserEntity(user)));
// 			when(paletteJpaRepository.findById(notExistPaletteId))
// 				.thenReturn(Optional.empty());
//
// 			// when & then
// 			assertThatThrownBy(() -> todoFolderService.save(userId, request))
// 				.hasMessage(PaletteErrorCode.NOT_EXIST.getMessage());
// 		}
// 	}
// }
