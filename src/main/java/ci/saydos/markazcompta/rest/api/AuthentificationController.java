package ci.saydos.markazcompta.rest.api;


import ci.saydos.markazcompta.business.UtilisateurBusiness;
import ci.saydos.markazcompta.dao.entity.*;
import ci.saydos.markazcompta.dao.repository.UtilisateurDirectionRepository;
import ci.saydos.markazcompta.dao.repository.UtilisateurRepository;
import ci.saydos.markazcompta.dao.repository.UtilisateurRoleRepository;
import ci.saydos.markazcompta.security.JwtUtil;
import ci.saydos.markazcompta.security.MyUserDetailsService;
import ci.saydos.markazcompta.utils.contract.Response;
import ci.saydos.markazcompta.utils.dto.*;
import ci.saydos.markazcompta.utils.dto.transformer.DirectionTransformer;
import ci.saydos.markazcompta.utils.dto.transformer.RoleTransformer;
import ci.saydos.markazcompta.utils.dto.transformer.UtilisateurTransformer;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@CrossOrigin("*")
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthentificationController {


    private final MyUserDetailsService userDetailsService;

    private final AuthenticationManager authenticationManager;
    private final UtilisateurBusiness   userBusiness ;

    private final JwtUtil                        jwt;
    private final UtilisateurRepository          userRepository;
    private final UtilisateurDirectionRepository utilisateurDirectionRepository;
    private final UtilisateurRoleRepository      utilisateurRoleRepository;

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request) throws ParseException {
        log.info("Start controller " + this.getClass().getName() + ".authenticate");

        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword());
        authenticationManager.authenticate(authenticationToken);

        UserDetails  user            = userDetailsService.loadUserByUsername(request.getEmail());



        final String jwtAccessToken  = jwt.generateAccessToken(user);
        final String jwtRefreshToken = jwt.generateRefreshToken(user);
        final long expiresIn = jwt.calculateExpiresIn();

        Utilisateur utilisateur = userRepository.findByEmail(request.getEmail(),false);
        UtilisateurDto userDto = UtilisateurTransformer.INSTANCE.toDto(utilisateur);
        List<UtilisateurDirection> utilisateurDirections = utilisateurDirectionRepository.findByIdUtilisateur(utilisateur.getId(), false);

        if (utilisateurDirections != null) {
            List<Direction> directions = new ArrayList<>();
            utilisateurDirections.stream().map(userDir -> directions.add(userDir.getDirection())).collect(Collectors.toList());
            List<DirectionDto> directionDtos = directions.stream().map(direction -> {
                try {
                    return DirectionTransformer.INSTANCE.toDto(direction);
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
            }).toList();
            userDto.setDirections(directionDtos);
            userDto.setPassword(null);
            log.info("Directions {}", directions);
        }

        List<UtilisateurRole> utilisateurRoles = utilisateurRoleRepository.findByUtilisateurId(utilisateur.getId(), false);
        if (utilisateurRoles != null) {
            List<Role> roles = new ArrayList<>();
            utilisateurRoles.stream().map(roleUser -> roles.add(roleUser.getRole())).collect(Collectors.toList());
            List<RoleDto> rolesDtos = roles.stream().map(role -> {
                try {
                    return RoleTransformer.INSTANCE.toDto(role);
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
            }).collect(Collectors.toList());

           userDto.setRoles(rolesDtos);
            log.info("roles {}",roles);
        }

        log.info("End controller " + this.getClass().getName() + ".authenticate");
        return ResponseEntity.ok(
                AuthenticationResponse.builder()
                        .accessToken(jwtAccessToken)
                        .refreshToken(jwtRefreshToken)
                        .expiresIn(expiresIn)
                        .utilisateur(userDto)
                        .build()
        );

    }

    @PostMapping("refresh-token")
    public void refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
        log.info(this.getClass().getName() + ".refreshToken start !");
        userBusiness.refreshToken(request, response);
        log.info(this.getClass().getName() + ".refreshToken end !");
    }

    @PostMapping("/change-password")
    public Response<UtilisateurDto> changePassword(
            @RequestBody ChangePasswordRequest request

    ) throws ParseException {
        log.info("Start controller "+this.getClass().getName() + ".changePassword");
        Response<UtilisateurDto> response = new Response<>();
        response = userBusiness.changeMotDePasse(request);
        log.info("End controller"+this.getClass().getName() + ".changePassword");
        return response;
    }

    @PostMapping("forgot-password")
    public ResponseEntity<String> forgotPassword(
            @RequestBody ResetPassword request)throws ParseException {
        log.info("Start controller "+this.getClass().getName() + ".forgotPassword");
        userBusiness.forgotPassword(request.getEmail());
        log.info("End controller "+this.getClass().getName() + ".forgotPassword");
        return ResponseEntity.ok("Mot de passe réinitialisé avec succès");
    }


}

