package ci.saydos.markazcompta.utils.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ResetPassword {
    private String email;
    private String newPassword;
}
