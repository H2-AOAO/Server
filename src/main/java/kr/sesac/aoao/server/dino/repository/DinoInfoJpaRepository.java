package kr.sesac.aoao.server.dino.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface DinoInfoJpaRepository extends JpaRepository<DinoInfoEntity, Long> {
	Optional<DinoInfoEntity> findByStep(int Lv);
}
