package kr.sesac.aoao.server.todo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TodoFolderJpaRepository extends JpaRepository<TodoFolderEntity, Long> {
}
