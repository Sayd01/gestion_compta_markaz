package ci.saydos.markazcompta.utils.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import ci.saydos.markazcompta.utils.Status;

/**
 * APIExceptionHandler
 *
 * @author Sayd & Souleymane
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EntityNotFoundException extends RuntimeException {
    private Status status;


}