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

	@ManyToOne
	@JoinColumn(name = "user_id")
	private UserEntity user;

	@Column(nullable = false, length = 20)
	private String name;

	@Column(nullable = false, length = 20)
	private String color;

	@Column(nullable = false)
	private int exp;

	@Column(nullable = false)
	private boolean flag;

	@ManyToOne
	@JoinColumn(name = "level")
	private DinoInfoEntity dino;

	public DinoEntity(UserEntity user, String name, String color, int exp, boolean flag,  DinoInfoEntity dino) {
		this.user = user;
		this.name = name;
		this.color = color;
		this.exp = exp;
		this.flag = flag;
		this.dino = dino;
	}

	public void changeName(String name) {
		this.name = name;
	}

	public void changeExp(int exp) {
		this.exp = exp;
	}

	public void saveFlag(boolean flag){ this.flag = flag;}
	public void saveColor(String color){this.color = color;}
}
