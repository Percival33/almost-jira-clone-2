package pl.edu.pw.elka.pap.z16.almostjira.exceptions;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.Serial;

@ResponseStatus(value = HttpStatus.UNAUTHORIZED)
public class ClientNotAuthorizedException extends RuntimeException {
    /**
     * TODO: use lombok to remove getters
     */
    @Serial
    private static final long serialVersionUID = 1L;

    public ClientNotAuthorizedException() {
        super(String.format("User not authorized to modify resource"));
    }

    static public String getName() {
        return "ClientNotAuthorizedException";
    }
}