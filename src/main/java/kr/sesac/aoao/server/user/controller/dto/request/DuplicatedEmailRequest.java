package kr.sesac.aoao.server.user.controller.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class DuplicatedEmailRequest {

	@Email(message = "이메일 형식을 지켜주세요")
	private final String email;
	private final boolean flag;
}