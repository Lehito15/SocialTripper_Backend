package com.socialtripper.restapi.exceptions;

import com.socialtripper.restapi.dto.messages.ErrorDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.net.HttpURLConnection;


@ControllerAdvice
public class FaultController {
    @ResponseBody
    @ExceptionHandler(EventNotFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    ResponseEntity<ErrorDTO> eventNotFoundExceptionHandler(EventNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ErrorDTO(HttpURLConnection.HTTP_NOT_FOUND,HttpStatus.NOT_FOUND, e.getMessage()));
    }

    @ResponseBody
    @ExceptionHandler(PostNotFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    ResponseEntity<ErrorDTO> postNotFoundExceptionHandler(PostNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErrorDTO(HttpURLConnection.HTTP_NOT_FOUND,HttpStatus.NOT_FOUND, e.getMessage()));
    }

    @ResponseBody
    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    ResponseEntity<ErrorDTO> userNotFoundExceptionHandler(UserNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErrorDTO(HttpURLConnection.HTTP_NOT_FOUND,HttpStatus.NOT_FOUND, e.getMessage()));
    }

    @ResponseBody
    @ExceptionHandler(GroupNotFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    ResponseEntity<ErrorDTO> groupNotFoundExceptionHandler(GroupNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErrorDTO(HttpURLConnection.HTTP_NOT_FOUND,HttpStatus.NOT_FOUND, e.getMessage()));
    }

}