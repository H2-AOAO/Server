package kr.sesac.aoao.server.item.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
/**
 * @since 2024.01.22
 * @author 김은서
 */
public interface ItemJpaRepository extends JpaRepository<ItemEntity, Long> {

	Optional<ItemEntity> findById(Long id);
	ItemEntity findNonNullById(long itemId);

}
