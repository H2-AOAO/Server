package kr.sesac.aoao.server.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import kr.sesac.aoao.server.controller.dto.response.ExceptionResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ApplicationException.class)
    public ResponseEntity<ExceptionResponse> handleException(ApplicationException e) {
        HttpStatus statusCode = e.getStatusCode();
        ExceptionResponse response = ExceptionResponse.from(e.getMessage());

        return ResponseEntity.status(statusCode).body(response);
    }
}
