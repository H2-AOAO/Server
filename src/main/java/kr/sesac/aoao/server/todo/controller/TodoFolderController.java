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
import kr.sesac.aoao.server.todo.controller.dto.request.TodoFolderSaveRequest;
import kr.sesac.aoao.server.todo.controller.dto.request.TodoFolderUpdateRequest;
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
     * @parameter TodoFolderSaveRequest
     * @return ResponseEntity<ApplicationResponse<Void>>
     * @author 김유빈
     */
    @PostMapping
    public ResponseEntity<ApplicationResponse<Void>> save(@RequestParam Long userId, @RequestBody TodoFolderSaveRequest request) {
        Long folderId = todoFolderService.save(userId, request);
        return ResponseEntity.created(URI.create("/folders/" + folderId)).build();
    }

    /**
     * 투두 폴더 수정 API
     * @since 2024.01.22
     * @parameter Long, TodoFolderUpdateRequest
     * @return ResponseEntity<ApplicationResponse<Void>>
     * @author 김유빈
     */
    @PostMapping("/{folderId}")
    public ResponseEntity<ApplicationResponse<Void>> update(
        @RequestParam Long userId, @PathVariable Long folderId,
        @RequestBody TodoFolderUpdateRequest request) {
        todoFolderService.update(userId, folderId, request);
        return ResponseEntity.ok().build();
    }
}
