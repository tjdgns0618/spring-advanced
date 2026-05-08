package org.example.expert.domain.auth.exception;

import org.example.expert.common.exception.ServiceException;
import org.springframework.http.HttpStatus;

public class AuthException extends ServiceException {

    public AuthException(String message) {
        super(HttpStatus.UNAUTHORIZED, message);
    }
}
