package kr.sesac.aoao.server.todo.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.sesac.aoao.server.global.exception.ApplicationException;
import kr.sesac.aoao.server.todo.controller.dto.request.TodoSaveRequest;
import kr.sesac.aoao.server.todo.controller.dto.request.TodoUpdateRequest;
import kr.sesac.aoao.server.todo.exception.TodoErrorCode;
import kr.sesac.aoao.server.todo.exception.TodoFolderErrorCode;
import kr.sesac.aoao.server.todo.repository.TodoEntity;
import kr.sesac.aoao.server.todo.repository.TodoFolderEntity;
import kr.sesac.aoao.server.todo.repository.TodoFolderJpaRepository;
import kr.sesac.aoao.server.todo.repository.TodoJpaRepository;
import kr.sesac.aoao.server.user.exception.UserErrorCode;
import kr.sesac.aoao.server.user.jwt.UserCustomDetails;
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
     * @parameter UserCustomDetails, Long, TodoFolderSaveRequest
     * @return Long
     * @author 김유빈
     */
    @Override
    public Long save(UserCustomDetails userDetails, Long folderId, TodoSaveRequest request) {
        Long userId = extractUserId(userDetails);
        UserEntity savedUser = findUserById(userId);
        TodoFolderEntity savedTodoFolder = findTodoFolderById(folderId);
        TodoEntity todo = TodoEntity.save(request.getContent(), savedTodoFolder, savedUser);
        return todoJpaRepository.save(todo).getId();
    }

    /**
     * 투두 수정
     * @since 2024.01.24
     * @parameter UserCustomDetails, Long, Long, TodoUpdateRequest
     * @author 김유빈
     */
    @Override
    public void update(UserCustomDetails userDetails, Long folderId, Long todoId, TodoUpdateRequest request) {
        Long userId = extractUserId(userDetails);
        UserEntity savedUser = findUserById(userId);
        TodoFolderEntity savedTodoFolder = findTodoFolderById(folderId);
        TodoEntity savedTodo = findTodoById(todoId);

        savedTodo.update(request.getContent(), savedTodoFolder, savedUser);
    }

    /**
     * 투두 삭제
     * @since 2024.01.24
     * @parameter UserCustomDetails, Long, Long
     * @author 김유빈
     */
    @Override
    public void delete(UserCustomDetails userDetails, Long folderId, Long todoId) {
        Long userId = extractUserId(userDetails);
        UserEntity savedUser = findUserById(userId);
        TodoFolderEntity savedTodoFolder = findTodoFolderById(folderId);
        TodoEntity savedTodo = findTodoById(todoId);

        savedTodo.validateUserIsWriter(savedTodoFolder, savedUser);
        todoJpaRepository.deleteById(savedTodo.getId());
    }

    /**
     * 투두 체크
     * @since 2024.01.24
     * @parameter UserCustomDetails, Long, Long
     * @author 김유빈
     */
    @Override
    public void check(UserCustomDetails userDetails, Long folderId, Long todoId) {
        Long userId = extractUserId(userDetails);
        UserEntity savedUser = findUserById(userId);
        TodoFolderEntity savedTodoFolder = findTodoFolderById(folderId);
        TodoEntity savedTodo = findTodoById(todoId);

        savedTodo.check(savedTodoFolder, savedUser);
    }

    private UserEntity findUserById(Long userId) {
        return userJpaRepository.findById(userId)
            .orElseThrow(() -> new ApplicationException(UserErrorCode.NOT_EXIST));
    }

    private TodoFolderEntity findTodoFolderById(Long folderId) {
        return todoFolderJpaRepository.findById(folderId)
            .orElseThrow(() -> new ApplicationException(TodoFolderErrorCode.NOT_EXIST));
    }

    private TodoEntity findTodoById(Long todoId) {
        return todoJpaRepository.findById(todoId)
            .orElseThrow(() -> new ApplicationException(TodoErrorCode.NOT_EXIST));
    }

    private Long extractUserId(UserCustomDetails userCustomDetails) {
        return userCustomDetails.getUserEntity().getId();
    }
}
