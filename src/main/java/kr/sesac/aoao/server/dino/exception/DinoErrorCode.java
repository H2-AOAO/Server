package kr.sesac.aoao.server.dino.exception;

import org.springframework.http.HttpStatus;

import kr.sesac.aoao.server.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum DinoErrorCode implements ErrorCode {
	NO_DINO(HttpStatus.BAD_REQUEST, "공룡 정보가 존재하지 않습니다.");

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
