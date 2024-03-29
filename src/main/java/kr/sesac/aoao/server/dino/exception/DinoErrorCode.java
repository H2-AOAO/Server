package kr.sesac.aoao.server.dino.exception;

import org.springframework.http.HttpStatus;

import kr.sesac.aoao.server.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
/**
 * 다이노 에러 코드
 * @since 2024.01.19
 * @author 김은서
 */
@RequiredArgsConstructor
public enum DinoErrorCode implements ErrorCode {
	NO_DINO(HttpStatus.BAD_REQUEST, "공룡 정보가 존재하지 않습니다."),
	NO_DINO_INFO(HttpStatus.BAD_REQUEST, "공룡 레벨 정보가 존재하지 않습니다."),
	NOT_ENOUGH_POINT(HttpStatus.BAD_REQUEST, "포인트가 충분하지 않습니다.");

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
