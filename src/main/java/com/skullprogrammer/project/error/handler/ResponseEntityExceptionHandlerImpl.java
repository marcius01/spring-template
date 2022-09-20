package com.skullprogrammer.project.error.handler;

import com.skullprogrammer.project.dto.response.ErrorResponse;
import com.skullprogrammer.project.error.SkullProgrammerException;
import com.skullprogrammer.project.error.enums.ErrorType;
import lombok.extern.java.Log;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@ResponseStatus
@Log
public class ResponseEntityExceptionHandlerImpl extends ResponseEntityExceptionHandler {
    
    @ExceptionHandler({SkullProgrammerException.class})
    @ResponseBody
    public ResponseEntity<ErrorResponse> digitalPantryException (SkullProgrammerException ex, WebRequest request) {
        ErrorType errorResponse = ex.getErrorResponse();
        ErrorResponse result = ErrorResponse.builder().status(errorResponse.getStatus()).message(errorResponse.getErrorCode()).build();
        return ResponseEntity.status(errorResponse.getStatus())
                .body(result);
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    @ResponseBody
    public ResponseEntity<ErrorResponse> internalAuthenticationServiceException (UsernameNotFoundException ex, WebRequest request) {
        ErrorType errorResponse = ErrorType.USER_NOT_AUTHORIZED;
        ErrorResponse result = ErrorResponse.builder().status(errorResponse.getStatus()).message(errorResponse.getErrorCode()).build();
        return ResponseEntity.status(errorResponse.getStatus())
                .body(result);
    }

    @ExceptionHandler({ AuthenticationException.class })
    @ResponseBody
    public ResponseEntity<ErrorResponse> handleAuthenticationException(AuthenticationException ex, WebRequest request) {
        ErrorType errorResponse = ErrorType.USER_NOT_AUTHORIZED;
        ErrorResponse result = ErrorResponse.builder().status(errorResponse.getStatus()).message(errorResponse.getErrorCode()).build();
        return ResponseEntity.status(errorResponse.getStatus())
                .body(result);
    }


    @ExceptionHandler({ AccessDeniedException.class })
    @ResponseBody
    public ResponseEntity<ErrorResponse> handleAccessDeniedException(AccessDeniedException ex, WebRequest request) {
        ErrorType errorResponse = ErrorType.USER_NOT_AUTHORIZED;
        ErrorResponse result = ErrorResponse.builder().status(errorResponse.getStatus()).message(errorResponse.getErrorCode()).build();
        return ResponseEntity.status(errorResponse.getStatus())
                .body(result);
    }

}
