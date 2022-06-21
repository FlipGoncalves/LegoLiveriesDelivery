package restapi.tqs.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class AddressAlreadyExistsException extends Exception{
    private static final long serialVersionUID = 1L;

    public AddressAlreadyExistsException(String message){
        super(message);
    }
}
