package kr.sesac.aoao.server.user.controller.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;

@Getter
public class SignUpRequest {

	@NotBlank(message = "이메일을 입력해주세요")
	private final String email;

	@NotBlank(message = "닉네임을 입력해주세요.")
	@Size(min = 2, message = "닉네임이 너무 짧습니다.")
	private final String nickname;

	@NotBlank(message = "비밀번호를 입력해주세요")
	@Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,20}$",
		message = "비밀번호는 8~20 자리이면서 1개 이상의 알파벳, 숫자, 특수문자를 포함해야합니다.")
	private final String password;

	private final String checkedPassword;

	@Builder
	public SignUpRequest(String email, String nickname, String password, String checkedPassword) {
		this.email = email;
		this.nickname = nickname;
		this.password = password;
		this.checkedPassword = checkedPassword;
	}

}
