package kr.sesac.aoao.server.user.controller.dto.request;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class UserNicknameUpdateRequest {

    private final String nickname;
    private final String password;
}
