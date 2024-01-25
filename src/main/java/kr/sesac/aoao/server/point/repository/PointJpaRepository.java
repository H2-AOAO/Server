package kr.sesac.aoao.server.point.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import kr.sesac.aoao.server.user.repository.UserEntity;

public interface PointJpaRepository extends JpaRepository<PointEntity, Long> {
	Optional<PointEntity> findByUser(UserEntity user);
}
