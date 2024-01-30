package kr.sesac.aoao.server.todo.service;

import kr.sesac.aoao.server.todo.controller.dto.response.FolderQueryDetailResponse;
import kr.sesac.aoao.server.todo.controller.dto.response.PaletteQueryDetailResponse;
import kr.sesac.aoao.server.todo.controller.dto.response.TodoQueryDetailResponse;
import kr.sesac.aoao.server.user.jwt.UserCustomDetails;

/**
 * @since 2024.01.24
 * @author 김유빈
 */
public interface TodoQueryService {

	TodoQueryDetailResponse findAllTodos(UserCustomDetails userDetails, String date);

	FolderQueryDetailResponse findAllFolders(UserCustomDetails userDetails, String date);

	PaletteQueryDetailResponse findAllPalettes();
}
