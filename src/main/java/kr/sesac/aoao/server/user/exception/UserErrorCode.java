package kr.sesac.aoao.server.user.exception;

import org.springframework.http.HttpStatus;

import kr.sesac.aoao.server.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum UserErrorCode implements ErrorCode {

	NOT_EXIST(HttpStatus.BAD_REQUEST, "존재하지 않는 사용자입니다."),
	EXISTENT_EMAIL(HttpStatus.BAD_REQUEST, "이미 존재하는 이메일입니다."),
	INVALID_PASSWORD(HttpStatus.BAD_REQUEST, "비밀번호가 일치하지 않습니다."),
	NOT_FOUND_USER(HttpStatus.BAD_REQUEST, "사용자를 찾을 수 없습니다."),
	NOT_EXISTENT_EMAIL(HttpStatus.BAD_REQUEST, "가입되지 않은 Email 입니다."),
	NOT_CORRECTED_PASSWORD(HttpStatus.BAD_REQUEST, "잘못된 비밀번호입니다."),

	// token
	FORBIDDEN(HttpStatus.BAD_REQUEST, "필터에러"),
	INVALID_TOKEN(HttpStatus.BAD_REQUEST, "토큰이 유효하지 않습니다."),
	EXPIRE_TOKEN(HttpStatus.FORBIDDEN, "TOKEN이 만료되었습니다."),
	UNSUPPORTED_TOKEN(HttpStatus.FORBIDDEN, "지원되지 않는 JWT TOKEN입니다."),
	WRONG_TOKEN(HttpStatus.FORBIDDEN, "잘못 설계된 TOKEN입니다.");

	private final HttpStatus httpStatus;
	private final String message;

	@Override
	public HttpStatus getStatusCode() {
		return httpStatus;
	}

	@Override
	public String getMessage() {
		return message;
	}
}
