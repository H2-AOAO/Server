package kr.sesac.aoao.server.user.infrastructure;

import jakarta.persistence.*;
import kr.sesac.aoao.server.global.entity.BaseEntity;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Entity
@Table(name = "users")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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

}
