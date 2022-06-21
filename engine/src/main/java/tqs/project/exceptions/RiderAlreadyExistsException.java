package tqs.project.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class RiderAlreadyExistsException extends Exception{
    private static final long serialVersionUID = 1L;

    public RiderAlreadyExistsException(String message){
        super(message);
    }
}
