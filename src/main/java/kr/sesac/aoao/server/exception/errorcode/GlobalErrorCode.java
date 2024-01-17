package kr.sesac.aoao.server.exception.errorcode;

import org.springframework.http.HttpStatus;

import kr.sesac.aoao.server.exception.ErrorCode;

public enum GlobalErrorCode implements ErrorCode {
    ;

    @Override
    public HttpStatus getStatusCode() {
        return null;
    }

    @Override
    public String getMessage() {
        return null;
    }
}
