                                        									
/*
 * Java business for entity table utilisateur_direction
 * Created on 2023-08-29 ( Time 13:35:25 )
 * Generator tool : Telosys Tools Generator ( version 3.3.0 )
 * Copyright 2018 Geo. All Rights Reserved.
 */

package ci.saydos.markazcompta.business;

import lombok.extern.java.Log;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.http.HttpStatus;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import ci.saydos.markazcompta.utils.*;
import ci.saydos.markazcompta.utils.dto.*;
import ci.saydos.markazcompta.utils.exception.*;
import ci.saydos.markazcompta.utils.contract.IBasicBusiness;
import ci.saydos.markazcompta.utils.contract.Request;
import ci.saydos.markazcompta.utils.contract.Response;
import ci.saydos.markazcompta.utils.dto.transformer.*;
import ci.saydos.markazcompta.dao.entity.UtilisateurDirection;
import ci.saydos.markazcompta.dao.entity.Utilisateur;
import ci.saydos.markazcompta.dao.entity.Direction;
import ci.saydos.markazcompta.dao.entity.*;
import ci.saydos.markazcompta.dao.repository.*;

/**
 * BUSINESS for table "utilisateur_direction"
 *
 * @author Geo
 */
@Log
@Component
public class UtilisateurDirectionBusiness implements IBasicBusiness<Request<UtilisateurDirectionDto>, Response<UtilisateurDirectionDto>> {

    private Response<UtilisateurDirectionDto> response;
    @Autowired
    private UtilisateurDirectionRepository    utilisateurDirectionRepository;
    @Autowired
    private UtilisateurRepository             utilisateurRepository;
    @Autowired
    private DirectionRepository               directionRepository;
    @Autowired
    private FunctionalError                   functionalError;
    @Autowired
    private TechnicalError                    technicalError;
    @Autowired
    private ExceptionUtils                    exceptionUtils;
    @PersistenceContext
    private EntityManager                     em;

    private SimpleDateFormat dateFormat;
    private SimpleDateFormat dateTimeFormat;

    public UtilisateurDirectionBusiness() {
        dateFormat     = new SimpleDateFormat("dd/MM/yyyy");
        dateTimeFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
    }

