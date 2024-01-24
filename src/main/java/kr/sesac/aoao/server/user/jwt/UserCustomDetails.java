package kr.sesac.aoao.server.user.jwt;

import java.util.List;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import kr.sesac.aoao.server.user.repository.UserEntity;
import lombok.Getter;

/**
 * @author 이상민
 * @since 2024.01.20
 */
@Getter
public class UserCustomDetails extends User {

	private final UserEntity userEntity;

	public UserCustomDetails(UserEntity userEntity) {
		super(userEntity.getEmail(), userEntity.getPassword(), List.of(new SimpleGrantedAuthority("USER")));
		this.userEntity = userEntity;
	}

}
