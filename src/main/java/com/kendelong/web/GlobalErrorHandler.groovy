package com.kendelong.web;

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.dao.DataAccessException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.validation.BindException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestController

import com.kendelong.ItemNotFoundException

@ControllerAdvice(annotations = RestController.class)
public class GlobalErrorHandler
{
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@ExceptionHandler(Exception.class)
	public ResponseEntity<WebServiceResponse> handleException(Exception ex)
	{
		logger.error("Error handling response", ex)
		WebServiceResponse response = new WebServiceResponseWithError(status: 'failure', message: ex.message, errorCode: 1000)
		return new ResponseEntity(response, HttpStatus.INTERNAL_SERVER_ERROR)
	}

	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<WebServiceResponse> handleArugmentException(IllegalArgumentException ex)
	{
		logger.warn("Failure creating user: $ex.message")
		WebServiceResponse response = new WebServiceResponseWithError(status: 'failure', message: ex.message, errorCode: 1001)
		return new ResponseEntity(response, HttpStatus.CONFLICT)
	}
	
	@ExceptionHandler(BindException.class)
	public ResponseEntity<WebServiceResponse> handleBindingAndValidationError(BindException be)
	{
		def message = be.allErrors.collect { it.defaultMessage}.join(',')  // prints 309
		def selfDocumentingMessage = be.allErrors.collect {it.codes[1]}.join(',')   // prints NotNull.email - you probably want this one, it's better
		WebServiceResponse response = new WebServiceResponseWithError(status: 'failure', message: "Binding or validation error: [$selfDocumentingMessage]", errorCode: message)
		return new ResponseEntity(response, HttpStatus.BAD_REQUEST)
	}

	@ExceptionHandler(HttpMessageNotReadableException.class)
	public ResponseEntity<WebServiceResponse> handleUnreadableRequestError(HttpMessageNotReadableException be)
	{
		// this is too bad, it shows up as this obscure error rather than a bind exception
		def message = be.message
		WebServiceResponse response = new WebServiceResponseWithError(status: 'failure', message: message, errorCode: 1002)
		return new ResponseEntity(response, HttpStatus.BAD_REQUEST)
	}

	@ExceptionHandler(ItemNotFoundException.class)
	public ResponseEntity<WebServiceResponse> handleItemNotFound(ItemNotFoundException e)
	{
		def message = "Could not find [$e.type] with id [$e.id]"
		WebServiceResponse response = new WebServiceResponseWithError(status: 'failure', message: message, errorCode: 1003)
		return new ResponseEntity(response, HttpStatus.NOT_FOUND)
	}
	
	//org.springframework.dao.DataAccessException
	@ExceptionHandler(DataAccessException.class)
	public ResponseEntity<WebServiceResponse> handleDataAccessException(DataAccessException e)
	{
		
		def message = "Data access exception: [$e.mostSpecificCause.message]; contact developer, he messed up"
		WebServiceResponse response = new WebServiceResponseWithError(status: 'failure', message: message, errorCode: 1004)
		return new ResponseEntity(response, HttpStatus.INTERNAL_SERVER_ERROR)
	}

	public static class WebServiceResponseWithError extends WebServiceResponse
	{
		String errorCode
	}

}
