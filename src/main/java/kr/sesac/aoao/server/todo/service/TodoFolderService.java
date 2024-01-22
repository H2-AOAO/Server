package kr.sesac.aoao.server.todo.service;

import kr.sesac.aoao.server.todo.controller.dto.request.TodoFolderSaveRequest;
import kr.sesac.aoao.server.todo.controller.dto.request.TodoFolderUpdateRequest;

public interface TodoFolderService {

    Long save(Long userId, TodoFolderSaveRequest request);

    void update(Long userId, Long folderId, TodoFolderUpdateRequest request);

    void delete(Long userId, Long folderId);
}
