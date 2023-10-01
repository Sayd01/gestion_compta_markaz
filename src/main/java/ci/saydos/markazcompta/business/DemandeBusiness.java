
/*
 * Java business for entity table demande
 * Created on 2023-08-06 ( Time 01:31:05 )
 * Generator tool : Telosys Tools Generator ( version 3.3.0 )
 * Copyright 2018 Geo. All Rights Reserved.
 */

package ci.saydos.markazcompta.business;

import ci.saydos.markazcompta.utils.enums.StatutDemandeEnum;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.apache.commons.lang3.StringUtils;
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
import ci.saydos.markazcompta.dao.entity.Demande;
import ci.saydos.markazcompta.dao.entity.Utilisateur;
import ci.saydos.markazcompta.dao.entity.*;
import ci.saydos.markazcompta.dao.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * BUSINESS for table "demande"
 *
 * @author Geo
 */
@Log
@Service
@RequiredArgsConstructor
@Transactional
public class DemandeBusiness implements IBasicBusiness<Request<DemandeDto>, Response<DemandeDto>> {

    private final DirectionRepository         directionRepository;
    private final DemandeRepository           demandeRepository;
    private final DepenseRepository           depenseRepository;
    private final DemandeHistoriqueRepository demandeHistoriqueRepository;
    private final DemandeHistoriqueBusiness   demandeHistoriqueBusiness;
    private final UtilisateurRepository       utilisateurRepository;
    private final FunctionalError             functionalError;

    @PersistenceContext
    private       EntityManager    em;
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");


