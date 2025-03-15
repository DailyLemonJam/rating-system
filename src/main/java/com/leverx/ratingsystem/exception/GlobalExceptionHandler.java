package com.leverx.ratingsystem.exception;

import com.leverx.ratingsystem.dto.ErrorDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorDto> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e) {
        return new ResponseEntity<>(new ErrorDto("Incorrect argument type"), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorDto> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        return new ResponseEntity<>(new ErrorDto("Invalid request data"), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(CommentNotFoundException.class)
    public ResponseEntity<ErrorDto> handleCommentNotFoundException(CommentNotFoundException e) {
        return new ResponseEntity<>(new ErrorDto(e.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(NotAllowedToUpdateCommentException.class)
    public ResponseEntity<ErrorDto> handleNotAllowedToUpdateCommentException(NotAllowedToUpdateCommentException e) {
        return new ResponseEntity<>(new ErrorDto(e.getMessage()), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorDto> handleUserNotFoundException(UserNotFoundException e) {
        return new ResponseEntity<>(new ErrorDto(e.getMessage()), HttpStatus.FORBIDDEN);
    }

}
