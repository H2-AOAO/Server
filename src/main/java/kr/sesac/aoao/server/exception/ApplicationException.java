package kr.sesac.aoao.server.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public class ApplicationException extends RuntimeException {

    private final HttpStatus statusCode;
    private final String message;

    public ApplicationException(ErrorCode code) {
        statusCode = code.getStatusCode();
        message = code.getMessage();
    }
}