    /**
     * create Demande by using DemandeDto as object.
     *
     * @param request
     * @return response
     */
    @Override
    public Response<DemandeDto> create(Request<DemandeDto> request, Locale locale) throws ParseException {
        log.info("----begin create Demande-----");

        Response<DemandeDto>    response        = new Response<DemandeDto>();
        List<Demande>           items           = new ArrayList<>();
        List<DemandeHistorique> itemsHistorique = new ArrayList<>();

        for (DemandeDto dto : request.getDatas()) {
            // Definir les parametres obligatoires
            Map<String, java.lang.Object> fieldsToVerify = new HashMap<String, java.lang.Object>();
            fieldsToVerify.put("label", dto.getLabel());
            fieldsToVerify.put("montant", dto.getMontant());
            fieldsToVerify.put("idDirection", dto.getIdDirection());
            if (!Validate.RequiredValue(fieldsToVerify).isGood()) {
                throw new InvalidEntityException(functionalError.FIELD_EMPTY(Validate.getValidate().getField(), locale));
            }

            // Verify if utilisateur exist
            Utilisateur existingUtilisateur = null;
            if (request.getUser() != null && request.getUser() > 0) {
                existingUtilisateur = utilisateurRepository.findOne(request.getUser(), false);
                if (existingUtilisateur == null) {
                    throw new EntityNotFoundException(functionalError.DATA_NOT_EXIST("utilisateur idUtilisateur -> " + request.getUser(), locale));
                }
            }
            Direction existingDirection = null;
            if (dto.getIdDirection() != null && dto.getIdDirection() > 0) {
                existingDirection = directionRepository.findOne(dto.getIdDirection(), false);
                if (existingDirection == null) {
                    throw new EntityNotFoundException(functionalError.DATA_NOT_EXIST("Direction idDirection -> " + dto.getIdDirection(), locale));
                }
            }


            String  Prefix       = "dem";
            Demande entityToSave = null;
            entityToSave = DemandeTransformer.INSTANCE.toEntity(dto, existingUtilisateur, existingDirection);
            entityToSave.setCreatedAt(Utilities.getCurrentDate());
            entityToSave.setCreatedBy(request.getUser());
            entityToSave.setIsDeleted(false);
            entityToSave.setCode(Utilities.generateUniqueCode(Prefix, 10));
            entityToSave.setDateDebut(Utilities.getCurrentDate());
            entityToSave.setStatut(StatutDemandeEnum.INITIE);
            items.add(entityToSave);

            DemandeHistorique demandeHistorique = new DemandeHistorique();
            demandeHistorique.setDemande(entityToSave);
            demandeHistorique.setUtilisateur(existingUtilisateur);
            demandeHistorique.setCreatedAt(Utilities.getCurrentDate());
            demandeHistorique.setCreatedBy(request.getUser());
            demandeHistorique.setStatut(StatutDemandeEnum.INITIE);
            demandeHistorique.setIsDeleted(false);
            itemsHistorique.add(demandeHistorique);
        }

        if (!items.isEmpty()) {
            List<Demande> itemsSaved = null;
            // inserer les donnees en base de donnees
            itemsSaved = demandeRepository.saveAll((Iterable<Demande>) items);
            if (itemsSaved == null) {
                throw new InternalErrorException(functionalError.SAVE_FAIL("demande", locale));
            }
            List<DemandeDto> itemsDto = (Utilities.isTrue(request.getIsSimpleLoading())) ? DemandeTransformer.INSTANCE.toLiteDtos(itemsSaved) : DemandeTransformer.INSTANCE.toDtos(itemsSaved);

            List<DemandeHistorique> itemsDemandeSaved = null;
            itemsDemandeSaved = demandeHistoriqueRepository.saveAll(itemsHistorique);
            if (itemsDemandeSaved == null) {
                throw new InternalErrorException(functionalError.SAVE_FAIL("demande", locale));
            }

            final int    size        = itemsSaved.size();
            List<String> listOfError = Collections.synchronizedList(new ArrayList<>());
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

        log.info("----end create Demande-----");
        return response;
    }

    /**
     * update Demande by using DemandeDto as object.
     *
     * @param request
     * @return response
     */
    @Override
    public Response<DemandeDto> update(Request<DemandeDto> request, Locale locale) throws ParseException {
        log.info("----begin update Demande-----");

        Response<DemandeDto> response = new Response<DemandeDto>();
        List<Demande>        items    = new ArrayList<Demande>();

        for (DemandeDto dto : request.getDatas()) {
            // Definir les parametres obligatoires
            Map<String, java.lang.Object> fieldsToVerify = new HashMap<String, java.lang.Object>();
            fieldsToVerify.put("id", dto.getId());
            if (!Validate.RequiredValue(fieldsToVerify).isGood()) {
                throw new InvalidEntityException(functionalError.FIELD_EMPTY(Validate.getValidate().getField(), locale));
            }

            // Verifier si la demande existe
            Demande entityToSave = null;
            entityToSave = demandeRepository.findOne(dto.getId(), false);
            if (entityToSave == null) {
                throw new EntityNotFoundException(functionalError.DATA_NOT_EXIST("demande id -> " + dto.getId(), locale));
            }

            // Verify if utilisateur exist
            if (dto.getIdUtilisateur() != null && dto.getIdUtilisateur() > 0) {
                Utilisateur existingUtilisateur = utilisateurRepository.findOne(dto.getIdUtilisateur(), false);
                if (existingUtilisateur == null) {
                    throw new EntityNotFoundException(functionalError.DATA_NOT_EXIST("utilisateur idUtilisateur -> " + dto.getIdUtilisateur(), locale));
                }
                entityToSave.setUtilisateur(existingUtilisateur);
            }
            if (Utilities.notBlank(dto.getLabel())) {
                entityToSave.setLabel(dto.getLabel());
            }
            if (Utilities.notBlank(dto.getCode())) {
                entityToSave.setCode(dto.getCode());
            }
            if (Utilities.notBlank(dto.getDescription())) {
                entityToSave.setDescription(dto.getDescription());
            }
            if (dto.getMontant() != null && dto.getMontant() > 0) {
                entityToSave.setMontant(dto.getMontant());
            }
            if (Utilities.notBlank(dto.getImageUrl())) {
                entityToSave.setImageUrl(dto.getImageUrl());
            }
            if (Utilities.notBlank(dto.getDateDebut())) {
                entityToSave.setDateDebut(dateFormat.parse(dto.getDateDebut()));
            }
            if (Utilities.notBlank(dto.getDateFin())) {
                entityToSave.setDateFin(dateFormat.parse(dto.getDateFin()));
            }
            if (dto.getStatut() != null) {
                entityToSave.setStatut(dto.getStatut());
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
            List<Demande> itemsSaved = null;
            // maj les donnees en base
            itemsSaved = demandeRepository.saveAll((Iterable<Demande>) items);
            if (itemsSaved == null) {
                throw new InternalErrorException(functionalError.SAVE_FAIL("demande", locale));
            }
            List<DemandeDto> itemsDto = (Utilities.isTrue(request.getIsSimpleLoading())) ? DemandeTransformer.INSTANCE.toLiteDtos(itemsSaved) : DemandeTransformer.INSTANCE.toDtos(itemsSaved);

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

        log.info("----end update Demande-----");
        return response;
    }

    /**
     * delete Demande by using DemandeDto as object.
     *
     * @param request
     * @return response
     */
    @Override
    public Response<DemandeDto> delete(Request<DemandeDto> request, Locale locale) {
        log.info("----begin delete Demande-----");

        Response<DemandeDto> response = new Response<DemandeDto>();
        List<Demande>        items    = new ArrayList<Demande>();

        for (DemandeDto dto : request.getDatas()) {
            // Definir les parametres obligatoires
            Map<String, java.lang.Object> fieldsToVerify = new HashMap<String, java.lang.Object>();
            fieldsToVerify.put("id", dto.getId());
            if (!Validate.RequiredValue(fieldsToVerify).isGood()) {
                throw new InvalidEntityException(functionalError.FIELD_EMPTY(Validate.getValidate().getField(), locale));
            }

            // Verifier si la demande existe
            Demande existingEntity = null;
            existingEntity = demandeRepository.findOne(dto.getId(), false);
            if (existingEntity == null) {
                throw new EntityNotFoundException(functionalError.DATA_NOT_EXIST("demande -> " + dto.getId(), locale));
            }

            // -----------------------------------------------------------------------
            // ----------- CHECK IF DATA IS USED
            // -----------------------------------------------------------------------

            // depense
            List<Depense> listOfDepense = depenseRepository.findByIdDemande(existingEntity.getId(), false);
            if (listOfDepense != null && !listOfDepense.isEmpty()) {
                throw new InternalErrorException(functionalError.DATA_NOT_DELETABLE("(" + listOfDepense.size() + ")", locale));
            }
            // demandeHistorique
            List<DemandeHistorique> listOfDemandeHistorique = demandeHistoriqueRepository.findByIdDemande(existingEntity.getId(), false);
            if (listOfDemandeHistorique != null && !listOfDemandeHistorique.isEmpty()) {
                throw new InternalErrorException(functionalError.DATA_NOT_DELETABLE("(" + listOfDemandeHistorique.size() + ")", locale));
            }


            existingEntity.setIsDeleted(true);
            items.add(existingEntity);
        }

        if (!items.isEmpty()) {
            // supprimer les donnees en base
            demandeRepository.saveAll((Iterable<Demande>) items);

            Status status = new Status();
            status.setCode(StatusCode.SUCCESS);
            status.setMessage(StatusMessage.SUCCESS);

            response.setHasError(false);
            response.setHttpCode(HttpStatus.OK.value());
            response.setStatus(status);
        }

        log.info("----end delete Demande-----");
        return response;
    }

    /**
     * get Demande by using DemandeDto as object.
     *
     * @param request
     * @return response
     */
    @Override
    public Response<DemandeDto> getByCriteria(Request<DemandeDto> request, Locale locale) throws Exception {
        log.info("----begin get Demande-----");

        Response<DemandeDto> response = new Response<DemandeDto>();
        List<Demande>        items    = demandeRepository.getByCriteria(request, em, locale);

        if (items != null && !items.isEmpty()) {
            List<DemandeDto> itemsDto = (Utilities.isTrue(request.getIsSimpleLoading())) ? DemandeTransformer.INSTANCE.toLiteDtos(items) : DemandeTransformer.INSTANCE.toDtos(items);

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
            response.setCount(demandeRepository.count(request, em, locale));
            response.setHasError(false);
        } else {
            response.setStatus(functionalError.DATA_EMPTY("demande", locale));
            response.setHasError(false);
            return response;
        }

        log.info("----end get Demande-----");
        return response;
    }

    /**
     * get full DemandeDto by using Demande as object.
     *
     * @param dto
     * @param size
     * @param isSimpleLoading
     * @param locale
     * @return
     * @throws Exception
     */
    private DemandeDto getFullInfos(DemandeDto dto, Integer size, Boolean isSimpleLoading, Locale locale) throws Exception {
        // put code here

        if (Utilities.isTrue(isSimpleLoading)) {
            return dto;
        }
        if (size > 1) {
            return dto;
        }

        return dto;
    }

    public Response<DemandeDto> custom(Request<DemandeDto> request, Locale locale) {
        log.info("----begin custom DemandeDto-----");
        Response<DemandeDto> response = new Response<DemandeDto>();

        response.setHasError(false);
        response.setCount(1L);
        response.setItems(Arrays.asList(new DemandeDto()));
        log.info("----end custom DemandeDto-----");
        return response;
    }

    public Response<DemandeDto> valide(Request<DemandeDto> request, Locale locale) throws ParseException {
        log.info("----begin create Demande-----");

        Response<DemandeDto>           response           = new Response<DemandeDto>();
        Response<DemandeHistoriqueDto> responseHistorique = new Response<>();
        List<Demande>                  items              = new ArrayList<>();
        List<DemandeHistorique>        itemsHistorique    = new ArrayList<>();

        for (DemandeDto dto : request.getDatas()) {
            // Definir les parametres obligatoires
            Map<String, java.lang.Object> fieldsToVerify = new HashMap<String, java.lang.Object>();
            fieldsToVerify.put("id", dto.getId());
            fieldsToVerify.put("statut", dto.getStatut());
            if (!Validate.RequiredValue(fieldsToVerify).isGood()) {
                throw new InvalidEntityException(functionalError.FIELD_EMPTY(Validate.getValidate().getField(), locale));
            }

            Demande entityToSave = null;
            entityToSave = demandeRepository.findOne(dto.getId(), false);
            if (entityToSave == null) {
                throw new EntityNotFoundException(functionalError.DATA_NOT_EXIST("demande id -> " + dto.getId(), locale));
            }

            // Verify if utilisateur exist
            Utilisateur existingUtilisateur = null;
            if (request.getUser() != null && request.getUser() > 0) {
                existingUtilisateur = utilisateurRepository.findOne(request.getUser(), false);
                if (existingUtilisateur == null) {
                    throw new EntityNotFoundException(functionalError.DATA_NOT_EXIST("utilisateur idUtilisateur -> " + dto.getIdUtilisateur(), locale));
                }
            }

            DemandeHistorique demandeHistorique = demandeHistoriqueRepository.findByUserAndDemandeAndStatut(
                    entityToSave.getUtilisateur().getId(),
                    entityToSave.getId(),
                    dto.getStatut(),
                    false);

            if (demandeHistorique != null) {
                throw new InternalErrorException(functionalError.DATA_EXIST("demande id -> " + dto.getId(),locale));
            }

            if (dto.getStatut().equals(StatutDemandeEnum.INVALIDE)) {
                entityToSave.setStatut(dto.getStatut());
                entityToSave.setUtilisateur(existingUtilisateur);
                entityToSave.setUpdatedAt(Utilities.getCurrentDate());
                entityToSave.setUpdatedBy(request.getUser());
                entityToSave.setDateFin(Utilities.getCurrentDate());
                items.add(entityToSave);
            } else if (dto.getStatut().equals(StatutDemandeEnum.VALIDE)) {
                entityToSave.setStatut(dto.getStatut());
                entityToSave.setUtilisateur(existingUtilisateur);
                entityToSave.setUpdatedAt(Utilities.getCurrentDate());
                entityToSave.setUpdatedBy(request.getUser());
                items.add(entityToSave);
            } else {
                throw new InternalErrorException(functionalError.DISALLOWED_OPERATION("Cette demande est en cours de traitement ou terminée",locale));
            }

            DemandeHistoriqueDto demandeHistoriqueDto = new DemandeHistoriqueDto();
            demandeHistoriqueDto.setIdDemande(entityToSave.getId());
            demandeHistoriqueDto.setStatut(dto.getStatut());
            demandeHistoriqueDto.setCreatedBy(request.getUser());
            demandeHistoriqueDto.setIdUtilisateur(request.getUser());

            Request<DemandeHistoriqueDto> requestHistorique = new Request<>();
            requestHistorique.setUser(request.getUser());
            requestHistorique.setDatas(Arrays.asList(demandeHistoriqueDto));
            demandeHistoriqueBusiness.create(requestHistorique, locale);


        }

        if (!items.isEmpty()) {
            List<Demande> itemsSaved = null;
            // inserer les donnees en base de donnees
            itemsSaved = demandeRepository.saveAll((Iterable<Demande>) items);
            if (itemsSaved == null) {
                throw new InternalErrorException(functionalError.SAVE_FAIL("demande", locale));
            }
            List<DemandeDto> itemsDto = (Utilities.isTrue(request.getIsSimpleLoading())) ? DemandeTransformer.INSTANCE.toLiteDtos(itemsSaved) : DemandeTransformer.INSTANCE.toDtos(itemsSaved);

            List<DemandeHistorique> itemsDemandeSaved = null;
            itemsDemandeSaved = demandeHistoriqueRepository.saveAll(itemsHistorique);
            if (itemsDemandeSaved == null) {
                throw new InternalErrorException(functionalError.SAVE_FAIL("demande", locale));
            }

            final int    size        = itemsSaved.size();
            List<String> listOfError = Collections.synchronizedList(new ArrayList<>());
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

        log.info("----end create Demande-----");
        return response;
    }


}