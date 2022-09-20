package com.skullprogrammer.project.error;

import com.skullprogrammer.project.error.enums.ErrorType;
import lombok.Data;

@Data
public class SkullProgrammerException extends RuntimeException {

    private ErrorType errorResponse;

    public SkullProgrammerException(ErrorType errorResponse) {
        this.errorResponse = errorResponse;
    }

    public SkullProgrammerException(ErrorType errorResponse, String message) {
        super(message);
        this.errorResponse = errorResponse;
    }

    public SkullProgrammerException(ErrorType errorResponse, String message, Throwable cause) {
        super(message, cause);
        this.errorResponse = errorResponse;
    }

    public SkullProgrammerException(ErrorType errorResponse, Throwable cause) {
        super(cause);
        this.errorResponse = errorResponse;
    }
}
