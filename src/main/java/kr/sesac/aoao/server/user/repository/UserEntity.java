package kr.sesac.aoao.server.user.repository;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import kr.sesac.aoao.server.global.entity.BaseEntity;
import kr.sesac.aoao.server.user.domain.User;
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

	public static UserEntity from(User user){
		UserEntity userEntity = new UserEntity();
		userEntity.userId = user.getUserId();
		userEntity.nickname = user.getNickname();
		userEntity.password = user.getPassword();
		userEntity.email = user.getEmail();
		userEntity.profile = user.getProfile();
		userEntity.role = user.getRole();
		return userEntity;
	}

	public User toModel(){
		return User.builder()
			.userId(userId)
			.email(email)
			.password(password)
			.nickname(nickname)
			.profile(profile)
			.role(role)
			.build();
	}

}
