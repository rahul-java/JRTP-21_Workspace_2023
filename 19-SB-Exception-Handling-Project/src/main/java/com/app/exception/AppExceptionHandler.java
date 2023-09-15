package com.app.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class AppExceptionHandler {

	@ExceptionHandler(value=NoProductFoundException.class)
	public ResponseEntity<ExceptionBean> handleNoDataFoundException(NoProductFoundException ne)
	{
		String message = ne.getMessage();
		ExceptionBean exceptionBean = new ExceptionBean();
		exceptionBean.setCode("Error : 9999");
		exceptionBean.setMsg(message);
		
		return new ResponseEntity<ExceptionBean>(exceptionBean,HttpStatus.BAD_REQUEST);
		
	}
	
	@ExceptionHandler(value=NullPointerException.class)
	public ResponseEntity<ExceptionBean> handleNullPonterException(NullPointerException ne)
	{
		String message = ne.getMessage();
		ExceptionBean exceptionBean = new ExceptionBean();
		exceptionBean.setCode("Error : 0000");
		exceptionBean.setMsg(ne.toString());
		
		return new ResponseEntity<ExceptionBean>(exceptionBean,HttpStatus.BAD_REQUEST);
		
	}
}
