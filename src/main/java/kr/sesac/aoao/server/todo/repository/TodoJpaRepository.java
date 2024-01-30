package kr.sesac.aoao.server.todo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @since 2024.01.24
 * @author 김유빈
 */
public interface TodoJpaRepository extends JpaRepository<TodoEntity, Long> {
}
