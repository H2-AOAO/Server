package kr.sesac.aoao.server.dino.repository;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import kr.sesac.aoao.server.global.entity.BaseEntity;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@Builder
@Table(name = "dino_info")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class DinoInfoEntity extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int Lv;

	@Column(nullable = false)
	private int all_exp;

	@Column(nullable = false, length = 30)
	private String lv_name;

	@OneToMany(mappedBy = "dino")
	private List<DinoEntity> dinos;
}
