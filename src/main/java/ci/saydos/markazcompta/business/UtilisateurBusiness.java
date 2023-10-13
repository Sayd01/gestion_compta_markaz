                                                            														
/*
 * Java business for entity table utilisateur
 * Created on 2023-08-08 ( Time 19:02:56 )
 * Generator tool : Telosys Tools Generator ( version 3.3.0 )
 * Copyright 2018 Geo. All Rights Reserved.
 */

package ci.saydos.markazcompta.business;

import ci.saydos.markazcompta.dao.entity.*;
import ci.saydos.markazcompta.dao.repository.*;
import ci.saydos.markazcompta.utils.*;
import ci.saydos.markazcompta.utils.contract.IBasicBusiness;
import ci.saydos.markazcompta.utils.contract.Request;
import ci.saydos.markazcompta.utils.contract.Response;
import ci.saydos.markazcompta.utils.dto.ChangePasswordRequest;
import ci.saydos.markazcompta.utils.dto.DirectionDto;
import ci.saydos.markazcompta.utils.dto.RoleDto;
import ci.saydos.markazcompta.utils.dto.UtilisateurDto;
import ci.saydos.markazcompta.utils.dto.transformer.DirectionTransformer;
import ci.saydos.markazcompta.utils.dto.transformer.RoleTransformer;
import ci.saydos.markazcompta.utils.dto.transformer.UtilisateurTransformer;
import ci.saydos.markazcompta.utils.exception.EntityNotFoundException;
import ci.saydos.markazcompta.utils.exception.FunctionalException;
import ci.saydos.markazcompta.utils.exception.InternalErrorException;
import ci.saydos.markazcompta.utils.exception.InvalidEntityException;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.text.ParseException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * BUSINESS for table "utilisateur"
 *
 * @author Geo
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class UtilisateurBusiness implements IBasicBusiness<Request<UtilisateurDto>, Response<UtilisateurDto>> {
    private final RoleRepository roleRepository;

    @Value("${application.security.jwt.secret}")
    private String SECRET;
    @Value("${application.security.jwt.access-token.expiration}")
    private long   ACCESS_TOKEN_EXPIRATION;

    @Value("${user-password}")
    private String defaultPassword;

    private final UtilisateurRepository utilisateurRepository;

    private final DepenseRepository depenseRepository;

    private final DemandeHistoriqueRepository demandeHistoriqueRepository;

    private final UtilisateurRoleRepository utilisateurRoleRepository;

    private final CaisseRepository caisseRepository;

    private final DemandeRepository demandeRepository;

    private final UtilisateurDirectionRepository utilisateurDirectionRepository;

    private final FunctionalError functionalError;



    @PersistenceContext
    private EntityManager em;


    /**
     * create Utilisateur by using UtilisateurDto as object.
     *
     * @param request
     * @return response
     */
    @Override
    public Response<UtilisateurDto> create(Request<UtilisateurDto> request, Locale locale) throws ParseException {
        log.info("----begin create Utilisateur-----");

        Response<UtilisateurDto> response = new Response<UtilisateurDto>();
        List<Utilisateur>        items    = new ArrayList<Utilisateur>();

        for (UtilisateurDto dto : request.getDatas()) {
            // Definir les parametres obligatoires
            Map<String, java.lang.Object> fieldsToVerify = new HashMap<String, java.lang.Object>();
            fieldsToVerify.put("email", dto.getEmail());
            if (!Validate.RequiredValue(fieldsToVerify).isGood()) {
                throw new InvalidEntityException(functionalError.FIELD_EMPTY(Validate.getValidate().getField(), locale));
            }

            //Verifier que l'utilisateur connecté est l'admin
            List<UtilisateurRole> userRoles = utilisateurRoleRepository.findByUtilisateurId(request.getUser(),false);
            if (Utilities.isNotEmpty(userRoles)) {
                for (UtilisateurRole userRole : userRoles){
                    if (!Objects.equals(userRole.getRole().getName(), "ADMIN")) {
                        throw new FunctionalException(functionalError.OPERATION("The USER does not have an ADMIN role",locale));
                    }
                }
            }


            // Verify if utilisateur to insert do not exist
            Utilisateur existingEntity = null;
            if (existingEntity != null) {
                throw new InternalErrorException(functionalError.DATA_EXIST("utilisateur id -> " + dto.getId(), locale));
            }

            // verif unique email in db
            existingEntity = utilisateurRepository.findByEmail(dto.getEmail(), false);
            if (existingEntity != null) {
                throw new InternalErrorException(functionalError.DATA_EXIST("utilisateur email -> " + dto.getEmail(), locale));
            }
            // verif unique email irn items to save
            if (items.stream().anyMatch(a -> a.getEmail().equalsIgnoreCase(dto.getEmail()))) {
                throw new InternalErrorException(functionalError.DATA_DUPLICATE(" email ", locale));
            }


            Utilisateur entityToSave = null;
            entityToSave = UtilisateurTransformer.INSTANCE.toEntity(dto);
            entityToSave.setCreatedAt(Utilities.getCurrentDate());
            entityToSave.setCreatedBy(request.getUser());
            entityToSave.setPassword(new BCryptPasswordEncoder().encode(defaultPassword));
            entityToSave.setIsDeleted(false);
            entityToSave.setIsFirstConnexion(true);
            items.add(entityToSave);
        }

        if (!items.isEmpty()) {
            List<Utilisateur> itemsSaved = null;
            // inserer les donnees en base de donnees
            itemsSaved = utilisateurRepository.saveAll((Iterable<Utilisateur>) items);
            if (itemsSaved == null) {
                throw new InternalErrorException(functionalError.SAVE_FAIL("utilisateur", locale));
            }
            List<UtilisateurDto> itemsDto = (Utilities.isTrue(request.getIsSimpleLoading())) ? UtilisateurTransformer.INSTANCE.toLiteDtos(itemsSaved) : UtilisateurTransformer.INSTANCE.toDtos(itemsSaved);

            final int    size        = itemsSaved.size();
            List<String> listOfError = Collections.synchronizedList(new ArrayList<String>());
            itemsDto.parallelStream().forEach(dto -> {
                try {
                    dto = getFullInfos(dto, size, request.getIsSimpleLoading(), locale);
                } catch (Exception e) {
                    listOfError.add(e.getMessage());
                    e.printStackTrace();
                }
            });
            if (Utilities.isNotEmpty(listOfError)) {
                Object[] objArray = listOfError.stream().distinct().toArray();
                throw new RuntimeException(StringUtils.join(objArray, ", "));
            }
            Status status = new Status();
            status.setCode(StatusCode.SUCCESS);
            status.setMessage(StatusMessage.SUCCESS);

            response.setHttpCode(HttpStatus.CREATED.value());
            response.setStatus(status);
            response.setItems(itemsDto);
            response.setHasError(false);
        }

        log.info("----end create Utilisateur-----");
        return response;
    }

    /**
     * update Utilisateur by using UtilisateurDto as object.
     *
     * @param request
     * @return response
     */
    @Override
    public Response<UtilisateurDto> update(Request<UtilisateurDto> request, Locale locale) throws ParseException {
        log.info("----begin update Utilisateur-----");

        Response<UtilisateurDto> response = new Response<UtilisateurDto>();
        List<Utilisateur>        items    = new ArrayList<Utilisateur>();

        for (UtilisateurDto dto : request.getDatas()) {
            // Definir les parametres obligatoires
            Map<String, java.lang.Object> fieldsToVerify = new HashMap<String, java.lang.Object>();
            fieldsToVerify.put("id", dto.getId());
            if (!Validate.RequiredValue(fieldsToVerify).isGood()) {
                throw new InvalidEntityException(functionalError.FIELD_EMPTY(Validate.getValidate().getField(), locale));
            }

            // Verifier si la utilisateur existe
            Utilisateur entityToSave = null;
            entityToSave = utilisateurRepository.findOne(dto.getId(), false);
            if (entityToSave == null) {
                throw new EntityNotFoundException(functionalError.DATA_NOT_EXIST("utilisateur id -> " + dto.getId(), locale));
            }

            if (Utilities.notBlank(dto.getFirstName())) {
                entityToSave.setFirstName(dto.getFirstName());
            }
            if (Utilities.notBlank(dto.getLastName())) {
                entityToSave.setLastName(dto.getLastName());
            }
            if (Utilities.notBlank(dto.getEmail())) {
                entityToSave.setEmail(dto.getEmail());
            }
            if (Utilities.notBlank(dto.getImageUrl())) {
                entityToSave.setImageUrl(dto.getImageUrl());
            }
            if (dto.getCreatedBy() != null && dto.getCreatedBy() > 0) {
                entityToSave.setCreatedBy(dto.getCreatedBy());
            }
            if (dto.getUpdatedBy() != null && dto.getUpdatedBy() > 0) {
                entityToSave.setUpdatedBy(dto.getUpdatedBy());
            }
            if (Utilities.notBlank(dto.getPassword())) {
				entityToSave.setPassword(new BCryptPasswordEncoder().encode(dto.getPassword()));
                entityToSave.setPassword(dto.getPassword());
            }
            entityToSave.setUpdatedAt(Utilities.getCurrentDate());
            entityToSave.setUpdatedBy(request.getUser());
            items.add(entityToSave);
        }

        if (!items.isEmpty()) {
            List<Utilisateur> itemsSaved = null;
            // maj les donnees en base
            itemsSaved = utilisateurRepository.saveAll((Iterable<Utilisateur>) items);
            if (itemsSaved == null) {
                throw new InternalErrorException(functionalError.SAVE_FAIL("utilisateur", locale));
            }
            List<UtilisateurDto> itemsDto = (Utilities.isTrue(request.getIsSimpleLoading())) ? UtilisateurTransformer.INSTANCE.toLiteDtos(itemsSaved) : UtilisateurTransformer.INSTANCE.toDtos(itemsSaved);

            final int    size        = itemsSaved.size();
            List<String> listOfError = Collections.synchronizedList(new ArrayList<String>());
            itemsDto.parallelStream().forEach(dto -> {
                try {
                    dto = getFullInfos(dto, size, request.getIsSimpleLoading(), locale);
                } catch (Exception e) {
                    listOfError.add(e.getMessage());
                    e.printStackTrace();
                }
            });
            if (Utilities.isNotEmpty(listOfError)) {
                Object[] objArray = listOfError.stream().distinct().toArray();
                throw new RuntimeException(StringUtils.join(objArray, ", "));
            }
            Status status = new Status();
            status.setCode(StatusCode.SUCCESS);
            status.setMessage(StatusMessage.SUCCESS);

            response.setItems(itemsDto);
            response.setHasError(false);
            response.setHttpCode(HttpStatus.OK.value());
            response.setStatus(status);
        }

        log.info("----end update Utilisateur-----");
        return response;
    }

    /**
     * delete Utilisateur by using UtilisateurDto as object.
     *
     * @param request
     * @return response
     */
    @Override
    public Response<UtilisateurDto> delete(Request<UtilisateurDto> request, Locale locale) {
        log.info("----begin delete Utilisateur-----");

        Response<UtilisateurDto> response = new Response<UtilisateurDto>();
        List<Utilisateur>        items    = new ArrayList<Utilisateur>();

        for (UtilisateurDto dto : request.getDatas()) {
            // Definir les parametres obligatoires
            Map<String, java.lang.Object> fieldsToVerify = new HashMap<String, java.lang.Object>();
            fieldsToVerify.put("id", dto.getId());
            if (!Validate.RequiredValue(fieldsToVerify).isGood()) {
                throw new InvalidEntityException(functionalError.FIELD_EMPTY(Validate.getValidate().getField(), locale));
            }

            // Verifier si la utilisateur existe
            Utilisateur existingEntity = null;
            existingEntity = utilisateurRepository.findOne(dto.getId(), false);
            if (existingEntity == null) {
                throw new EntityNotFoundException(functionalError.DATA_NOT_EXIST("utilisateur -> " + dto.getId(), locale));
            }

            // -----------------------------------------------------------------------
            // ----------- CHECK IF DATA IS USED
            // -----------------------------------------------------------------------

            // depense
            List<Depense> listOfDepense = depenseRepository.findByIdChargeFixe(existingEntity.getId(), false);
            if (listOfDepense != null && !listOfDepense.isEmpty()) {
                throw new InternalErrorException(functionalError.DATA_NOT_DELETABLE("(" + listOfDepense.size() + ")", locale));
            }
            // demandeHistorique
            List<DemandeHistorique> listOfDemandeHistorique = demandeHistoriqueRepository.findByIdUtilisateur(existingEntity.getId(), false);
            if (listOfDemandeHistorique != null && !listOfDemandeHistorique.isEmpty()) {
                throw new InternalErrorException(functionalError.DATA_NOT_DELETABLE("(" + listOfDemandeHistorique.size() + ")", locale));
            }
            // utilisateurRole
            List<UtilisateurRole> listOfUtilisateurRole = utilisateurRoleRepository.findByUtilisateurId(existingEntity.getId(), false);
            if (listOfUtilisateurRole != null && !listOfUtilisateurRole.isEmpty()) {
                throw new InternalErrorException(functionalError.DATA_NOT_DELETABLE("(" + listOfUtilisateurRole.size() + ")", locale));
            }
            // caisse
            List<Caisse> listOfCaisse = caisseRepository.findByIdUtilisateur(existingEntity.getId(), false);
            if (listOfCaisse != null && !listOfCaisse.isEmpty()) {
                throw new InternalErrorException(functionalError.DATA_NOT_DELETABLE("(" + listOfCaisse.size() + ")", locale));
            }
            // demande
            List<Demande> listOfDemande = demandeRepository.findByIdUtilisateur(existingEntity.getId(), false);
            if (listOfDemande != null && !listOfDemande.isEmpty()) {
                throw new InternalErrorException(functionalError.DATA_NOT_DELETABLE("(" + listOfDemande.size() + ")", locale));
            }
            // utilisateurDirection
            List<UtilisateurDirection> listOfUtilisateurDirection = utilisateurDirectionRepository.findByIdUtilisateur(existingEntity.getId(), false);
            if (listOfUtilisateurDirection != null && !listOfUtilisateurDirection.isEmpty()) {
                throw new InternalErrorException(functionalError.DATA_NOT_DELETABLE("(" + listOfUtilisateurDirection.size() + ")", locale));
            }


            existingEntity.setIsDeleted(true);
            items.add(existingEntity);
        }

        if (!items.isEmpty()) {
            // supprimer les donnees en base
            utilisateurRepository.saveAll((Iterable<Utilisateur>) items);

            Status status = new Status();
            status.setCode(StatusCode.SUCCESS);
            status.setMessage(StatusMessage.SUCCESS);

            response.setHasError(false);
            response.setHttpCode(HttpStatus.OK.value());
            response.setStatus(status);
        }

        log.info("----end delete Utilisateur-----");
        return response;
    }

    /**
     * get Utilisateur by using UtilisateurDto as object.
     *
     * @param request
     * @return response
     */
    @Override
    public Response<UtilisateurDto> getByCriteria(Request<UtilisateurDto> request, Locale locale) throws Exception {
        log.info("----begin get Utilisateur-----");

        Response<UtilisateurDto> response = new Response<UtilisateurDto>();
        List<Utilisateur>        items    = utilisateurRepository.getByCriteria(request, em, locale);

        if (items != null && !items.isEmpty()) {
            List<UtilisateurDto> itemsDto = (Utilities.isTrue(request.getIsSimpleLoading())) ? UtilisateurTransformer.INSTANCE.toLiteDtos(items) : UtilisateurTransformer.INSTANCE.toDtos(items);

            final int    size        = items.size();
            List<String> listOfError = Collections.synchronizedList(new ArrayList<String>());
            itemsDto.parallelStream().forEach(dto -> {
                try {
                    dto = getFullInfos(dto, size, request.getIsSimpleLoading(), locale);
                } catch (Exception e) {
                    listOfError.add(e.getMessage());
                    e.printStackTrace();
                }
            });
            if (Utilities.isNotEmpty(listOfError)) {
                Object[] objArray = listOfError.stream().distinct().toArray();
                throw new RuntimeException(StringUtils.join(objArray, ", "));
            }
            Status status = new Status();
            status.setCode(StatusCode.SUCCESS);
            status.setMessage(StatusMessage.SUCCESS);

            response.setItems(itemsDto);
            response.setHasError(false);
            response.setHttpCode(HttpStatus.OK.value());
            response.setStatus(status);
            response.setCount(utilisateurRepository.count(request, em, locale));
            response.setHasError(false);
        } else {
            response.setStatus(functionalError.DATA_EMPTY("utilisateur", locale));
            response.setHasError(false);
            return response;
        }

        log.info("----end get Utilisateur-----");
        return response;
    }

    /**
     * get full UtilisateurDto by using Utilisateur as object.
     *
     * @param dto
     * @param size
     * @param isSimpleLoading
     * @param locale
     * @return
     */
    private UtilisateurDto getFullInfos(UtilisateurDto dto, Integer size, Boolean isSimpleLoading, Locale locale) {
        log.info(this.getClass().getName()+"getFullInfos Start !");

        List<UtilisateurDirection> utilisateurDirections = utilisateurDirectionRepository.findByIdUtilisateur(dto.getId(), false);
        if (utilisateurDirections != null) {
            List<Direction> directions = new ArrayList<>();
            utilisateurDirections.stream().map(userDir -> directions.add(userDir.getDirection())).collect(Collectors.toList());
            List<DirectionDto> directionDtos = directions.stream().map(direction -> {
                try {
                    return DirectionTransformer.INSTANCE.toDto(direction);
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
            }).collect(Collectors.toList());
            dto.setDirections(directionDtos);
            log.info("roles {}",dto.getDirections());
        }

        List<UtilisateurRole> utilisateurRoles = utilisateurRoleRepository.findByUtilisateurId(dto.getId(), false);
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

            dto.setRoles(rolesDtos);
            log.info("roles {}",dto.getRoles());
        }

        dto.setPassword(null);
        if (Utilities.isTrue(isSimpleLoading)) {
            return dto;
        }
        if (size > 1) {
            return dto;
        }
        log.info(this.getClass().getName()+"getFullInfos End !");
        return dto;
    }

    public Response<UtilisateurDto> custom(Request<UtilisateurDto> request, Locale locale) {
        log.info("----begin custom UtilisateurDto-----");
        Response<UtilisateurDto> response = new Response<UtilisateurDto>();

        response.setHasError(false);
        response.setCount(1L);
        response.setItems(Arrays.asList(new UtilisateurDto()));
        log.info("----end custom UtilisateurDto-----");
        return response;
    }

    public void refreshToken(
            @NotNull HttpServletRequest request,
            @NotNull HttpServletResponse response
    ) throws IOException {

        String authToken = request.getHeader("Authorization");

        if (authToken != null && authToken.startsWith("Bearer ")) {
            try {
                String      refreshtoken = authToken.substring(7);
                Algorithm   algorithm    = Algorithm.HMAC256(SECRET);
                JWTVerifier jwtVerifier  = JWT.require(algorithm).build();
                DecodedJWT  decodedJWT   = jwtVerifier.verify(refreshtoken);
                String      username     = decodedJWT.getSubject();
                Utilisateur utilisateur  = utilisateurRepository.findByEmail(username, false);
                List<Role>  roles        = new ArrayList<>();

                utilisateurRoleRepository.findUserRoleByEmail(utilisateur.getEmail())
                        .forEach(userRole -> roles.add(userRole.getRole()));
                List<String> authorities = new ArrayList<>();

                roles.forEach(role -> authorities.add(role.getName()));
                System.out.println(authorities);

                String accessToken = JWT.create()
                        .withSubject(utilisateur.getEmail()) //
                        .withExpiresAt(new Date(System.currentTimeMillis() + ACCESS_TOKEN_EXPIRATION))
                        .withClaim("roles", authorities)
                        .sign(algorithm);

                Map<String, String> idToken = new HashMap<>();
                idToken.put("access_token", accessToken);
                idToken.put("refresh_token", refreshtoken);
                response.setContentType("application/json");
                new ObjectMapper().writeValue(response.getOutputStream(), idToken);
            } catch (Exception e) {
                response.addHeader("error-Messsage", e.getMessage());
                response.sendError(HttpServletResponse.SC_FORBIDDEN);
            }
        } else {
            throw new RuntimeException("Refresh token required !!!");
        }
    }

    public Response<UtilisateurDto> changeMotDePasse(ChangePasswordRequest dto) throws ParseException {
        log.info(this.getClass().getName() + ".changeMotDePasse Start !");
        Response<UtilisateurDto> response = new Response<>();
        validate(dto);
        Optional<Utilisateur> utilisateurOptional = utilisateurRepository.findById(dto.getId());
        if (utilisateurOptional.isEmpty()) {
            log.warn("Aucun utilisateur n'a ete trouvé avec l'ID"+dto.getId());
            throw new FunctionalException(functionalError.OPERATION("Aucun utilisateur n'a ete trouve",Locale.FRANCE));
        }

        Utilisateur utilisateur = utilisateurOptional.get();
        if (utilisateur.getIsFirstConnexion()) {
            utilisateur.setIsFirstConnexion(false);
        }
        utilisateur.setPassword(new BCryptPasswordEncoder().encode(dto.getNewPassword()));

        response.setItem(UtilisateurTransformer.INSTANCE.toDto(utilisateurRepository.save(utilisateur)));
        Status status = new Status();
        status.setCode(StatusCode.SUCCESS);
        status.setMessage(StatusMessage.SUCCESS);
        response.setHasError(false);
        response.setHttpCode(HttpStatus.OK.value());
        response.setStatus(status);
        response.setHasError(false);
        log.info("End service " +this.getClass().getName() + ".changeMotDePasse");
        return response;
    }

    private void validate(ChangePasswordRequest dto){
        if(dto ==null){
            log.warn("Impossible de modifier le mot de passe avec un objet NULL");
            throw new FunctionalException(functionalError.OPERATION("Aucune information n'a ete fourni pour pouvoir changer le mot de passe",Locale.FRANCE));

        }
        if(dto.getId() ==null){
            log.warn("Impossible de modifier le mot de passe avec un IDN NULL");
            throw new FunctionalException(functionalError.OPERATION("ID utilisateur null::Impossible de modifier le mot de passe",Locale.FRANCE));

        }
        if(Objects.isNull(dto.getConfirmationPassword()) || Objects.isNull(dto.getNewPassword())){
            log.warn("Impossible de modifier le mot de passe avec un mot de passse NULL");
            throw new FunctionalException(functionalError.OPERATION("Mot de passe utilisateur null::Impossible de modifier le mot de passe",Locale.FRANCE));

        }
        if(!dto.getConfirmationPassword().equals(dto.getNewPassword())){
            log.warn("Impossible de modifier le mot de passe avec deux mots de passe different NULL");
            throw new FunctionalException(functionalError.OPERATION("Mot de passe utilisateur non conformes::Impossible de modifier le mot de passe ",Locale.FRANCE));

        }

    }

    public Response<UtilisateurDto> createAdmin(Request<UtilisateurDto> request, Locale locale) throws ParseException {
        log.info(this.getClass().getName()+".createAdmin");

        Response<UtilisateurDto> response = new Response<UtilisateurDto>();
        List<Utilisateur>        items    = new ArrayList<Utilisateur>();

        for (UtilisateurDto dto : request.getDatas()) {
            // Definir les parametres obligatoires
            Map<String, java.lang.Object> fieldsToVerify = new HashMap<String, java.lang.Object>();
            fieldsToVerify.put("email", dto.getEmail());
            if (!Validate.RequiredValue(fieldsToVerify).isGood()) {
                throw new InvalidEntityException(functionalError.FIELD_EMPTY(Validate.getValidate().getField(), locale));
            }

            // Verify if utilisateur to insert do not exist
            Utilisateur existingEntity = null;
            if (existingEntity != null) {
                throw new InternalErrorException(functionalError.DATA_EXIST("utilisateur id -> " + dto.getId(), locale));
            }

            // verif unique email in db
            existingEntity = utilisateurRepository.findByEmail(dto.getEmail(), false);
            if (existingEntity != null) {
                throw new InternalErrorException(functionalError.DATA_EXIST("utilisateur email -> " + dto.getEmail(), locale));
            }
            // verif unique email in items to save
            if (items.stream().anyMatch(a -> a.getEmail().equalsIgnoreCase(dto.getEmail()))) {
                throw new InternalErrorException(functionalError.DATA_DUPLICATE(" email ", locale));
            }


            Utilisateur entityToSave = null;
            entityToSave = UtilisateurTransformer.INSTANCE.toEntity(dto);
            entityToSave.setCreatedAt(Utilities.getCurrentDate());
            entityToSave.setCreatedBy(request.getUser());
            entityToSave.setPassword(new BCryptPasswordEncoder().encode(defaultPassword));
            entityToSave.setIsDeleted(false);
            items.add(entityToSave);

            Role role = roleRepository.findByName("ADMIN",false);
            UtilisateurRole utilisateurRole = UtilisateurRole.builder()
                    .role(role)
                    .utilisateur(entityToSave)
                    .createdBy(request.getUser())
                    .createdAt(Utilities.getCurrentDate())
                    .isDeleted(false)
                    .build();
            utilisateurRoleRepository.save(utilisateurRole);
        }

        if (!items.isEmpty()) {
            List<Utilisateur> itemsSaved = null;
            // inserer les donnees en base de donnees
            itemsSaved = utilisateurRepository.saveAll((Iterable<Utilisateur>) items);
            if (itemsSaved == null) {
                throw new InternalErrorException(functionalError.SAVE_FAIL("utilisateur", locale));
            }
            List<UtilisateurDto> itemsDto = (Utilities.isTrue(request.getIsSimpleLoading())) ? UtilisateurTransformer.INSTANCE.toLiteDtos(itemsSaved) : UtilisateurTransformer.INSTANCE.toDtos(itemsSaved);

            final int    size        = itemsSaved.size();
            List<String> listOfError = Collections.synchronizedList(new ArrayList<String>());
            itemsDto.parallelStream().forEach(dto -> {
                try {
                    dto = getFullInfos(dto, size, request.getIsSimpleLoading(), locale);
                } catch (Exception e) {
                    listOfError.add(e.getMessage());
                    e.printStackTrace();
                }
            });
            if (Utilities.isNotEmpty(listOfError)) {
                Object[] objArray = listOfError.stream().distinct().toArray();
                throw new RuntimeException(StringUtils.join(objArray, ", "));
            }
            Status status = new Status();
            status.setCode(StatusCode.SUCCESS);
            status.setMessage(StatusMessage.SUCCESS);

            response.setHttpCode(HttpStatus.CREATED.value());
            response.setStatus(status);
            response.setItems(itemsDto);
            response.setHasError(false);
        }

        log.info(this.getClass().getName() +".createAdmin End !");
        return response;
    }



}
