package ci.saydos.markazcompta.config;

import ci.saydos.markazcompta.security.JwtAuthorizationFilter;
import ci.saydos.markazcompta.security.MyUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.HashMap;

@EnableWebSecurity
@RequiredArgsConstructor
@Configuration
public class SecurityConfig {

    private final MyUserDetailsService   userDetailsService;
    private final JwtAuthorizationFilter jwtFilter;


    static HashMap<String, String> pages = new HashMap<String, String>() {{
        // ARTICLE
        put("VIEW_ARTICLE",  "/article/getByCriteria");
        put("CREATE_ARTICLE", "/article/create");
        put("UPDATE_ARTICLE", "/article/update");
        put("DELETE_ARTICLE", "/article/delete");
        // CAISSE
        put("VIEW_CAISSE", "/caisse/getByCriteria");
        put("CREATE_CAISSE", "/caisse/create");
        put("UPDATE_CAISSE", "/caisse/update");
        put("DELETE_CAISSE", "/caisse/delete");
        //CHARGE_FIXE
        put("VIEW_CHARGE_FIXE", "/chargeFixe/getByCriteria");
        put("CREATE_CHARGE_FIXE", "/chargeFixe/create");
        put("UPDATE_CHARGE_FIXE", "/chargeFixe/update");
        put("DELETE_CHARGE_FIXE", "/chargeFixe/delete");
        // DEMANDE
        put("VIEW_DEMANDE", "/demande/getByCriteria");
        put("CREATE_DEMANDE", "/demande/create");
        put("UPDATE_DEMANDE", "/demande/update");
        put("DELETE_DEMANDE", "/demande/delete");
        // DEMANDE_HISTORIQUE
        put("VIEW_DEMANDE_HISTORIQUE", "/demandeHistorique/getByCriteria");
        put("CREATE_DEMANDE_HISTORIQUE", "/demandeHistorique/create");
        put("UPDATE_DEMANDE_HISTORIQUE", "/demandeHistorique/update");
        put("DELETE_DEMANDE_HISTORIQUE", "/demandeHistorique/delete");
        // DEPENSE
        put("VIEW_DEPENSE", "/depense/getByCriteria");
        put("CREATE_DEPENSE", "/depense/create");
        put("UPDATE_DEPENSE", "/depense/update");
        put("DELETE_DEPENSE", "/depense/delete");
        // DIRECTION
        put("VIEW_DIRECTION", "/direction/getByCriteria");
        put("CREATE_DIRECTION", "/direction/create");
        put("UPDATE_DIRECTION", "/direction/update");
        put("DELETE_DIRECTION", "/direction/delete");
        // MARKAZ
        put("VIEW_MARKAZ", "/markaz/getByCriteria");
        put("CREATE_MARKAZ", "/markaz/create");
        put("UPDATE_MARKAZ", "/markaz/update");
        put("DELETE_MARKAZ", "/markaz/delete");
        // RECETTE
        put("VIEW_RECETTE", "/recette/getByCriteria");
        put("CREATE_RECETTE", "/recette/create");
        put("UPDATE_RECETTE", "/recette/update");
        put("DELETE_RECETTE", "/recette/delete");
        // ROLE
        put("VIEW_ROLE", "/role/getByCriteria");
        put("CREATE_ROLE", "/role/create");
        put("UPDATE_ROLE", "/role/update");
        put("DELETE_ROLE", "/role/delete");
        // STOCK
        put("VIEW_STOCK", "/stock/getByCriteria");
        put("CREATE_STOCK", "/stock/create");
        put("UPDATE_STOCK", "/stock/update");
        put("DELETE_STOCK", "/stock/delete");
        // STOCK_HISTORIQUE
        put("VIEW_STOCK_HISTORIQUE", "/stockHistorique/getByCriteria");
        put("CREATE_STOCK_HISTORIQUE", "/stockHistorique/create");
        put("UPDATE_STOCK_HISTORIQUE", "/stockHistorique/update");
        put("DELETE_STOCK_HISTORIQUE", "/stockHistorique/delete");
        // UTILISATEUR
        put("VIEW_UTILISATEUR", "/utilisateur/getByCriteria");
        put("CREATE_UTILISATEUR", "/utilisateur/create");
        put("UPDATE_UTILISATEUR", "/utilisateur/update");
        put("DELETE_UTILISATEUR", "/utilisateur/delete");
        // UTILISATEUR_DIRECTION
        put("VIEW_UTILISATEUR_DIRECTION", "/utilisateurDirection/getByCriteria");
        put("CREATE_UTILISATEUR_DIRECTION", "/utilisateurDirection/create");
        put("UPDATE_UTILISATEUR_DIRECTION", "/utilisateurDirection/update");
        put("DELETE_UTILISATEUR_DIRECTION", "/utilisateurDirection/delete");
        // UTILISATEUR_ROLE
        put("VIEW_UTILISATEUR_ROLE", "/utilisateurRole/getByCriteria");
        put("CREATE_UTILISATEUR_ROLE", "/utilisateurRole/create");
        put("UPDATE_UTILISATEUR_ROLE", "/utilisateurRole/update");
        put("DELETE_UTILISATEUR_ROLE", "/utilisateurRole/delete");
        // STATISTIQUE
        put("VIEW_DASHBOARD_DEMANDE", "/statistique/demande");
        put("VIEW_DASHBOARD_CAISSE", "/statistique/caisse");
        put("VIEW_DASHBOARD_DEMANDE_BY_PERIOD", "/statistique/demandes-par-periode");
        put("VIEW_DASHBOARD_CAISSE_BY_PERIOD", "/statistique/caisse-par-periode");
        // AUTHENTICATION
        put("CHANGE_PASSWORD", "/auth/change-password");
        put("REFRESH_TOKEN", "/auth/refresh-token");
        put("AUTHENTICATION", "/auth/authenticate");
    }};

