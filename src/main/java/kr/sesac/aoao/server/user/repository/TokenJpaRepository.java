package kr.sesac.aoao.server.user.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TokenJpaRepository extends JpaRepository<RefreshTokenEntity, String> {

	Optional<RefreshTokenEntity> findByEmail(String email);
}
