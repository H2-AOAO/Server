package kr.sesac.aoao.server.todo.repository;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
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
@Table(name = "todo_folder")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class TodoFolderEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String content;

    @Column
    private LocalDate date;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @ManyToOne
    @JoinColumn(name = "palette_id")
    private PaletteEntity palette;

    public TodoFolderEntity(String content, LocalDate date, UserEntity user, PaletteEntity palette) {
        this.content = content;
        this.date = date;
        this.user = user;
        this.palette = palette;
    }

    public void update(UserEntity user, String content, PaletteEntity palette) {
        validateUserIsWriter(user);
        this.content = content;
        this.palette = palette;
    }

    public void validateUserIsWriter(UserEntity user) {
        if (!this.user.isWriter(user)) {
            throw new ApplicationException(TodoFolderErrorCode.IS_NOT_WRITER);
        }
    }
}