    static HashMap<String[], String[]> routes = new HashMap<String[], String[]>() {{

        put(new String[]{"/dossiers/add", "/dossiers/update", "/dossiers/delete"},
                new String[]{"UPDATE_DOSSIERS"});

        put(new String[]{"/commandes/nouveau", "/commandes/edit", "/commandes/add", "/commandes/update", "/commandes/delete"},
                new String[]{"UPDATE_COMMANDES"});

        put(new String[]{"/factures/nouveau", "/factures/edit", "/factures/add", "/factures/update", "/factures/delete"},
                new String[]{"UPDATE_FACTURES"});
        put(new String[]{"/factures/print", "/factures/preview"},
                new String[]{"ROLE_PRINT_FACTURES"});


        put(new String[]{"/fournisseurs/add", "/fournisseurs/update", "/fournisseurs/delete"},
                new String[]{"UPDATE_FOURNISSEURS"});

        put(new String[]{"/produits/add", "/produits/update", "/produits/delete",
                        "/familles/save", "/familles/delete", "/tva/dave", "/tva/delete"},
                new String[]{"UPDATE_PRODUITS"});
    }};

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration conf) throws Exception {
        return conf.getAuthenticationManager();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService);
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .authorizeHttpRequests(request ->
                        request.requestMatchers(
                                        "/auth/**",
                                        "**/buckets/**",
                                        "**/upload/**",
                                        "**/download/**",
                                        "**/utilisateur/**",
                                        "/v2/api-docs",
                                        "/swagger-resources",
                                        "/swagger-resources/**",
                                        "/configuration/ui",
                                        "/configuration/security",
                                        "/swagger-ui.html",
                                        "/webjars/**",
                                        "/v3/api-docs/**",
                                        "/swagger-ui/**").permitAll()
                                .anyRequest().authenticated())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider())
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        pages.forEach((k, v) -> {
            try {
                http.authorizeHttpRequests(request -> request.requestMatchers(v).hasAnyRole(k, "ADMIN"));
            } catch (Exception e) {
            }
        });

        routes.forEach((k, v) -> {
            try {
                http.authorizeHttpRequests(request -> request.requestMatchers(k).hasAnyRole(v[0], "ADMIN"));
            } catch (Exception e) {
            }
        });


        return http.build();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("*"));
        configuration.setAllowedMethods(Arrays.asList("HEAD", "GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS"));
        configuration.setAllowCredentials(true);
        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Cache-Control", "Content-Type", "Access-Control-Allow-Origin"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }


}
