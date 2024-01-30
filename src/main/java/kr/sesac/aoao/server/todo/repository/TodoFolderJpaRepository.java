package kr.sesac.aoao.server.todo.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import kr.sesac.aoao.server.user.repository.UserEntity;

/**
 * @since 2024.01.22
 * @author 김유빈
 */
public interface TodoFolderJpaRepository extends JpaRepository<TodoFolderEntity, Long> {

	List<TodoFolderEntity> findBySelectedDateAndUser(LocalDate date, UserEntity user);

	@Query("SELECT todoFolder "
		+ "FROM TodoFolderEntity todoFolder "
		+ "WHERE YEAR(todoFolder.selectedDate) = :year "
			+ "AND MONTH(todoFolder.selectedDate) = :month "
			+ "AND todoFolder.user = :user")
	List<TodoFolderEntity> findByMonthAndUser(int year, int month, UserEntity user);
}
