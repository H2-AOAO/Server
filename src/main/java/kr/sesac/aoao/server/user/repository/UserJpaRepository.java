package kr.sesac.aoao.server.user.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserJpaRepository extends JpaRepository<UserEntity, Long> {

	Optional<UserEntity> findByEmail(String email);

	Optional<UserEntity> findByNickname(String nickname);

	boolean existsByNickname(String nickname);
}
