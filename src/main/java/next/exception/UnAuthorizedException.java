package next.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by johngrib on 2017. 5. 9..
 */
@ResponseStatus(value= HttpStatus.UNAUTHORIZED, reason="UNAUTHORIZED!!!") // 401
public class UnAuthorizedException extends RuntimeException {
    public UnAuthorizedException(String message) {
        super(message);
    }
}
