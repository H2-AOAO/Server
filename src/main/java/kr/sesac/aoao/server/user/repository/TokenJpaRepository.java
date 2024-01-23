package kr.sesac.aoao.server.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TokenJpaRepository extends JpaRepository<RefreshTokenEntity, String> {

	RefreshTokenEntity findByEmail(String email);
}
