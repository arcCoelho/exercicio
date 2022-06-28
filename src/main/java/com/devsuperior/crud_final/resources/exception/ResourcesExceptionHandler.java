package com.devsuperior.crud_final.resources.exception;

import java.time.Instant;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.devsuperior.crud_final.service.exception.ResourcesNotFoundException;

@ControllerAdvice
public class ResourcesExceptionHandler {

	@ExceptionHandler(ResourcesNotFoundException.class)
	public ResponseEntity<StandardError> EntityNotFound(ResourcesNotFoundException e, HttpServletRequest req){
		HttpStatus status = HttpStatus.NOT_FOUND;
		
		StandardError error = new StandardError();
		error.setError("Recurso não encontrado");
		error.setMessage(e.getMessage());
		error.setPath(req.getRequestURI());
		error.setStatus(status.value());
		error.setTimestamp(Instant.now());
		
		return ResponseEntity.status(status).body(error);
	}
	
}
