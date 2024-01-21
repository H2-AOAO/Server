package kr.sesac.aoao.server.todo.controller;

import java.net.URI;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kr.sesac.aoao.server.global.controller.dto.response.ApplicationResponse;
import kr.sesac.aoao.server.todo.controller.dto.request.TodoFolderSaveRequest;
import kr.sesac.aoao.server.todo.service.TodoFolderService;
import lombok.RequiredArgsConstructor;

/**
 * @since 2024.01.19
 * @author 김유빈
 */
@RequestMapping("/folders")
@RestController
@RequiredArgsConstructor
public class TodoFolderController {

    private final TodoFolderService todoFolderService;

    // todo: userId -> token
    /**
     * 투두 폴더 생성 API
     * @since 2024.01.19
     * @parameter Long, TodoFolderSaveRequest
     * @return ResponseEntity<ApplicationResponse<Void>>
     * @author 김유빈
     */
    @PostMapping("/{userId}")
    public ResponseEntity<ApplicationResponse<Void>> save(@PathVariable Long userId, TodoFolderSaveRequest request) {
        Long folderId = todoFolderService.save(userId, request);
        return ResponseEntity.created(URI.create("/folders/" + folderId)).build();
    }
}
