package kr.sesac.aoao.server.todo.controller.dto.response;

import java.util.List;

import kr.sesac.aoao.server.todo.repository.TodoFolderEntity;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class TodoFolderDetailResponse {

	private final long folderId;
	private final String colorCode;
	private final String content;
	private final List<TodoDetailResponse> todos;

	public static TodoFolderDetailResponse from(TodoFolderEntity todoFolder) {
		return new TodoFolderDetailResponse(
			todoFolder.getId(),
			todoFolder.getPalette().getColorCode(),
			todoFolder.getContent(),
			todoFolder.getTodos().stream()
				.map(TodoDetailResponse::from)
				.toList()
		);
	}
}
