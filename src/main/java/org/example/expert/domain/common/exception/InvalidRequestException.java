package org.example.expert.domain.common.exception;

import org.example.expert.common.exception.ServiceException;
import org.springframework.http.HttpStatus;

public class InvalidRequestException extends ServiceException {
    public InvalidRequestException(String message) {
        super(HttpStatus.BAD_REQUEST, message);
    }
}
