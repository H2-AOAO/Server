package kr.sesac.aoao.server.todo.service;

import kr.sesac.aoao.server.todo.controller.dto.request.TodoSaveRequest;
import kr.sesac.aoao.server.todo.controller.dto.request.TodoUpdateRequest;

public interface TodoService {

    Long save(Long userId, Long folderId, TodoSaveRequest request);

    void update(Long userId, Long folderId, Long todoId, TodoUpdateRequest request);
}