    /**
     * create UtilisateurDirection by using UtilisateurDirectionDto as object.
     *
     * @param request
     * @return response
     */
    @Override
    public Response<UtilisateurDirectionDto> create(Request<UtilisateurDirectionDto> request, Locale locale) throws ParseException {
        log.info("----begin create UtilisateurDirection-----");

        Response<UtilisateurDirectionDto> response = new Response<UtilisateurDirectionDto>();
        List<UtilisateurDirection>        items    = new ArrayList<UtilisateurDirection>();

        for (UtilisateurDirectionDto dto : request.getDatas()) {
            // Definir les parametres obligatoires
            Map<String, java.lang.Object> fieldsToVerify = new HashMap<String, java.lang.Object>();
            fieldsToVerify.put("directionCode", dto.getDirectionCode());
            fieldsToVerify.put("utilisateurEmail", dto.getUtilisateurEmail());
            if (!Validate.RequiredValue(fieldsToVerify).isGood()) {
                throw new InvalidEntityException(functionalError.FIELD_EMPTY(Validate.getValidate().getField(), locale));
            }

            // Verify if direction exist
            Direction existingDirection = null;
            existingDirection = directionRepository.findByCode(dto.getDirectionCode(), false);
            if (existingDirection == null) {
                throw new EntityNotFoundException(functionalError.DATA_NOT_EXIST("direction codeDirection -> " + dto.getDirectionCode(), locale));
            }

            // Verify if utilisateur exist
            Utilisateur existingUtilisateur = null;
            existingUtilisateur = utilisateurRepository.findByEmail(dto.getUtilisateurEmail(), false);
            if (existingUtilisateur == null) {
                throw new EntityNotFoundException(functionalError.DATA_NOT_EXIST("utilisateur Email -> " + dto.getIdUtilisateur(), locale));
            }

            UtilisateurDirection utilisateurDirection = utilisateurDirectionRepository.findByDirectionAndUser(existingUtilisateur.getEmail(), existingDirection.getCode(), false);
            if (utilisateurDirection != null) {
                throw new InternalErrorException(functionalError.DATA_EXIST(existingUtilisateur.getFirstName()+" appartient déja à la direction "+existingDirection.getIntitule(),locale));
            }

            UtilisateurDirection entityToSave = null;
            entityToSave = UtilisateurDirectionTransformer.INSTANCE.toEntity(dto, existingUtilisateur, existingDirection);
            entityToSave.setCreatedAt(Utilities.getCurrentDate());
            entityToSave.setCreatedBy(request.getUser());
            entityToSave.setIsDeleted(false);
            items.add(entityToSave);
        }

        if (!items.isEmpty()) {
            List<UtilisateurDirection> itemsSaved = null;
            // inserer les donnees en base de donnees
            itemsSaved = utilisateurDirectionRepository.saveAll((Iterable<UtilisateurDirection>) items);
            if (itemsSaved == null) {
                throw new InternalErrorException(functionalError.SAVE_FAIL("utilisateurDirection", locale));
            }
            List<UtilisateurDirectionDto> itemsDto = (Utilities.isTrue(request.getIsSimpleLoading())) ? UtilisateurDirectionTransformer.INSTANCE.toLiteDtos(itemsSaved) : UtilisateurDirectionTransformer.INSTANCE.toDtos(itemsSaved);

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

        log.info("----end create UtilisateurDirection-----");
        return response;
    }

    /**
     * update UtilisateurDirection by using UtilisateurDirectionDto as object.
     *
     * @param request
     * @return response
     */
    @Override
    public Response<UtilisateurDirectionDto> update(Request<UtilisateurDirectionDto> request, Locale locale) throws ParseException {
        log.info("----begin update UtilisateurDirection-----");

        Response<UtilisateurDirectionDto> response = new Response<UtilisateurDirectionDto>();
        List<UtilisateurDirection>        items    = new ArrayList<UtilisateurDirection>();

        for (UtilisateurDirectionDto dto : request.getDatas()) {
            // Definir les parametres obligatoires
            Map<String, java.lang.Object> fieldsToVerify = new HashMap<String, java.lang.Object>();
            fieldsToVerify.put("id", dto.getId());
            if (!Validate.RequiredValue(fieldsToVerify).isGood()) {
                throw new InvalidEntityException(functionalError.FIELD_EMPTY(Validate.getValidate().getField(), locale));
            }

            // Verifier si la utilisateurDirection existe
            UtilisateurDirection entityToSave = null;
            entityToSave = utilisateurDirectionRepository.findOne(dto.getId(), false);
            if (entityToSave == null) {
                throw new EntityNotFoundException(functionalError.DATA_NOT_EXIST("utilisateurDirection id -> " + dto.getId(), locale));
            }

            // Verify if utilisateur exist
            if (dto.getIdUtilisateur() != null && dto.getIdUtilisateur() > 0) {
                Utilisateur existingUtilisateur = utilisateurRepository.findOne(dto.getIdUtilisateur(), false);
                if (existingUtilisateur == null) {
                    throw new EntityNotFoundException(functionalError.DATA_NOT_EXIST("utilisateur idUtilisateur -> " + dto.getIdUtilisateur(), locale));
                }
                entityToSave.setUtilisateur(existingUtilisateur);
            }
            // Verify if direction exist
            if (dto.getIdDirection() != null && dto.getIdDirection() > 0) {
                Direction existingDirection = directionRepository.findOne(dto.getIdDirection(), false);
                if (existingDirection == null) {
                    throw new EntityNotFoundException(functionalError.DATA_NOT_EXIST("direction idDirection -> " + dto.getIdDirection(), locale));
                }
                entityToSave.setDirection(existingDirection);
            }
            if (dto.getCreatedBy() != null && dto.getCreatedBy() > 0) {
                entityToSave.setCreatedBy(dto.getCreatedBy());
            }
            if (dto.getUpdatedBy() != null && dto.getUpdatedBy() > 0) {
                entityToSave.setUpdatedBy(dto.getUpdatedBy());
            }
            entityToSave.setUpdatedAt(Utilities.getCurrentDate());
            entityToSave.setUpdatedBy(request.getUser());
            items.add(entityToSave);
        }

        if (!items.isEmpty()) {
            List<UtilisateurDirection> itemsSaved = null;
            // maj les donnees en base
            itemsSaved = utilisateurDirectionRepository.saveAll((Iterable<UtilisateurDirection>) items);
            if (itemsSaved == null) {
                throw new InternalErrorException(functionalError.SAVE_FAIL("utilisateurDirection", locale));
            }
            List<UtilisateurDirectionDto> itemsDto = (Utilities.isTrue(request.getIsSimpleLoading())) ? UtilisateurDirectionTransformer.INSTANCE.toLiteDtos(itemsSaved) : UtilisateurDirectionTransformer.INSTANCE.toDtos(itemsSaved);

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

        log.info("----end update UtilisateurDirection-----");
        return response;
    }

    /**
     * delete UtilisateurDirection by using UtilisateurDirectionDto as object.
     *
     * @param request
     * @return response
     */
    @Override
    public Response<UtilisateurDirectionDto> delete(Request<UtilisateurDirectionDto> request, Locale locale) {
        log.info("----begin delete UtilisateurDirection-----");

        Response<UtilisateurDirectionDto> response = new Response<UtilisateurDirectionDto>();
        List<UtilisateurDirection>        items    = new ArrayList<UtilisateurDirection>();

        for (UtilisateurDirectionDto dto : request.getDatas()) {
            // Definir les parametres obligatoires
            Map<String, java.lang.Object> fieldsToVerify = new HashMap<String, java.lang.Object>();
            fieldsToVerify.put("id", dto.getId());
            if (!Validate.RequiredValue(fieldsToVerify).isGood()) {
                throw new InvalidEntityException(functionalError.FIELD_EMPTY(Validate.getValidate().getField(), locale));
            }

            // Verifier si la utilisateurDirection existe
            UtilisateurDirection existingEntity = null;
            existingEntity = utilisateurDirectionRepository.findOne(dto.getId(), false);
            if (existingEntity == null) {
                throw new EntityNotFoundException(functionalError.DATA_NOT_EXIST("utilisateurDirection -> " + dto.getId(), locale));
            }

            // -----------------------------------------------------------------------
            // ----------- CHECK IF DATA IS USED
            // -----------------------------------------------------------------------


            existingEntity.setIsDeleted(true);
            items.add(existingEntity);
        }

        if (!items.isEmpty()) {
            // supprimer les donnees en base
            utilisateurDirectionRepository.saveAll((Iterable<UtilisateurDirection>) items);

            Status status = new Status();
            status.setCode(StatusCode.SUCCESS);
            status.setMessage(StatusMessage.SUCCESS);

            response.setHasError(false);
            response.setHttpCode(HttpStatus.OK.value());
            response.setStatus(status);
        }

        log.info("----end delete UtilisateurDirection-----");
        return response;
    }

    /**
     * get UtilisateurDirection by using UtilisateurDirectionDto as object.
     *
     * @param request
     * @return response
     */
    @Override
    public Response<UtilisateurDirectionDto> getByCriteria(Request<UtilisateurDirectionDto> request, Locale locale) throws Exception {
        log.info("----begin get UtilisateurDirection-----");

        Response<UtilisateurDirectionDto> response = new Response<UtilisateurDirectionDto>();
        List<UtilisateurDirection>        items    = utilisateurDirectionRepository.getByCriteria(request, em, locale);

        if (items != null && !items.isEmpty()) {
            List<UtilisateurDirectionDto> itemsDto = (Utilities.isTrue(request.getIsSimpleLoading())) ? UtilisateurDirectionTransformer.INSTANCE.toLiteDtos(items) : UtilisateurDirectionTransformer.INSTANCE.toDtos(items);

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
            response.setCount(utilisateurDirectionRepository.count(request, em, locale));
            response.setHasError(false);
        } else {
            response.setStatus(functionalError.DATA_EMPTY("utilisateurDirection", locale));
            response.setHasError(false);
            return response;
        }

        log.info("----end get UtilisateurDirection-----");
        return response;
    }

    /**
     * get full UtilisateurDirectionDto by using UtilisateurDirection as object.
     *
     * @param dto
     * @param size
     * @param isSimpleLoading
     * @param locale
     * @return
     * @throws Exception
     */
    private UtilisateurDirectionDto getFullInfos(UtilisateurDirectionDto dto, Integer size, Boolean isSimpleLoading, Locale locale) throws Exception {
        // put code here

        if (Utilities.isTrue(isSimpleLoading)) {
            return dto;
        }
        if (size > 1) {
            return dto;
        }

        return dto;
    }

    public Response<UtilisateurDirectionDto> custom(Request<UtilisateurDirectionDto> request, Locale locale) {
        log.info("----begin custom UtilisateurDirectionDto-----");
        Response<UtilisateurDirectionDto> response = new Response<UtilisateurDirectionDto>();

        response.setHasError(false);
        response.setCount(1L);
        response.setItems(Arrays.asList(new UtilisateurDirectionDto()));
        log.info("----end custom UtilisateurDirectionDto-----");
        return response;
    }
}
