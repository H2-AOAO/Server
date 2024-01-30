package kr.sesac.aoao.server.item.exception;

import org.springframework.http.HttpStatus;

import kr.sesac.aoao.server.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
/**
 * @since 2024.01.22
 * @author 김은서
 */
@RequiredArgsConstructor
public enum ItemErrorCode implements ErrorCode {
	NOT_FOUND_ITEM(HttpStatus.BAD_REQUEST, "존재하지 않는 아이템입니다."),;

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
