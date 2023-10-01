package ci.saydos.markazcompta.utils.exception;

import ci.saydos.markazcompta.utils.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * APIExceptionHandler
 *
 * @author Sayd & Souleymane
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FunctionalException extends RuntimeException {
    private Status status;


}