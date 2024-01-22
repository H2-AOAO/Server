package kr.sesac.aoao.server.todo.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.sesac.aoao.server.global.exception.ApplicationException;
import kr.sesac.aoao.server.todo.controller.dto.request.TodoFolderSaveRequest;
import kr.sesac.aoao.server.todo.exception.PaletteErrorCode;
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
        UserEntity savedUser = userJpaRepository.findById(userId)
            .orElseThrow(() -> new ApplicationException(UserErrorCode.NOT_EXIST));
        PaletteEntity savedPalette = paletteJpaRepository.findById(request.getPaletteId())
            .orElseThrow(() -> new ApplicationException(PaletteErrorCode.NOT_EXIST));

        TodoFolderEntity todoFolder = new TodoFolderEntity(
            request.getContent(),
            request.getDate(),
            savedUser,
            savedPalette
        );
        return todoFolderJpaRepository.save(todoFolder).getId();
    }
}