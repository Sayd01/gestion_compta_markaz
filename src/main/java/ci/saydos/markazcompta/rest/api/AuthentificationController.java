//package ci.saydos.markazcompta.rest.api;
//
//
//import ci.saydos.markazcompta.business.UtilisateurBusiness;
//import ci.saydos.markazcompta.dao.repository.UtilisateurRepository;
//import ci.saydos.markazcompta.security.JwtUtil;
//import ci.saydos.markazcompta.security.MyUserDetailsService;
//import ci.saydos.markazcompta.utils.dto.AuthenticationRequest;
//import ci.saydos.markazcompta.utils.dto.AuthenticationResponse;
//import ci.saydos.markazcompta.utils.dto.UtilisateurDto;
//import ci.saydos.markazcompta.utils.dto.transformer.UtilisateurTransformer;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.java.Log;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.web.bind.annotation.*;
//
//import java.io.IOException;
//import java.text.ParseException;
//
//@Log
//@RestController
//@RequiredArgsConstructor
//@RequestMapping("/auth")
//public class AuthentificationController {
//
//
//    private final MyUserDetailsService userDetailsService;
//
//    private final AuthenticationManager authenticationManager;
//    private final UtilisateurBusiness   userBusiness;
//
//    private final JwtUtil               jwt;
//    private final UtilisateurRepository userRepository;
//
//    @PostMapping("/authenticate")
//    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request) {
//        log.info("J'entre ici");
//        UsernamePasswordAuthenticationToken authenticationToken =
//                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword());
//        authenticationManager.authenticate(authenticationToken);
//
//        UserDetails  user            = userDetailsService.loadUserByUsername(request.getEmail());
//        log.info("================ Email: " + user.getUsername());
//        final String jwtAccessToken  = jwt.generateAccessToken(user);
//        final String jwtRefreshToken = jwt.generateRefreshToken(user);
//        return ResponseEntity.ok(
//                AuthenticationResponse.builder()
//                        .accessToken(jwtAccessToken)
//                        .refreshToken(jwtRefreshToken)
//                        .build()
//        );
//
//    }
//
//    @PostMapping("/refresh-token")
//    public void refreshToken(
//            HttpServletRequest request,
//            HttpServletResponse response
//    ) throws IOException {
//        log.info("========================== Init refresh token in AuthenticationController");
//        userBusiness.refreshToken(request, response);
//    }
//
//    @GetMapping("/profile")
//    public UtilisateurDto profile(Authentication authentication) throws ParseException {
//        return UtilisateurTransformer.INSTANCE.toDto(userRepository.findByEmail((String) authentication.getPrincipal(), false));
//    }
//
//
//}
//
