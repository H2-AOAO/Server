package kr.sesac.aoao.server.todo.exception;

import org.springframework.http.HttpStatus;

import kr.sesac.aoao.server.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum PaletteErrorCode implements ErrorCode {

    NOT_EXIST(HttpStatus.BAD_REQUEST, "존재하지 않는 색상 정보입니다."),
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
