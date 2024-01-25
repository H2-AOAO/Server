package kr.sesac.aoao.server.global.exception;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import kr.sesac.aoao.server.global.controller.dto.response.ApplicationResponse;
import lombok.extern.java.Log;

/**
 * @author 김유빈
 * @since 2024.01.17
 */
@Log
@RestControllerAdvice
public class GlobalExceptionHandler {

	/**
	 * 어플리케이션 exception 처리 추가
	 *
	 * @return ResponseEntity<ApplicationResponse < Void>>
	 * @parameter ApplicationException
	 * @author 김유빈
	 * @since 2024.01.17
	 */
	@ExceptionHandler(ApplicationException.class)
	public ResponseEntity<ApplicationResponse<Void>> handleApplicationException(ApplicationException e) {
		HttpStatus statusCode = e.getStatusCode();
		ApplicationResponse<Void> response = ApplicationResponse.fail(e.getMessage());

		return ResponseEntity.status(statusCode).body(response);
	}

	/**
	 * spring validation exception 처리 추가
	 *
	 * @return ResponseEntity<ApplicationResponse < Void>>
	 * @parameter MethodArgumentNotValidException
	 * @author 김유빈
	 * @since 2024.01.22
	 */
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ApplicationResponse<Void>> handleValidationException(MethodArgumentNotValidException e) {
		ApplicationResponse<Void> response = ApplicationResponse.fail(
			e.getBindingResult().getAllErrors().get(0).getDefaultMessage());
		return ResponseEntity.badRequest().body(response);
	}

	/**
	 * jpa entity column exception 처리 추가
	 *
	 * @return ResponseEntity<ApplicationResponse < Void>>
	 * @parameter DataIntegrityViolationException
	 * @author 김유빈
	 * @since 2024.01.22
	 */
	@ExceptionHandler(DataIntegrityViolationException.class)
	public ResponseEntity<ApplicationResponse<Void>> handleEntityException(DataIntegrityViolationException e) {
		log.warning(e.getMessage());
		ApplicationResponse<Void> response = ApplicationResponse.fail("올바른 값을 입력해주세요.");
		return ResponseEntity.badRequest().body(response);
	}

	/**
	 * global exception 처리 추가
	 *
	 * @return ResponseEntity<ApplicationResponse < Void>>
	 * @parameter Exception
	 * @author 김유빈
	 * @since 2024.01.22
	 */
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ApplicationResponse<Void>> handleGlobalException(Exception e) {
		log.warning(e.getMessage());
		ApplicationResponse<Void> response = ApplicationResponse.fail("서버에 문제가 발생하였습니다. 관리자에게 문의해주세요.");
		return ResponseEntity.internalServerError().body(response);
	}

}
