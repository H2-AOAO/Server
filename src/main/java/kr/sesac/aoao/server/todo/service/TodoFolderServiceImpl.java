package kr.sesac.aoao.server.todo.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.sesac.aoao.server.global.exception.ApplicationException;
import kr.sesac.aoao.server.todo.controller.dto.request.TodoFolderSaveRequest;
import kr.sesac.aoao.server.todo.controller.dto.request.TodoFolderUpdateRequest;
import kr.sesac.aoao.server.todo.exception.PaletteErrorCode;
import kr.sesac.aoao.server.todo.exception.TodoFolderErrorCode;
import kr.sesac.aoao.server.todo.repository.PaletteEntity;
import kr.sesac.aoao.server.todo.repository.PaletteJpaRepository;
import kr.sesac.aoao.server.todo.repository.TodoFolderEntity;
import kr.sesac.aoao.server.todo.repository.TodoFolderJpaRepository;
import kr.sesac.aoao.server.user.exception.UserErrorCode;
import kr.sesac.aoao.server.user.repository.UserEntity;
import kr.sesac.aoao.server.user.repository.UserJpaRepository;
import lombok.RequiredArgsConstructor;

/**
 * @since 2024.01.19
 * @author 김유빈
 */
@RequiredArgsConstructor
@Transactional
@Service
public class TodoFolderServiceImpl implements TodoFolderService {

    private final TodoFolderJpaRepository todoFolderJpaRepository;
    private final UserJpaRepository userJpaRepository;
    private final PaletteJpaRepository paletteJpaRepository;

    /**
     * 투두 폴더 생성
     * @since 2024.01.19
     * @parameter Long, TodoFolderSaveRequest
     * @return Long
     * @author 김유빈
     */
    @Override
    public Long save(Long userId, TodoFolderSaveRequest request) {
        UserEntity savedUser = findUserById(userId);
        PaletteEntity savedPalette = findPaletteById(request.getPaletteId());

        TodoFolderEntity todoFolder = new TodoFolderEntity(
            request.getContent(),
            request.getDate(),
            savedUser,
            savedPalette
        );
        return todoFolderJpaRepository.save(todoFolder).getId();
    }

    /**
     * 투두 폴더 수정
     * @since 2024.01.22
     * @parameter Long, Long, TodoFolderSaveRequest
     * @author 김유빈
     */
    @Override
    public void update(Long userId, Long folderId, TodoFolderUpdateRequest request) {
        UserEntity savedUser = findUserById(userId);
        PaletteEntity savedPalette = findPaletteById(request.getPaletteId());
        TodoFolderEntity savedTodoFolder = findTodoFolderById(folderId);

        savedTodoFolder.update(savedUser, request.getContent(), savedPalette);
    }

    /**
     * 투두 폴더 삭제
     * @since 2024.01.22
     * @parameter Long, Long
     * @author 김유빈
     */
    @Override
    public void delete(Long userId, Long folderId) {
        UserEntity savedUser = findUserById(userId);
        TodoFolderEntity savedTodoFolder = findTodoFolderById(folderId);

        savedTodoFolder.validateUserIsWriter(savedUser);
        todoFolderJpaRepository.deleteById(savedTodoFolder.getId());
    }

    private UserEntity findUserById(Long userId) {
        return userJpaRepository.findById(userId)
            .orElseThrow(() -> new ApplicationException(UserErrorCode.NOT_EXIST));
    }

    private PaletteEntity findPaletteById(Long paletteId) {
        return paletteJpaRepository.findById(paletteId)
            .orElseThrow(() -> new ApplicationException(PaletteErrorCode.NOT_EXIST));
    }

    private TodoFolderEntity findTodoFolderById(Long folderId) {
        return todoFolderJpaRepository.findById(folderId)
            .orElseThrow(() -> new ApplicationException(TodoFolderErrorCode.NOT_EXIST));
    }
}
