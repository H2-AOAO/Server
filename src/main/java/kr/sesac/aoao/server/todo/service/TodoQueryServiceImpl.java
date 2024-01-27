package kr.sesac.aoao.server.todo.service;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.sesac.aoao.server.global.exception.ApplicationException;
import kr.sesac.aoao.server.todo.controller.dto.response.FolderDetailResponse;
import kr.sesac.aoao.server.todo.controller.dto.response.FolderQueryDetailResponse;
import kr.sesac.aoao.server.todo.controller.dto.response.PaletteDetailResponse;
import kr.sesac.aoao.server.todo.controller.dto.response.PaletteQueryDetailResponse;
import kr.sesac.aoao.server.todo.controller.dto.response.TodoFolderDetailResponse;
import kr.sesac.aoao.server.todo.controller.dto.response.TodoQueryDetailResponse;
import kr.sesac.aoao.server.todo.exception.TodoErrorCode;
import kr.sesac.aoao.server.todo.repository.PaletteEntity;
import kr.sesac.aoao.server.todo.repository.PaletteJpaRepository;
import kr.sesac.aoao.server.todo.repository.TodoEntity;
import kr.sesac.aoao.server.todo.repository.TodoFolderEntity;
import kr.sesac.aoao.server.todo.repository.TodoFolderJpaRepository;
import kr.sesac.aoao.server.user.exception.UserErrorCode;
import kr.sesac.aoao.server.user.jwt.UserCustomDetails;
import kr.sesac.aoao.server.user.repository.UserEntity;
import kr.sesac.aoao.server.user.repository.UserJpaRepository;
import lombok.RequiredArgsConstructor;

/**
 * @since 2024.01.24
 * @author 김유빈
 */
@RequiredArgsConstructor
@Transactional
@Service
public class TodoQueryServiceImpl implements TodoQueryService {

	private final TodoFolderJpaRepository todoFolderJpaRepository;
	private final UserJpaRepository userJpaRepository;
	private final PaletteJpaRepository paletteJpaRepository;

	/**
	 * 투두리스트 조회
	 * @since 2024.01.24
	 * @parameter UserCustomDetails, String
	 * @author 김유빈
	 */
	@Override
	public TodoQueryDetailResponse findAllTodos(UserCustomDetails userDetails, String date) {
		Long userId = extractUserId(userDetails);
		UserEntity savedUser = findUserById(userId);
		List<TodoFolderEntity> folders = todoFolderJpaRepository.findBySelectedDateAndUser(convertDate(date), savedUser);
		int check = countCheckedTodo(folders);
		return new TodoQueryDetailResponse(
			check,
			folders.stream()
				.map(TodoFolderDetailResponse::from)
				.toList()
		);
	}

	/**
	 * 폴더리스트 조회
	 * @since 2024.01.24
	 * @parameter UserCustomDetails, String
	 * @author 김유빈
	 */
	@Override
	public FolderQueryDetailResponse findAllFolders(UserCustomDetails userDetails, String date) {
		Long userId = extractUserId(userDetails);
		UserEntity savedUser = findUserById(userId);
		List<TodoFolderEntity> folders = todoFolderJpaRepository.findBySelectedDateAndUser(convertDate(date), savedUser);
		return new FolderQueryDetailResponse(
			folders.stream()
				.map(FolderDetailResponse::from)
				.toList()
		);
	}

	/**
	 * 팔레트 리스트 조회
	 * @since 2024.01.24
	 * @parameter
	 * @author 김유빈
	 */
	@Override
	public PaletteQueryDetailResponse findAllPalettes() {
		List<PaletteEntity> palettes = paletteJpaRepository.findAll();
		return new PaletteQueryDetailResponse(
			palettes.stream()
				.map(PaletteDetailResponse::from)
				.toList()
		);
	}

	private int countCheckedTodo(List<TodoFolderEntity> todoFolders) {
		int count = 0;
		for (TodoFolderEntity todoFolder : todoFolders) {
			for (TodoEntity todo : todoFolder.getTodos()) {
				if (todo.isChecked()) {
					count++;
				}
			}
		}
		return count;
	}

	private LocalDate convertDate(String date) {
		int[] dates = Arrays.stream(date.split("-"))
			.mapToInt(Integer::parseInt)
			.toArray();
		if (dates.length != 3) {
			throw new ApplicationException(TodoErrorCode.INVALID_DATE_FORMAT);
		}
		return LocalDate.of(dates[0], dates[1], dates[2]);
	}

	private UserEntity findUserById(Long userId) {
		return userJpaRepository.findById(userId)
			.orElseThrow(() -> new ApplicationException(UserErrorCode.NOT_EXIST));
	}

	private Long extractUserId(UserCustomDetails userCustomDetails) {
		return userCustomDetails.getUserEntity().getId();
	}
}
