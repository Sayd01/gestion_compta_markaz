package ci.saydos.markazcompta.security;

import ci.saydos.markazcompta.dao.entity.Role;
import ci.saydos.markazcompta.dao.entity.Utilisateur;
import ci.saydos.markazcompta.dao.repository.RoleRepository;
import ci.saydos.markazcompta.dao.repository.UtilisateurRepository;
import ci.saydos.markazcompta.dao.repository.UtilisateurRoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class MyUserDetailsService implements UserDetailsService {
    private final UtilisateurRepository     userRepository;
    private final RoleRepository            roleRepository;
    private final UtilisateurRoleRepository userRoleRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Utilisateur utilisateur = userRepository.findByEmail(email, false);
        if (utilisateur == null) {
            throw new UsernameNotFoundException("No User found with username: " + email);
        }
        List<Role> roles = new ArrayList<>();
        userRoleRepository.findUserRoleByEmail(utilisateur.getEmail())
                .forEach(userRole -> roles.add(userRole.getRole()));

        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        roles.forEach(role -> authorities.add(new SimpleGrantedAuthority(role.getName())));

        return new ExtendedUser(utilisateur.getEmail(), utilisateur.getPassword(), authorities);
    }
}
