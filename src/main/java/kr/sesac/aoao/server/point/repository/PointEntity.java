package kr.sesac.aoao.server.point.repository;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import kr.sesac.aoao.server.global.entity.BaseEntity;
import kr.sesac.aoao.server.user.repository.UserEntity;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "point")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class PointEntity extends BaseEntity {

    private static final int CHECK_POINT = 10;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

	@Column
	private int point = 0;

    @OneToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    public PointEntity(UserEntity userEntity) {
        this.user = userEntity;
    }

    public void changePoint(int point){ this.point = point; }

    public void todoCheck() {
        this.point += CHECK_POINT;
    }

    public void todoUncheck() {
        this.point -= CHECK_POINT;
    }
}
