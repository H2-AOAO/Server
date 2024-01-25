package kr.sesac.aoao.server.diary.repository;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import kr.sesac.aoao.server.diary.controller.dto.request.DiaryCreateRequest;
import kr.sesac.aoao.server.global.entity.BaseEntity;
import kr.sesac.aoao.server.global.exception.ApplicationException;
import kr.sesac.aoao.server.todo.exception.TodoFolderErrorCode;
import kr.sesac.aoao.server.todo.repository.PaletteEntity;
import kr.sesac.aoao.server.user.repository.UserEntity;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "diary")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class DiaryEntity extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id; // 다이어리 아이디

	@Column
	private String content; // 일기 내용

	@Column
	private LocalDateTime date; // 캘린더 설정 날짜

	@ManyToOne
	@JoinColumn(name = "user_id")
	private UserEntity user; // 일기 작성자

	public DiaryEntity(DiaryCreateRequest request) {
		this.id = request.getUserId();
		this.content = request.getContent();
		this.date = request.getDate();
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

	public void diary(DiaryCreateRequest request) {
		this.id = request.getUserId();
		this.content = request.getContent();
		// this.date = request.getDate();
	}

}
