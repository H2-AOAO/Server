package kr.sesac.aoao.server.dino.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import kr.sesac.aoao.server.user.jwt.UserCustomDetails;
import kr.sesac.aoao.server.user.repository.UserEntity;

public interface DinoJpaRepository extends JpaRepository<DinoEntity, Long> {

	Optional<DinoEntity> findByUserAndFlag(UserEntity user, Boolean flag);
	Optional<DinoEntity> findByUser(UserEntity user);
	Optional<DinoEntity> findByUserAndDino (UserEntity user,DinoInfoEntity dinoInfo);
	List<DinoEntity> findAllByUser(UserEntity user);
}
