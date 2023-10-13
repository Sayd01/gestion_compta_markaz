package ci.saydos.markazcompta.utils.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ChangePasswordRequest {
    private Integer id;
    private String newPassword;
    private String confirmationPassword;
}
