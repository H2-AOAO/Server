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
import lombok.Getter;
import lombok.NoArgsConstructor;
/**
 * 다이노 정보
 * @since 2024.01.19
 * @author 김은서
 */
@Entity
@Getter
@Table(name = "dino_info")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class DinoInfoEntity extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private int allExp;

	@Column(nullable = false, length = 30)
	private String lvName;

	@Column(nullable = false)
	private int step;

	@OneToMany(mappedBy = "dino")
	private List<DinoEntity> dinos;

	public void changeLv(int Lv) {
		this.step = Lv;
	}
}
