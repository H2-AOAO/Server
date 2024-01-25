package kr.sesac.aoao.server.user.controller.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class DuplicatedNicknameRequest {

	@NotBlank(message = "닉네임을 입력해주세요.")
	@Size(min = 2, message = "닉네임이 너무 짧습니다. 2자 이상 입력해 주세요.")
	private final String nickname;

	private final boolean flag;
}
