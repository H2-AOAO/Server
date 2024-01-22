package kr.sesac.aoao.server.todo.service;

import kr.sesac.aoao.server.todo.controller.dto.request.TodoFolderSaveRequest;

public interface TodoFolderService {

    Long save(Long userId, TodoFolderSaveRequest request);
}
