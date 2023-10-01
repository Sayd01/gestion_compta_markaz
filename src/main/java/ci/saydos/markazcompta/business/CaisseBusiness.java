                                                        													
/*
 * Java business for entity table caisse
 * Created on 2023-08-10 ( Time 17:44:55 )
 * Generator tool : Telosys Tools Generator ( version 3.3.0 )
 * Copyright 2018 Geo. All Rights Reserved.
 */

package ci.saydos.markazcompta.business;

import ci.saydos.markazcompta.dao.entity.Caisse;
import ci.saydos.markazcompta.dao.entity.Utilisateur;
import ci.saydos.markazcompta.dao.repository.CaisseRepository;
import ci.saydos.markazcompta.dao.repository.DepenseRepository;
import ci.saydos.markazcompta.dao.repository.UtilisateurRepository;
import ci.saydos.markazcompta.utils.*;
import ci.saydos.markazcompta.utils.contract.IBasicBusiness;
import ci.saydos.markazcompta.utils.contract.Request;
import ci.saydos.markazcompta.utils.contract.Response;
import ci.saydos.markazcompta.utils.dto.CaisseDto;
import ci.saydos.markazcompta.utils.dto.transformer.CaisseTransformer;
import ci.saydos.markazcompta.utils.enums.TypeCaisseEnum;
import ci.saydos.markazcompta.utils.exception.EntityNotFoundException;
import ci.saydos.markazcompta.utils.exception.InternalErrorException;
import ci.saydos.markazcompta.utils.exception.InvalidEntityException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * BUSINESS for table "caisse"
 *
 * @author Geo
 */
@Log
@Component
@RequiredArgsConstructor
@Transactional
public class CaisseBusiness implements IBasicBusiness<Request<CaisseDto>, Response<CaisseDto>> {

    private final CaisseRepository      caisseRepository;
    private final UtilisateurRepository utilisateurRepository;
    private final DepenseRepository     depenseRepository;
    private final FunctionalError       functionalError;


    @PersistenceContext
    private EntityManager em;

    private final SimpleDateFormat dateFormat     = new SimpleDateFormat("dd/MM/yyyy");
    private final SimpleDateFormat dateTimeFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");


