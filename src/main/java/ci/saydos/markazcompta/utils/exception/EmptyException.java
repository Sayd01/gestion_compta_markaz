package ci.saydos.markazcompta.utils.exception;

import ci.saydos.markazcompta.utils.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * APIExceptionHandler
 *
 * @author Sayd & Souleymane
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmptyException extends RuntimeException {
    private Status status;


}