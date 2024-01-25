package kr.sesac.aoao.server.todo.repository;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import kr.sesac.aoao.server.global.entity.BaseEntity;
import kr.sesac.aoao.server.user.repository.UserEntity;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "todo")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class TodoEntity extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column
	private String content;

	@Column
	private boolean checked;

	@ManyToOne
	@JoinColumn(name = "folder_id")
	private TodoFolderEntity todoFolder;

	private TodoEntity(String content, boolean checked, TodoFolderEntity todoFolder) {
		this.content = content;
		this.checked = checked;
		this.todoFolder = todoFolder;
	}

	public static TodoEntity save(String content, TodoFolderEntity todoFolder, UserEntity user) {
		todoFolder.validateUserIsWriter(user);
		return new TodoEntity(content, false, todoFolder);
	}

	public void update(String content, TodoFolderEntity todoFolder, UserEntity user) {
		todoFolder.validateUserIsWriter(user);
		this.content = content;
	}

	public void validateUserIsWriter(TodoFolderEntity todoFolder, UserEntity user) {
		todoFolder.validateUserIsWriter(user);
	}

	public void check(TodoFolderEntity todoFolder, UserEntity user) {
		todoFolder.validateUserIsWriter(user);
		this.checked = true;
	}

	public void uncheck(TodoFolderEntity todoFolder, UserEntity user) {
		todoFolder.validateUserIsWriter(user);
		this.checked = false;
	}
}
