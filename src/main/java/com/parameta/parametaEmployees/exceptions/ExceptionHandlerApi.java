package com.parameta.parametaEmployees.exceptions;

import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import com.parameta.parametaEmployees.utils.Constants;

@RestControllerAdvice
public class ExceptionHandlerApi {

	
	/**
	 * 
	 *  Exception become returns mandatory fields in JSON code */
	
	@ExceptionHandler({MethodArgumentNotValidException.class, BindException.class})
	public ResponseEntity<Map<String, String>> handleValidationExceptions(Exception ex) {
		
		BindingResult bindingResult;
		// obtaining result
		if(ex instanceof MethodArgumentNotValidException) {
			bindingResult = ((MethodArgumentNotValidException) ex).getBindingResult();
		} else {
			bindingResult = ((BindException) ex).getBindingResult();
		}
		
		// mapping errors
		Map<String, String> errors = new HashMap<>();
		
		for(FieldError error: bindingResult.getFieldErrors()) {
			errors.put(error.getField(), error.getDefaultMessage());
		}
		// response with 400 status
	    return ResponseEntity.badRequest().body(errors) ;
	}
	
	/**
	 * 
	 *  Exception shows when it has some conditions  */
	
	@ExceptionHandler(CriteriaIssuesException.class)
	public ResponseEntity<String> handleCriteriaIssuesException(CriteriaIssuesException ex) {
		return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(ex.getMessage());
	}
	
	/**
	 * 
	 *  Exception shows when exist internal error  */
	
	@ExceptionHandler(InternalErrorException.class)
	public ResponseEntity<String> handleInternalErrorException(InternalErrorException ex) {
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
	}
	
	/**
	 * 
	 *  bad format exception  */
	
	@ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<String> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
        return ResponseEntity.badRequest().body("{\"message\": \"" +  Constants.BAD_FORMAT + "\",\"code\": \"BAD_REQUEST\"}");
    }

}
