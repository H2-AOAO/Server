package kr.sesac.aoao.server.user.repository;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
import kr.sesac.aoao.server.user.domain.KakaoInfo;
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

	@Column(length = 100)
	private String password;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "resource_id")
	private Resource resource;

	@Enumerated(EnumType.STRING)
	private UserType userType;

	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<DinoEntity> dino;

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	public List<UserItemEntity> userItems;

	@OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
	private PointEntity point;

	public UserEntity(User user) {
		this.id = user.getId();
		this.nickname = user.getNickname();
		this.password = user.getPassword();
		this.email = user.getEmail();
		this.userType = UserType.BASIC;
	}

	public UserEntity(KakaoInfo kakaoInfo) {
		this.nickname = kakaoInfo.getNickname();
		this.password = "secret";
		this.email = kakaoInfo.getEmail();
		this.userType = UserType.KAKAO;
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

	public void saveUserItems(List<UserItemEntity> userItems) {this.userItems = userItems;}

	public void updateNickname(String nickname) {
		this.nickname = nickname;
	}

	public void updatePassword(String password) {
		this.password = password;
	}

	public void updateProfile(Resource resource) {
		this.resource = resource;
	}

	public void initProfile() {
		this.resource = null;
	}

    public void todoCheck() {
        this.point.todoCheck();
    }

    public void todoUncheck() {
        this.point.todoUncheck();
    }
}
