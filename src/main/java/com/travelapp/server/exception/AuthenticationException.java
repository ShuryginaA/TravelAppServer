package com.travelapp.server.exception;

import java.io.Serial;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.server.ResponseStatusException;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class AuthenticationException extends ResponseStatusException {

    @Serial
    private static final long serialVersionUID = 4504392658834709024L;

    public AuthenticationException() {
        super(HttpStatus.FORBIDDEN, "Ошибка аутентификации: введены неверные данные");
    }
}
