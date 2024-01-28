package kr.sesac.aoao.server.todo.controller.dto.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class TodoSaveResponse {

    private final long todoId;
}
