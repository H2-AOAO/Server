package kr.sesac.aoao.server.item.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import kr.sesac.aoao.server.user.repository.UserEntity;

public interface UserItemJpaRepository extends JpaRepository<UserItemEntity, Long> {
	Optional<UserItemEntity> findByUserAndItem(UserEntity user, ItemEntity item);
}
