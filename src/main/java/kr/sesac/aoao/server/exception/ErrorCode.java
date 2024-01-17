package kr.sesac.aoao.server.exception;

import org.springframework.http.HttpStatus;

public interface ErrorCode {

    HttpStatus getStatusCode();

    String getMessage();
}
