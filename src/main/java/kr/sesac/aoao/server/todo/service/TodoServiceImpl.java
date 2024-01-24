package kr.sesac.aoao.server.todo.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.sesac.aoao.server.global.exception.ApplicationException;
import kr.sesac.aoao.server.todo.controller.dto.request.TodoSaveRequest;
import kr.sesac.aoao.server.todo.exception.TodoFolderErrorCode;
import kr.sesac.aoao.server.todo.repository.TodoEntity;
import kr.sesac.aoao.server.todo.repository.TodoFolderEntity;
import kr.sesac.aoao.server.todo.repository.TodoFolderJpaRepository;
import kr.sesac.aoao.server.todo.repository.TodoJpaRepository;
import kr.sesac.aoao.server.user.exception.UserErrorCode;
import kr.sesac.aoao.server.user.repository.UserEntity;
import kr.sesac.aoao.server.user.repository.UserJpaRepository;
import lombok.RequiredArgsConstructor;

/**
 * @since 2024.01.23
 * @author 김유빈
 */
@RequiredArgsConstructor
@Transactional
@Service
public class TodoServiceImpl implements TodoService {

    private final TodoJpaRepository todoJpaRepository;
    private final TodoFolderJpaRepository todoFolderJpaRepository;
    private final UserJpaRepository userJpaRepository;

    /**
     * 투두 생성
     * @since 2024.01.23
     * @parameter Long, Long, TodoFolderSaveRequest
     * @return Long
     * @author 김유빈
     */
    @Override
    public Long save(Long userId, Long folderId, TodoSaveRequest request) {
        UserEntity savedUser = findUserById(userId);
        TodoFolderEntity savedTodoFolder = findTodoFolderById(folderId);
        TodoEntity todo = TodoEntity.save(request.getContent(), savedTodoFolder, savedUser);
        return todoJpaRepository.save(todo).getId();
    }

    private UserEntity findUserById(Long userId) {
        return userJpaRepository.findById(userId)
            .orElseThrow(() -> new ApplicationException(UserErrorCode.NOT_EXIST));
    }

    private TodoFolderEntity findTodoFolderById(Long folderId) {
        return todoFolderJpaRepository.findById(folderId)
            .orElseThrow(() -> new ApplicationException(TodoFolderErrorCode.NOT_EXIST));
    }
}
