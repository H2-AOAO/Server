package kr.sesac.aoao.server.todo.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import kr.sesac.aoao.server.user.repository.UserEntity;

public interface TodoFolderJpaRepository extends JpaRepository<TodoFolderEntity, Long> {

	List<TodoFolderEntity> findBySelectedDateAndUser(LocalDate date, UserEntity user);
}
