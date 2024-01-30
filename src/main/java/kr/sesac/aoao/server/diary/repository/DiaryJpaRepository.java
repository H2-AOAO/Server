package kr.sesac.aoao.server.diary.repository;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import kr.sesac.aoao.server.user.repository.UserEntity;

/**
 * @since 2024.01.23
 * @author 최정윤
 */
public interface DiaryJpaRepository extends JpaRepository<DiaryEntity, Long> {

	Optional<DiaryEntity> findByUser(UserEntity user);

	Optional<DiaryEntity> findByUserAndSelectedDate(UserEntity user, LocalDate date);
}