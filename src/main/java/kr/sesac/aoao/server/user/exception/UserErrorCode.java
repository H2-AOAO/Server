package kr.sesac.aoao.server.user.exception;

import org.springframework.http.HttpStatus;

import kr.sesac.aoao.server.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum UserErrorCode implements ErrorCode {
	EXISTENT_EMAIL(HttpStatus.BAD_REQUEST, "이미 존재하는 이메일입니다.");

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
