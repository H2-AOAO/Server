package kr.sesac.aoao.server.user.infrastructure;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;

@Entity
@Table(name = "users")
@RequiredArgsConstructor
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long user_id;

    @Column(nullable = false, length = 20)
    private String nickname;

    @Column(nullable = false, length = 60, unique = true)
    private String email;

    @Column(nullable = false, length = 100)
    private String password;

    @Column
    private String profile;

}
