package org.example.expert.domain.common.exception;

import org.example.expert.common.exception.ServiceException;
import org.springframework.http.HttpStatus;

public class ServerException extends ServiceException {

    public ServerException(String message) {
        super(HttpStatus.UNAUTHORIZED, message);
    }
}
