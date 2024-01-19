package kr.sesac.aoao.server.todo.repository;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import kr.sesac.aoao.server.global.entity.BaseEntity;
import kr.sesac.aoao.server.user.repository.UserEntity;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "todo_folder")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class TodoFolderEntity extends BaseEntity {

    @Column
    private String content;

    @Column
    private LocalDateTime date;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @ManyToOne
    @JoinColumn(name = "palette_id")
    private PaletteEntity palette;
}
