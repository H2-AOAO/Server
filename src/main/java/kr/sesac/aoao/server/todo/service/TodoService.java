package kr.sesac.aoao.server.todo.service;

import kr.sesac.aoao.server.todo.controller.dto.request.TodoSaveRequest;
import kr.sesac.aoao.server.todo.controller.dto.request.TodoUpdateRequest;
import kr.sesac.aoao.server.user.jwt.UserCustomDetails;

/**
 * @since 2024.01.24
 * @author 김유빈
 */
public interface TodoService {

	long save(UserCustomDetails userDetails, Long folderId, TodoSaveRequest request);

	void update(UserCustomDetails userDetails, Long folderId, Long todoId, TodoUpdateRequest request);

	void delete(UserCustomDetails userDetails, Long folderId, Long todoId);

	void check(UserCustomDetails userDetails, Long folderId, Long todoId);

	void uncheck(UserCustomDetails userDetails, Long folderId, Long todoId);
}
