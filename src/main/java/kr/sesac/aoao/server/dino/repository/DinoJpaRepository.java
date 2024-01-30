package kr.sesac.aoao.server.dino.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import kr.sesac.aoao.server.user.repository.UserEntity;
/**
 * @since 2024.01.19
 * @author 김은서
 */
public interface DinoJpaRepository extends JpaRepository<DinoEntity, Long> {

	Optional<DinoEntity> findByUserAndFlag(UserEntity user, Boolean flag);
	List<DinoEntity> findAllByUser(UserEntity user);
}
