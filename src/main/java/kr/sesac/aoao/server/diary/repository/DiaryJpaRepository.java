package kr.sesac.aoao.server.diary.repository;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import kr.sesac.aoao.server.user.repository.UserEntity;

public interface DiaryJpaRepository extends JpaRepository<DiaryEntity, Long> {

	Optional<DiaryEntity> findByUser(UserEntity user);

	Optional<DiaryEntity> findByUserAndSelectedDate(UserEntity user, LocalDate date);
}