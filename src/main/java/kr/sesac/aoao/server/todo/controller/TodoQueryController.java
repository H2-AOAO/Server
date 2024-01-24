package kr.sesac.aoao.server.todo.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import kr.sesac.aoao.server.global.controller.dto.response.ApplicationResponse;
import kr.sesac.aoao.server.todo.controller.dto.response.TodoQueryDetailResponse;
import kr.sesac.aoao.server.todo.service.TodoQueryService;
import kr.sesac.aoao.server.user.jwt.UserCustomDetails;
import lombok.RequiredArgsConstructor;

/**
 * @since 2024.01.23
 * @author 김유빈
 */
@RestController
@RequiredArgsConstructor
public class TodoQueryController {

    private final TodoQueryService todoQueryService;

    /**
     * 투두리스트 조회 API
     * @since 2024.01.24
     * @parameter UserCustomDetails, String
     * @return ResponseEntity<ApplicationResponse<TodoFindDetailResponse>>
     * @author 김유빈
     */
    @GetMapping("/todos")
    public ResponseEntity<ApplicationResponse<TodoQueryDetailResponse>> findAllTodos(
        @AuthenticationPrincipal UserCustomDetails userDetails,
        @RequestParam String date) {
        TodoQueryDetailResponse response = todoQueryService.findAllTodos(userDetails, date);
        return ResponseEntity.ok(ApplicationResponse.success(response));
    }
}
