package kr.sesac.aoao.server.todo.exception;

import org.springframework.http.HttpStatus;

import kr.sesac.aoao.server.global.exception.ErrorCode;

public enum TodoFolderErrorCode implements ErrorCode {

    NOT_EXIST(HttpStatus.BAD_REQUEST, "존재하지 않는 투두리스트 폴더입니다."),
    IS_NOT_WRITER(HttpStatus.BAD_REQUEST, "작성자가 아닙니다."),
    IS_NOT_EMPTY(HttpStatus.BAD_REQUEST, "폴더에 투두가 존재합니다."),
    ;

    private final HttpStatus httpStatus;
    private final String message;

    TodoFolderErrorCode(HttpStatus httpStatus, String message) {
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
