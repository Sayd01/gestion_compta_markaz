package ci.saydos.markazcompta.security;


import ci.saydos.markazcompta.dao.entity.Utilisateur;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.stream.Collectors;
@Component
public class JwtUtil {

    @Value("${application.security.jwt.secret}")
    private String SECRET;
    @Value("${application.security.jwt.access-token.expiration}")
    private long ACCESS_TOKEN_EXPIRATION;
    @Value("${application.security.jwt.refresh-token.expiration}")
    private long REFRESH_TOKEN_EXPIRATION;

    public String generateAccessToken(UserDetails user) {
        Algorithm      algorithm         = Algorithm.HMAC256(SECRET);
        Utilisateur    authenticatedUser = null;
        Authentication authentication    = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof User) {
            authenticatedUser = (Utilisateur) authentication.getPrincipal();
            // Accédez maintenant aux champs de l'entité User tels que user.getUsername(), user.getPassword(), etc.
        }
        return JWT.create()
                .withSubject(user.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + ACCESS_TOKEN_EXPIRATION))
                .withClaim("roles", user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                .withClaim("email", user.getUsername())
                .withClaim("firstName", authenticatedUser != null ? authenticatedUser.getFirstName() : "")
                .sign(algorithm);
    }
    public String generateRefreshToken(UserDetails user) {
        Algorithm algorithm = Algorithm.HMAC256(SECRET);
        return JWT.create()
                .withSubject(user.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + REFRESH_TOKEN_EXPIRATION))
                .withClaim("roles", user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                .sign(algorithm);
    }

    public long calculateExpiresIn() {
        long expirationMillis = System.currentTimeMillis() + ACCESS_TOKEN_EXPIRATION;
        long currentTimeMillis = System.currentTimeMillis();
        return (expirationMillis - currentTimeMillis) / 1000;
    }






}
