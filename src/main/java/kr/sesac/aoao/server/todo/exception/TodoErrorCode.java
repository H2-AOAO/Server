package kr.sesac.aoao.server.todo.exception;

import org.springframework.http.HttpStatus;

import kr.sesac.aoao.server.global.exception.ErrorCode;

public enum TodoErrorCode implements ErrorCode {
    ;

    private final HttpStatus httpStatus;
    private final String message;

    TodoErrorCode(HttpStatus httpStatus, String message) {
        this.httpStatus = httpStatus;
        this.message = message;
    }

    @Override
    public HttpStatus getStatusCode() {
        return httpStatus;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
