package kr.sesac.aoao.server.user.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author 이상민
 * @since 2024.01.19
 */
public interface TokenJpaRepository extends JpaRepository<RefreshTokenEntity, String> {

	Optional<RefreshTokenEntity> findByEmail(String email);
}