    /**
     * create Caisse by using CaisseDto as object.
     *
     * @param request
     * @return response
     */
    @Override
    public Response<CaisseDto> create(Request<CaisseDto> request, Locale locale) throws ParseException {
        log.info("----begin create Caisse-----");

        Response<CaisseDto> response = new Response<CaisseDto>();
        List<Caisse>        items    = new ArrayList<Caisse>();

        for (CaisseDto dto : request.getDatas()) {
            // Definir les parametres obligatoires
            Map<String, java.lang.Object> fieldsToVerify = new HashMap<String, java.lang.Object>();

            fieldsToVerify.put("type", dto.getType());
            //fieldsToVerify.put("idDepense", dto.getIdDepense());
            fieldsToVerify.put("idUtilisateur", dto.getIdUtilisateur());
            if (!Validate.RequiredValue(fieldsToVerify).isGood()) {
                throw new InvalidEntityException(functionalError.FIELD_EMPTY(Validate.getValidate().getField(), locale));
            }

            // Verify if caisse to insert do not exist
            Caisse existingEntity = null;
            if (existingEntity != null) {
                throw new InternalErrorException(functionalError.DATA_EXIST("caisse id -> " + dto.getId(), locale));
            }

            // Verify if utilisateur exist
            Utilisateur existingUtilisateur = null;
            if (dto.getIdUtilisateur() != null && dto.getIdUtilisateur() > 0) {
                existingUtilisateur = utilisateurRepository.findOne(dto.getIdUtilisateur(), false);
                if (existingUtilisateur == null) {
                    throw new EntityNotFoundException(functionalError.DATA_NOT_EXIST("utilisateur idUtilisateur -> " + dto.getIdUtilisateur(), locale));
                }
            }
            ;
            Caisse entityToSave = null;
            entityToSave = CaisseTransformer.INSTANCE.toEntity(dto, existingUtilisateur, null);
            entityToSave.setCreatedAt(Utilities.getCurrentDate());
            entityToSave.setCreatedBy(request.getUser());
            entityToSave.setIsDeleted(false);
            items.add(entityToSave);
        }

        if (!items.isEmpty()) {
            List<Caisse> itemsSaved = null;
            // inserer les donnees en base de donnees
            itemsSaved = caisseRepository.saveAll((Iterable<Caisse>) items);
            if (itemsSaved == null) {
                throw new InternalErrorException(functionalError.SAVE_FAIL("caisse", locale));
            }
            List<CaisseDto> itemsDto = (Utilities.isTrue(request.getIsSimpleLoading())) ? CaisseTransformer.INSTANCE.toLiteDtos(itemsSaved) : CaisseTransformer.INSTANCE.toDtos(itemsSaved);

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

        log.info("----end create Caisse-----");
        return response;
    }

    /**
     * update Caisse by using CaisseDto as object.
     *
     * @param request
     * @return response
     */
    @Override
    public Response<CaisseDto> update(Request<CaisseDto> request, Locale locale) throws ParseException {
        log.info("----begin update Caisse-----");

        Response<CaisseDto> response = new Response<CaisseDto>();
        List<Caisse>        items    = new ArrayList<Caisse>();

        for (CaisseDto dto : request.getDatas()) {
            // Definir les parametres obligatoires
            Map<String, java.lang.Object> fieldsToVerify = new HashMap<String, java.lang.Object>();
            fieldsToVerify.put("id", dto.getId());
            if (!Validate.RequiredValue(fieldsToVerify).isGood()) {
                throw new InvalidEntityException(functionalError.FIELD_EMPTY(Validate.getValidate().getField(), locale));
            }

            // Verifier si la caisse existe
            Caisse entityToSave = null;
            entityToSave = caisseRepository.findOne(dto.getId(), false);
            if (entityToSave == null) {
                throw new EntityNotFoundException(functionalError.DATA_NOT_EXIST("caisse id -> " + dto.getId(), locale));
            }

            // Verify if utilisateur exist
            if (dto.getIdUtilisateur() != null && dto.getIdUtilisateur() > 0) {
                Utilisateur existingUtilisateur = utilisateurRepository.findOne(dto.getIdUtilisateur(), false);
                if (existingUtilisateur == null) {
                    throw new EntityNotFoundException(functionalError.DATA_NOT_EXIST("utilisateur idUtilisateur -> " + dto.getIdUtilisateur(), locale));
                }
                entityToSave.setUtilisateur(existingUtilisateur);
            }

            if (dto.getCreatedBy() != null && dto.getCreatedBy() > 0) {
                entityToSave.setCreatedBy(dto.getCreatedBy());
            }
            if (dto.getSolde() > 0) {
                entityToSave.setSolde(dto.getSolde());
            }
            if (dto.getMontantEntre() > 0) {
                entityToSave.setMontantEntre(dto.getMontantEntre());
            }
            if (dto.getMontantSortie() > 0) {
                entityToSave.setMontantSortie(dto.getMontantSortie());
            }

            if (Utilities.notBlank(dto.getType().toString())) {
                entityToSave.setType(dto.getType());
            }
            if (dto.getUpdatedBy() != null && dto.getUpdatedBy() > 0) {
                entityToSave.setUpdatedBy(dto.getUpdatedBy());
            }

            entityToSave.setUpdatedAt(Utilities.getCurrentDate());
            entityToSave.setUpdatedBy(request.getUser());
            items.add(entityToSave);
        }

        if (!items.isEmpty()) {
            List<Caisse> itemsSaved = null;
            // maj les donnees en base
            itemsSaved = caisseRepository.saveAll((Iterable<Caisse>) items);
            if (itemsSaved == null) {
                throw new InternalErrorException(functionalError.SAVE_FAIL("caisse", locale));
            }
            List<CaisseDto> itemsDto = (Utilities.isTrue(request.getIsSimpleLoading())) ? CaisseTransformer.INSTANCE.toLiteDtos(itemsSaved) : CaisseTransformer.INSTANCE.toDtos(itemsSaved);

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

        log.info("----end update Caisse-----");
        return response;
    }

    /**
     * delete Caisse by using CaisseDto as object.
     *
     * @param request
     * @return response
     */
    @Override
    public Response<CaisseDto> delete(Request<CaisseDto> request, Locale locale) {
        log.info("----begin delete Caisse-----");

        Response<CaisseDto> response = new Response<CaisseDto>();
        List<Caisse>        items    = new ArrayList<Caisse>();

        for (CaisseDto dto : request.getDatas()) {
            // Definir les parametres obligatoires
            Map<String, java.lang.Object> fieldsToVerify = new HashMap<String, java.lang.Object>();
            fieldsToVerify.put("id", dto.getId());
            if (!Validate.RequiredValue(fieldsToVerify).isGood()) {
                throw new InvalidEntityException(functionalError.FIELD_EMPTY(Validate.getValidate().getField(), locale));
            }

            // Verifier si la caisse existe
            Caisse existingEntity = null;
            existingEntity = caisseRepository.findOne(dto.getId(), false);
            if (existingEntity == null) {
                throw new EntityNotFoundException(functionalError.DATA_NOT_EXIST("caisse -> " + dto.getId(), locale));
            }

            // -----------------------------------------------------------------------
            // ----------- CHECK IF DATA IS USED
            // -----------------------------------------------------------------------


            existingEntity.setIsDeleted(true);
            items.add(existingEntity);
        }

        if (!items.isEmpty()) {
            // supprimer les donnees en base
            caisseRepository.saveAll((Iterable<Caisse>) items);

            Status status = new Status();
            status.setCode(StatusCode.SUCCESS);
            status.setMessage(StatusMessage.SUCCESS);

            response.setHasError(false);
            response.setHttpCode(HttpStatus.OK.value());
            response.setStatus(status);
        }

        log.info("----end delete Caisse-----");
        return response;
    }

    /**
     * get Caisse by using CaisseDto as object.
     *
     * @param request
     * @return response
     */
    @Override
    public Response<CaisseDto> getByCriteria(Request<CaisseDto> request, Locale locale) throws Exception {
        log.info("----begin get Caisse-----");

        Response<CaisseDto> response = new Response<CaisseDto>();
        List<Caisse>        items    = caisseRepository.getByCriteria(request, em, locale);

        if (items != null && !items.isEmpty()) {
            List<CaisseDto> itemsDto = (Utilities.isTrue(request.getIsSimpleLoading()))
                    ? CaisseTransformer.INSTANCE.toLiteDtos(items)
                    : CaisseTransformer.INSTANCE.toDtos(items);

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
            response.setCount(caisseRepository.count(request, em, locale));
            response.setHasError(false);
        } else {
            response.setStatus(functionalError.DATA_EMPTY("caisse", locale));
            response.setHasError(false);
            return response;
        }

        log.info("----end get Caisse-----");
        return response;
    }

    /**
     * get full CaisseDto by using Caisse as object.
     *
     * @param dto
     * @param size
     * @param isSimpleLoading
     * @param locale
     * @return
     * @throws Exception
     */
    private CaisseDto getFullInfos(CaisseDto dto, Integer size, Boolean isSimpleLoading, Locale locale) throws Exception {
        // put code here

        if (Utilities.isTrue(isSimpleLoading)) {
            return dto;
        }
        if (size > 1) {
            return dto;
        }

        return dto;
    }

    public Response<CaisseDto> custom(Request<CaisseDto> request, Locale locale) {
        log.info("----begin custom CaisseDto-----");
        Response<CaisseDto> response = new Response<CaisseDto>();

        response.setHasError(false);
        response.setCount(1L);
        response.setItems(Arrays.asList(new CaisseDto()));
        log.info("----end custom CaisseDto-----");
        return response;
    }

    public Response<CaisseDto> approvisionnement(Request<CaisseDto> request, Locale locale) throws ParseException {
        log.info("----begin approvisionnement Caisse-----");

        Response<CaisseDto> response = new Response<CaisseDto>();
        List<Caisse>        items    = new ArrayList<Caisse>();
        for (CaisseDto dto : request.getDatas()) {
            // Definir les parametres obligatoires
            Map<String, java.lang.Object> fieldsToVerify = new HashMap<String, java.lang.Object>();
            fieldsToVerify.put("montant", dto.getMontantEntre());
            fieldsToVerify.put("libelle", dto.getLibelle());
            if (!Validate.RequiredValue(fieldsToVerify).isGood()) {
                throw new InvalidEntityException(functionalError.FIELD_EMPTY(Validate.getValidate().getField(), locale));
            }

            // Verify if caisse to insert do not exist
            Caisse existingEntity = null;
            if (existingEntity != null) {
                throw new InternalErrorException(functionalError.DATA_EXIST("caisse id -> " + dto.getId(), locale));
            }

            // Verify if utilisateur exist
            Utilisateur existingUtilisateur = null;
            if (request.getUser() != null && request.getUser() > 0) {
                System.out.println(request.getUser());
                existingUtilisateur = utilisateurRepository.findOne(request.getUser(), false);
                if (existingUtilisateur == null) {
                    throw new EntityNotFoundException(functionalError.DATA_NOT_EXIST("utilisateur idUtilisateur -> " + request.getUser(), locale));
                }
            }


            Caisse entityToSave = null;
            entityToSave = CaisseTransformer.INSTANCE.toEntity(dto, existingUtilisateur, null);
            entityToSave.setCreatedAt(Utilities.getCurrentDate());
            entityToSave.setCreatedBy(request.getUser());
            entityToSave.setType(TypeCaisseEnum.ENTREE);
            entityToSave.setMontantEntre(dto.getMontantEntre());
            entityToSave.setMontantSortie(0.0);
            entityToSave.setSolde(Utilities.addMontant(getMontantDisponible(), dto.getMontantEntre()));
            entityToSave.setIsDeleted(false);
            items.add(entityToSave);
        }

        if (!items.isEmpty()) {
            List<Caisse> itemsSaved = null;
            // inserer les donnees en base de donnees
            itemsSaved = caisseRepository.saveAll((Iterable<Caisse>) items);
            if (itemsSaved == null) {
                throw new InternalErrorException(functionalError.SAVE_FAIL("caisse", locale));
            }
            List<CaisseDto> itemsDto = (Utilities.isTrue(request.getIsSimpleLoading())) ? CaisseTransformer.INSTANCE.toLiteDtos(itemsSaved) : CaisseTransformer.INSTANCE.toDtos(itemsSaved);

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

        log.info("----end approvisionnement Caisse-----");
        return response;
    }

    public Double getMontantDisponible() {
        Double montantEntre = caisseRepository.montantTotalEntre(false);
        if (montantEntre == null) montantEntre = 0.0;
        Double montantSortie = caisseRepository.montantTotalSortie(false);
        if (montantSortie == null) montantSortie = 0.0;
        return Utilities.subtractMontant(montantEntre, montantSortie);
    }

}
