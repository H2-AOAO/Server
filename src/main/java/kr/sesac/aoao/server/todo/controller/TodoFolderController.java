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
import kr.sesac.aoao.server.todo.controller.dto.request.TodoFolderSaveRequest;
import kr.sesac.aoao.server.todo.controller.dto.request.TodoFolderUpdateRequest;
import kr.sesac.aoao.server.todo.service.TodoFolderService;
import kr.sesac.aoao.server.user.jwt.UserCustomDetails;
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

    /**
     * 투두 폴더 생성 API
     * @since 2024.01.19
     * @parameter UserCustomDetails, TodoFolderSaveRequest
     * @return ResponseEntity<ApplicationResponse<Void>>
     * @author 김유빈
     */
    @PostMapping
    public ResponseEntity<ApplicationResponse<Void>> save(
        @AuthenticationPrincipal UserCustomDetails userDetails,
        @RequestBody TodoFolderSaveRequest request) {
        Long folderId = todoFolderService.save(userDetails, request);
        return ResponseEntity.created(URI.create("/folders/" + folderId)).build();
    }

    /**
     * 투두 폴더 수정 API
     * @since 2024.01.22
     * @parameter UserCustomDetails, Long, TodoFolderUpdateRequest
     * @return ResponseEntity<ApplicationResponse<Void>>
     * @author 김유빈
     */
    @PostMapping("/{folderId}")
    public ResponseEntity<ApplicationResponse<Void>> update(
        @AuthenticationPrincipal UserCustomDetails userDetails,
        @PathVariable Long folderId,
        @RequestBody TodoFolderUpdateRequest request) {
        todoFolderService.update(userDetails, folderId, request);
        return ResponseEntity.ok().build();
    }

    /**
     * 투두 폴더 삭제 API
     * @since 2024.01.22
     * @parameter UserCustomDetails, Long
     * @return ResponseEntity<ApplicationResponse<Void>>
     * @author 김유빈
     */
    @DeleteMapping("/{folderId}")
    public ResponseEntity<ApplicationResponse<Void>> delete(
        @AuthenticationPrincipal UserCustomDetails userDetails,
        @PathVariable Long folderId) {
        todoFolderService.delete(userDetails, folderId);
        return ResponseEntity.noContent().build();
    }
}
