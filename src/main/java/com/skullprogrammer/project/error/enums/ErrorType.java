package com.skullprogrammer.project.error.enums;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
public enum ErrorType {

    USER_NOT_FOUND(HttpStatus.BAD_REQUEST, "U-01"),
    USER_NOT_AUTHORIZED(HttpStatus.UNAUTHORIZED, "U-02"),
    GENERIC_REQUEST_ERROR(HttpStatus.BAD_REQUEST, "G-01")
    ;

    private final static String BASE_ERROR_CODE = "SKULLPROGRAMMER-PROJECT";

    private HttpStatus status;
    private String errorCode;

    public HttpStatus getStatus() {
        return status;
    }

    public String getErrorCode() {

        return String.format("%s-%s", BASE_ERROR_CODE, errorCode);
    }

}
