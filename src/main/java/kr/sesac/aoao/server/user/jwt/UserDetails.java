package kr.sesac.aoao.server.user.jwt;

import java.util.List;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import kr.sesac.aoao.server.user.repository.UserEntity;
import lombok.Getter;

@Getter
public class UserDetails extends User {

	private final UserEntity userEntity;

	public UserDetails(UserEntity userEntity) {
		super(userEntity.getEmail(), userEntity.getPassword(), List.of(new SimpleGrantedAuthority("USER")));
		this.userEntity = userEntity;
	}

}
