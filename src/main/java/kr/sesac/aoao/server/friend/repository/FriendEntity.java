package kr.sesac.aoao.server.friend.repository;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import kr.sesac.aoao.server.global.entity.BaseEntity;
import kr.sesac.aoao.server.global.exception.ApplicationException;
import kr.sesac.aoao.server.todo.exception.TodoFolderErrorCode;
import kr.sesac.aoao.server.user.repository.UserEntity;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "friend")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class FriendEntity extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id; // 친구목록 아이디

	@ManyToOne
	@JoinColumn(name = "user_id")
	private UserEntity user; // 로그인한 유저 아이디

	@ManyToMany
	@JoinColumn(name = "user_id")
	private UserEntity user; // 찬규 유저 아이디

	public FriendEntity(String content, LocalDateTime date, UserEntity user) {
		this.content = content;
		this.date = date;
		this.user = user;
	}

	public void diaryUpdate(UserEntity user, String content) {
		validateUserIsWriter(user);
		this.content = content;
	}

	public void validateUserIsWriter(UserEntity user) {
		if (!this.user.isWriter(user)) {
			throw new ApplicationException(TodoFolderErrorCode.IS_NOT_WRITER);
		}
	}
}
