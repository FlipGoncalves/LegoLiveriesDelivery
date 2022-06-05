package restapi.tqs.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class BadOrderLegoListException extends Exception{
    private static final long serialVersionUID = 1L;

    public BadOrderLegoListException(String message){
        super(message);
    }
}
