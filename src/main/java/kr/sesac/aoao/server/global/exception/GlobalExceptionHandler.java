package kr.sesac.aoao.server.global.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import kr.sesac.aoao.server.global.controller.dto.response.ApplicationResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(ApplicationException.class)
	public ResponseEntity<ApplicationResponse<Void>> handleException(ApplicationException e) {
		HttpStatus statusCode = e.getStatusCode();
		ApplicationResponse<Void> response = ApplicationResponse.fail(e.getMessage());

		return ResponseEntity.status(statusCode).body(response);
	}
}
