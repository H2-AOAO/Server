package kr.sesac.aoao.server.item.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemJpaRepository extends JpaRepository<ItemEntity, Long> {

	Optional<ItemEntity> findById(Long id);

}
