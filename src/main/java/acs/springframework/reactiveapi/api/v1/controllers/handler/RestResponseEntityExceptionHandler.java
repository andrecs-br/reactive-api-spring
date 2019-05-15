package acs.springframework.reactiveapi.api.v1.controllers.handler;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import acs.springframework.reactiveapi.exceptions.ResourceNotFoundException;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@RestControllerAdvice
@Slf4j
public class RestResponseEntityExceptionHandler {
	
	@ExceptionHandler({ResourceNotFoundException.class})
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public Mono<String> handleNotFoundException(Throwable exception) {
		
		log.info("An exception will be throw: " + exception.getMessage());

		return Mono.just("Resource not Found");
		
	}
	

}
