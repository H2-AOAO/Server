package kr.sesac.aoao.server.dino.repository;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import kr.sesac.aoao.server.global.entity.BaseEntity;
import kr.sesac.aoao.server.user.repository.UserEntity;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "raise_dino")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class DinoEntity extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@OneToOne
	@JoinColumn(name = "user_id")
	private UserEntity user;

	@Column(nullable = false, length = 20)
	private String name;

	@Column(nullable = false, length = 20)
	private String color;

	@Column(nullable = false)
	private int exp;

	@ManyToOne
	@JoinColumn(name = "Lv")
	private DinoInfoEntity dino;

	public void changeName(String name) {
		this.name = name;
	}

	public void changeExp(int exp) {
		this.exp = exp;
	}
}
