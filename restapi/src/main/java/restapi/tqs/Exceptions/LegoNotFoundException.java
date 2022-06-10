package restapi.tqs.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class LegoNotFoundException extends Exception{
    private static final long serialVersionUID = 1L;

    public LegoNotFoundException(String message){
        super(message);
    }
}