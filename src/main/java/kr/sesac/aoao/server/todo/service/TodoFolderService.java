package kr.sesac.aoao.server.todo.service;

import kr.sesac.aoao.server.todo.controller.dto.request.TodoFolderSaveRequest;
import kr.sesac.aoao.server.todo.controller.dto.request.TodoFolderUpdateRequest;
import kr.sesac.aoao.server.user.jwt.UserCustomDetails;

public interface TodoFolderService {

	Long save(UserCustomDetails userDetails, TodoFolderSaveRequest request);

	void update(UserCustomDetails userDetails, Long folderId, TodoFolderUpdateRequest request);

	void delete(UserCustomDetails userDetails, Long folderId);
}
