package com.base.advice;

import org.apache.commons.lang3.StringUtils;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.base.util.Log;
import com.platform.messages.ErrorResponse;

/**
 * @author Muhil Kennedy Global controller errorhandling
 */
@RestControllerAdvice
@Order(Ordered.LOWEST_PRECEDENCE)
public class GlobalControllerAdvice extends ResponseEntityExceptionHandler {

	@ExceptionHandler(DataIntegrityViolationException.class)
	public ResponseEntity<ErrorResponse> handleSQLIntegrityException(DataIntegrityViolationException ex) {
		ErrorResponse errorResponse = new ErrorResponse(HttpStatus.CONFLICT.value(), "Oops! There is a data conflict! Please try again!"); //TODO: Localize
		Log.base.error(errorResponse.toString());
		if (logger.isDebugEnabled()) {
			ex.printStackTrace();
		}
		logger.error("SQLIntegrityConstraintViolationException :: Exception :: ", ex);
		return new ResponseEntity<ErrorResponse>(errorResponse, HttpStatus.CONFLICT);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorResponse> handleGenericException(Exception ex) {
		ErrorResponse errorResponse = new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), extractMessage(ex));
		Log.base.error(errorResponse.toString());
		logger.error("handleGenericException :: Exception :: {}", ex);
		return new ResponseEntity<ErrorResponse>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	private String extractMessage(Exception ex) {
		if (!StringUtils.isAllBlank(ex.getMessage())) {
			return ex.getMessage();
		} else if (ex.getCause() != null) {
			return ex.getCause().getMessage();
		} else {
			return ex.toString();
		}
	}

	// @Override
//	@ExceptionHandler(MethodArgumentNotValidException.class)
//	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
//			HttpHeaders headers, HttpStatus status, WebRequest request) {
//		String errorMessage = ex.getBindingResult().getFieldErrors().stream()
//				.map(error -> String.format("%s %s", error.getField(), error.getDefaultMessage())).findFirst()
//				.orElse(ex.getMessage());
//		ErrorResponse errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), errorMessage);
//		Log.base.error(errorResponse.toString());
//		if (logger.isDebugEnabled()) {
//			ex.printStackTrace();
//		}
//		logger.error("handleGenericException :: Exception :: " + ex.getMessage());
//		return new ResponseEntity<Object>(errorResponse, HttpStatus.BAD_REQUEST);
//	}

}
