package kr.sesac.aoao.server.item.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import kr.sesac.aoao.server.dino.repository.DinoEntity;
import kr.sesac.aoao.server.user.repository.UserEntity;

public interface ItemJpaRepository  extends JpaRepository<ItemEntity, Long> {

	Optional<ItemEntity> findById(Long id);

}
