package pl.edu.pw.elka.pap.z16.almostjira.exceptions;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.Serial;

@ResponseStatus(value = HttpStatus.NOT_ACCEPTABLE)
public class LoginAlreadyInUseException extends RuntimeException {
    /**
     * TODO: use lombok to remove getters
     */
    @Serial
    private static final long serialVersionUID = 1L;

    public LoginAlreadyInUseException() {
        super(String.format("Login is already in use"));
    }

    static public String getName() {
        return "LoginAlreadyInUseException";
    }
}