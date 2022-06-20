package tqs.project.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class OrderNotUpdatedException extends Exception{
    private static final long serialVersionUID = 1L;

    public OrderNotUpdatedException(String message){
        super(message);
    }
}