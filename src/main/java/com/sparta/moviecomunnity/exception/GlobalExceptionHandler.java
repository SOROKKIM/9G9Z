package com.sparta.moviecomunnity.exception;


import com.sparta.moviecomunnity.dto.SigninRequestDto;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.Server;
import org.apache.coyote.Response;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import static com.sparta.moviecomunnity.exception.ResponseCode.DUPLICATE_RESOURCE;
import static com.sparta.moviecomunnity.exception.ResponseCode.INVALID_ID_INFO;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = { ConstraintViolationException.class, DataIntegrityViolationException.class})
    protected ResponseEntity<ServerResponse> handleDataException() {
        log.error("handleDataException throw Exception : {}", ResponseCode.DUPLICATE_RESOURCE);
        return ServerResponse.toResponseEntity(DUPLICATE_RESOURCE);
    }

    @ExceptionHandler(value = { CustomException.class })
    protected ResponseEntity<ServerResponse> handleCustomException(CustomException e) {
        log.error("handleCustomException throw CustomException : {}", e.getErrorCode());
        return ServerResponse.toResponseEntity(e.getErrorCode());
    }
}

