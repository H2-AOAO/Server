package kr.sesac.aoao.server.dino.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface DinoJpaRepository extends JpaRepository<DinoEntity, Long> {
	Optional<DinoEntity> findByUserId(Long userID);
}
