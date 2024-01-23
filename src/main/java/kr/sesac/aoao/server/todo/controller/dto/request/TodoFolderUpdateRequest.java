package kr.sesac.aoao.server.todo.controller.dto.request;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class TodoFolderUpdateRequest {

    private final String content;
    private final Long paletteId;
}
