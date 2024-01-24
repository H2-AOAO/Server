package kr.sesac.aoao.server.todo.controller;

import java.net.URI;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kr.sesac.aoao.server.global.controller.dto.response.ApplicationResponse;
import kr.sesac.aoao.server.todo.controller.dto.request.TodoSaveRequest;
import kr.sesac.aoao.server.todo.controller.dto.request.TodoUpdateRequest;
import kr.sesac.aoao.server.todo.service.TodoService;
import kr.sesac.aoao.server.user.jwt.UserCustomDetails;
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

    /**
     * 투두 생성 API
     * @since 2024.01.23
     * @parameter UserCustomDetails, Long, TodoSaveRequest
     * @return ResponseEntity<ApplicationResponse<Void>>
     * @author 김유빈
     */
    @PostMapping
    public ResponseEntity<ApplicationResponse<Void>> save(
        @AuthenticationPrincipal UserCustomDetails userDetails,
        @PathVariable Long folderId,
        @RequestBody TodoSaveRequest request) {
        Long todoId = todoService.save(userDetails, folderId, request);
        return ResponseEntity.created(URI.create(String.format("/folders/%d/todos/%d", folderId, todoId))).build();
    }

    /**
     * 투두 수정 API
     * @since 2024.01.24
     * @parameter UserCustomDetails, Long, Long, TodoUpdateRequest
     * @return ResponseEntity<ApplicationResponse<Void>>
     * @author 김유빈
     */
    @PostMapping("/{todoId}")
    public ResponseEntity<ApplicationResponse<Void>> update(
        @AuthenticationPrincipal UserCustomDetails userDetails,
        @PathVariable Long folderId,
        @PathVariable Long todoId,
        @RequestBody TodoUpdateRequest request) {
        todoService.update(userDetails, folderId, todoId, request);
        return ResponseEntity.ok().build();
    }

    /**
     * 투두 삭제 API
     * @since 2024.01.24
     * @parameter UserCustomDetails, Long, Long
     * @return ResponseEntity<ApplicationResponse<Void>>
     * @author 김유빈
     */
    @DeleteMapping("/{todoId}")
    public ResponseEntity<ApplicationResponse<Void>> delete(
        @AuthenticationPrincipal UserCustomDetails userDetails,
        @PathVariable Long folderId,
        @PathVariable Long todoId) {
        todoService.delete(userDetails, folderId, todoId);
        return ResponseEntity.noContent().build();
    }

    /**
     * 투두 체크 API
     * @since 2024.01.24
     * @parameter UserCustomDetails, Long, Long
     * @return ResponseEntity<ApplicationResponse<Void>>
     * @author 김유빈
     */
    @PostMapping("/{todoId}/check")
    public ResponseEntity<ApplicationResponse<Void>> check(
        @AuthenticationPrincipal UserCustomDetails userDetails,
        @PathVariable Long folderId,
        @PathVariable Long todoId) {
        todoService.check(userDetails, folderId, todoId);
        return ResponseEntity.ok().build();
    }

    /**
     * 투두 체크 취소 API
     * @since 2024.01.24
     * @parameter UserCustomDetails, Long, Long
     * @return ResponseEntity<ApplicationResponse<Void>>
     * @author 김유빈
     */
    @PostMapping("/{todoId}/uncheck")
    public ResponseEntity<ApplicationResponse<Void>> uncheck(
        @AuthenticationPrincipal UserCustomDetails userDetails,
        @PathVariable Long folderId,
        @PathVariable Long todoId) {
        todoService.uncheck(userDetails, folderId, todoId);
        return ResponseEntity.ok().build();
    }
}
