package kr.sesac.aoao.server.diary.exception;

import org.springframework.http.HttpStatus;

import kr.sesac.aoao.server.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;

/**
 * @since 2024.01.23
 * @author 최정윤
 */
@RequiredArgsConstructor
public enum DiaryErrorCode implements ErrorCode {

	INVALID_DATE_FORMAT(HttpStatus.BAD_REQUEST, "잘못된 날짜 형식입니다."),
	NO_DIARY(HttpStatus.BAD_REQUEST, "일기 정보가 존재하지 않습니다."),
	EMPTY_DIARY(HttpStatus.BAD_REQUEST, "내용이 비어있습니다."),
	;

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
