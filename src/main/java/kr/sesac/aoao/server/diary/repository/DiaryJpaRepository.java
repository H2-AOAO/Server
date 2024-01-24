package kr.sesac.aoao.server.diary.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import kr.sesac.aoao.server.dino.repository.DinoEntity;
import kr.sesac.aoao.server.user.repository.UserEntity;

public interface DiaryJpaRepository extends JpaRepository <DiaryEntity, Long> {

	Optional<DiaryEntity> findByUserId(Long userID);
	Optional<DiaryEntity> findByUser(UserEntity user);
	Optional<DiaryEntity> findByContent(String content);
	Optional<DiaryEntity> deleteById(UserEntity user);
}
