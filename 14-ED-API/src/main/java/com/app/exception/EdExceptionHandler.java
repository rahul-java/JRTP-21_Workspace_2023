package com.app.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class EdExceptionHandler {

	@ExceptionHandler(value = EdException.class)
	public ResponseEntity<ExceptionInfo> handlleException(EdException edException)
	{
		ExceptionInfo exceptionInfo = new ExceptionInfo();
		exceptionInfo.setCode("EdException-1010");
		exceptionInfo.setMsg(edException.getMessage());
		
		return new ResponseEntity<ExceptionInfo>(exceptionInfo,HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
