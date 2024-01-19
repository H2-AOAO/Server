package kr.sesac.aoao.server.user.repository;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import kr.sesac.aoao.server.dino.domain.Dino;
import kr.sesac.aoao.server.dino.repository.DinoEntity;
import kr.sesac.aoao.server.global.entity.BaseEntity;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@Builder
@Table(name = "users")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class UserEntity extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long userId;

	@Column(nullable = false, length = 20, unique = true)
	private String nickname;

	@Column(nullable = false, length = 60, unique = true)
	private String email;

	@Column(nullable = false, length = 100)
	private String password;

	@Column
	private String profile;

	@Enumerated(EnumType.STRING)
	private Role role;



	@OneToOne(mappedBy = "user")
	private DinoEntity dino;
}
