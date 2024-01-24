package kr.sesac.aoao.server.todo.controller;

import java.net.URI;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import kr.sesac.aoao.server.global.controller.dto.response.ApplicationResponse;
import kr.sesac.aoao.server.todo.controller.dto.request.TodoSaveRequest;
import kr.sesac.aoao.server.todo.controller.dto.request.TodoUpdateRequest;
import kr.sesac.aoao.server.todo.service.TodoService;
import lombok.RequiredArgsConstructor;

/**
 * @since 2024.01.23
 * @author 김유빈
 */
@RequestMapping("/folders/{folderId}/todos")
@RestController
@RequiredArgsConstructor
public class TodoController {

    private final TodoService todoService;

    // todo: userId -> token
    /**
     * 투두 생성 API
     * @since 2024.01.23
     * @parameter Long, Long, TodoSaveRequest
     * @return ResponseEntity<ApplicationResponse<Void>>
     * @author 김유빈
     */
    @PostMapping
    public ResponseEntity<ApplicationResponse<Void>> save(
        @RequestParam Long userId,
        @PathVariable Long folderId,
        @RequestBody TodoSaveRequest request) {
        Long todoId = todoService.save(userId, folderId, request);
        return ResponseEntity.created(URI.create(String.format("/folders/%d/todos/%d", folderId, todoId))).build();
    }

    /**
     * 투두 수정 API
     * @since 2024.01.24
     * @parameter Long, Long, Long, TodoUpdateRequest
     * @return ResponseEntity<ApplicationResponse<Void>>
     * @author 김유빈
     */
    @PostMapping("/{todoId}")
    public ResponseEntity<ApplicationResponse<Void>> update(
        @RequestParam Long userId,
        @PathVariable Long folderId,
        @PathVariable Long todoId,
        @RequestBody TodoUpdateRequest request) {
        todoService.update(userId, folderId, todoId, request);
        return ResponseEntity.ok().build();
    }
}
