package kr.sesac.aoao.server.global.exception;

import org.springframework.http.HttpStatus;

public interface ErrorCode {

    HttpStatus getStatusCode();

    String getMessage();
}
