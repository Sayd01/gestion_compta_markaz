package ci.saydos.markazcompta.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

public class ExtendedUser extends User {
    public ExtendedUser(String userEmail, String password,
                        Collection<? extends GrantedAuthority> authorities) {
        super(userEmail, password, authorities);

    }
}