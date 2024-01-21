package kr.sesac.aoao.server.todo.repository;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import kr.sesac.aoao.server.global.entity.BaseEntity;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "palette")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class PaletteEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // todo: ENUM 정의하여 수정
    // todo: Application 실행 시 ENUM 순회하여 정보 저장
    @Column
    private String colorCode;
}
