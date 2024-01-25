package kr.sesac.aoao.server.user.repository;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import kr.sesac.aoao.server.dino.repository.DinoEntity;
import kr.sesac.aoao.server.global.entity.BaseEntity;
import kr.sesac.aoao.server.item.repository.UserItemEntity;
import kr.sesac.aoao.server.point.repository.PointEntity;
import kr.sesac.aoao.server.user.domain.User;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "users")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserEntity extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false, length = 20, unique = true)
	private String nickname;

	@Column(nullable = false, length = 60, unique = true)
	private String email;

	@Column(nullable = false, length = 100)
	private String password;

	@Column
	private String profile;

	@OneToOne(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private DinoEntity dino;

	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<UserItemEntity> userItems;
	
	@OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
	private PointEntity point;

	public UserEntity(User user) {
		this.id = user.getId();
		this.nickname = user.getNickname();
		this.password = user.getPassword();
		this.email = user.getEmail();
		this.profile = user.getProfile();
	}

	/**
	 * UserEntity 를 User domain 으로 변경
	 *
	 * @return user
	 * @author 이상민
	 * @since 2024.01.19
	 */
	public User toModel() {
		return new User(this);
	}

	/**
	 * 작성자 검증
	 *
	 * @return boolean
	 * @parameter UserEntity
	 * @author 김유빈
	 * @since 2024.01.22
	 */
	public boolean isWriter(UserEntity user) {
		return this.id.equals(user.id);
	}
}
