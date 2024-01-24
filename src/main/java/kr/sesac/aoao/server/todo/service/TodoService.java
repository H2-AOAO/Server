package kr.sesac.aoao.server.todo.service;

import kr.sesac.aoao.server.todo.controller.dto.request.TodoSaveRequest;

public interface TodoService {

    Long save(Long userId, Long folderId, TodoSaveRequest request);
}
