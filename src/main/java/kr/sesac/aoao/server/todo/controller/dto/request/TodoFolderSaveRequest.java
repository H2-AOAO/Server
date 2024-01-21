package kr.sesac.aoao.server.todo.controller.dto.request;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class TodoFolderSaveRequest {

    private final String content;
    private final LocalDateTime date;
    private final Long paletteId;
}
