package kr.sesac.aoao.server.todo.controller.dto.response;

import java.util.List;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class FolderQueryDetailResponse {

    private final List<FolderDetailResponse> folders;
}
