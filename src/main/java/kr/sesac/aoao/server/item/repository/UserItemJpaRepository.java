package kr.sesac.aoao.server.item.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import kr.sesac.aoao.server.user.repository.UserEntity;
/**
 * @since 2024.01.22
 * @author 김은서
 */
public interface UserItemJpaRepository extends JpaRepository<UserItemEntity, Long> {
	Optional<UserItemEntity> findByUserAndItem(UserEntity user, ItemEntity item);

	List<UserItemEntity> findAllByUser(UserEntity user);
}
