package kr.sesac.aoao.server.todo.controller.dto.response;

import kr.sesac.aoao.server.todo.repository.TodoEntity;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class TodoDetailResponse {

    private final long todoId;
    private final String content;
    private final boolean checked;

    public static TodoDetailResponse from(TodoEntity todo) {
        return new TodoDetailResponse(
            todo.getId(),
            todo.getContent(),
            todo.isChecked()
        );
    }
}
