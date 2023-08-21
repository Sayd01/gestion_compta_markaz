                                                											
/*
 * Java business for entity table depense
 * Created on 2023-08-09 ( Time 18:01:30 )
 * Generator tool : Telosys Tools Generator ( version 3.3.0 )
 * Copyright 2018 Geo. All Rights Reserved.
 */

package ci.saydos.markazcompta.business;

import ci.saydos.markazcompta.utils.enums.StatutDemandeEnum;
import ci.saydos.markazcompta.utils.enums.TypeCaisseEnum;
import ci.saydos.markazcompta.utils.enums.TypeDepenseEnum;
import lombok.RequiredArgsConstructor;
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
import ci.saydos.markazcompta.dao.entity.Depense;
import ci.saydos.markazcompta.dao.entity.Demande;
import ci.saydos.markazcompta.dao.entity.ChargeFixe;
import ci.saydos.markazcompta.dao.entity.*;
import ci.saydos.markazcompta.dao.repository.*;

/**
 * BUSINESS for table "depense"
 *
 * @author Geo
 */
@Log
@Component
@RequiredArgsConstructor
public class DepenseBusiness implements IBasicBusiness<Request<DepenseDto>, Response<DepenseDto>> {
    private final UtilisateurRepository utilisateurRepository;


    private final DepenseRepository           depenseRepository;
    private final DemandeRepository           demandeRepository;
    private final ChargeFixeRepository        chargeFixeRepository;
    private final CaisseRepository            caisseRepository;
    private final FunctionalError             functionalError;
    private final DemandeHistoriqueRepository demandeHistoriqueRepository;
    private final DemandeHistoriqueBusiness   demandeHistoriqueBusiness;

    @PersistenceContext
    private       EntityManager    em;
    private final SimpleDateFormat dateFormat     = new SimpleDateFormat("dd/MM/yyyy");
    private final SimpleDateFormat dateTimeFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");


    /**
     * create Depense by using DepenseDto as object.
     *
     * @param request
     * @return response
     */
    @Override
    public Response<DepenseDto> create(Request<DepenseDto> request, Locale locale) throws ParseException {
        log.info("----begin create Depense-----");

        Response<DepenseDto> response = new Response<DepenseDto>();
        List<Depense>        items    = new ArrayList<Depense>();

        for (DepenseDto dto : request.getDatas()) {
            // Definir les parametres obligatoires
            Map<String, java.lang.Object> fieldsToVerify = new HashMap<String, java.lang.Object>();
            fieldsToVerify.put("code", dto.getCode());
            fieldsToVerify.put("montant", dto.getMontant());
            fieldsToVerify.put("idDemande", dto.getIdDemande());
            fieldsToVerify.put("idChargeFixe", dto.getIdChargeFixe());
            if (!Validate.RequiredValue(fieldsToVerify).isGood()) {
                throw new InvalidEntityException(functionalError.FIELD_EMPTY(Validate.getValidate().getField(), locale));
            }

            // Verify if depense to insert do not exist
            Depense existingEntity = null;
            if (existingEntity != null) {
                throw new InternalErrorException(functionalError.DATA_EXIST("depense id -> " + dto.getId(), locale));
            }

            // verif unique code in db
            existingEntity = depenseRepository.findByCode(dto.getCode(), false);
            if (existingEntity != null) {
                throw new InternalErrorException(functionalError.DATA_EXIST("depense code -> " + dto.getCode(), locale));
            }
            // verif unique code in items to save
            if (items.stream().anyMatch(a -> a.getCode().equalsIgnoreCase(dto.getCode()))) {
                throw new InternalErrorException(functionalError.DATA_DUPLICATE(" code ", locale));
            }

            // Verify if demande exist
            Demande existingDemande = null;
            if (dto.getIdDemande() != null && dto.getIdDemande() > 0) {
                existingDemande = demandeRepository.findOne(dto.getIdDemande(), false);
                if (existingDemande == null) {
                    throw new EntityNotFoundException(functionalError.DATA_NOT_EXIST("demande idDemande -> " + dto.getIdDemande(), locale));
                }
            }
            // Verify if chargeFixe exist
            ChargeFixe existingChargeFixe = null;
            if (dto.getIdChargeFixe() != null && dto.getIdChargeFixe() > 0) {
                existingChargeFixe = chargeFixeRepository.findOne(dto.getIdChargeFixe(), false);
                if (existingChargeFixe == null) {
                    throw new EntityNotFoundException(functionalError.DATA_NOT_EXIST("chargeFixe idChargeFixe -> " + dto.getIdChargeFixe(), locale));
                }
            }
            Depense entityToSave = null;
            entityToSave = DepenseTransformer.INSTANCE.toEntity(dto, existingDemande, existingChargeFixe);
            entityToSave.setCreatedAt(Utilities.getCurrentDate());
            entityToSave.setCreatedBy(request.getUser());
            entityToSave.setIsDeleted(false);
            items.add(entityToSave);
        }

        if (!items.isEmpty()) {
            List<Depense> itemsSaved = null;
            // inserer les donnees en base de donnees
            itemsSaved = depenseRepository.saveAll((Iterable<Depense>) items);
            if (itemsSaved == null) {
                throw new InternalErrorException(functionalError.SAVE_FAIL("depense", locale));
            }
            List<DepenseDto> itemsDto = (Utilities.isTrue(request.getIsSimpleLoading())) ? DepenseTransformer.INSTANCE.toLiteDtos(itemsSaved) : DepenseTransformer.INSTANCE.toDtos(itemsSaved);

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

        log.info("----end create Depense-----");
        return response;
    }

    /**
     * update Depense by using DepenseDto as object.
     *
     * @param request
     * @return response
     */
    @Override
    public Response<DepenseDto> update(Request<DepenseDto> request, Locale locale) throws ParseException {
        log.info("----begin update Depense-----");

        Response<DepenseDto> response = new Response<DepenseDto>();
        List<Depense>        items    = new ArrayList<Depense>();

        for (DepenseDto dto : request.getDatas()) {
            // Definir les parametres obligatoires
            Map<String, java.lang.Object> fieldsToVerify = new HashMap<String, java.lang.Object>();
            fieldsToVerify.put("id", dto.getId());
            if (!Validate.RequiredValue(fieldsToVerify).isGood()) {
                throw new InvalidEntityException(functionalError.FIELD_EMPTY(Validate.getValidate().getField(), locale));
            }

            // Verifier si la depense existe
            Depense entityToSave = null;
            entityToSave = depenseRepository.findOne(dto.getId(), false);
            if (entityToSave == null) {
                throw new EntityNotFoundException(functionalError.DATA_NOT_EXIST("depense id -> " + dto.getId(), locale));
            }

            // Verify if demande exist
            if (dto.getIdDemande() != null && dto.getIdDemande() > 0) {
                Demande existingDemande = demandeRepository.findOne(dto.getIdDemande(), false);
                if (existingDemande == null) {
                    throw new EntityNotFoundException(functionalError.DATA_NOT_EXIST("demande idDemande -> " + dto.getIdDemande(), locale));
                }
                entityToSave.setDemande(existingDemande);
            }
            // Verify if chargeFixe exist
            if (dto.getIdChargeFixe() != null && dto.getIdChargeFixe() > 0) {
                ChargeFixe existingChargeFixe = chargeFixeRepository.findOne(dto.getIdChargeFixe(), false);
                if (existingChargeFixe == null) {
                    throw new EntityNotFoundException(functionalError.DATA_NOT_EXIST("chargeFixe idChargeFixe -> " + dto.getIdChargeFixe(), locale));
                }
                entityToSave.setChargeFixe(existingChargeFixe);
            }
            if (Utilities.notBlank(dto.getCode())) {
                entityToSave.setCode(dto.getCode());
            }
            if (dto.getCreatedBy() != null && dto.getCreatedBy() > 0) {
                entityToSave.setCreatedBy(dto.getCreatedBy());
            }
            if (dto.getMontant() != null && dto.getMontant() > 0) {
                entityToSave.setMontant(dto.getMontant());
            }
            if (dto.getUpdatedBy() != null && dto.getUpdatedBy() > 0) {
                entityToSave.setUpdatedBy(dto.getUpdatedBy());
            }
            entityToSave.setUpdatedAt(Utilities.getCurrentDate());
            entityToSave.setUpdatedBy(request.getUser());
            items.add(entityToSave);
        }

        if (!items.isEmpty()) {
            List<Depense> itemsSaved = null;
            // maj les donnees en base
            itemsSaved = depenseRepository.saveAll((Iterable<Depense>) items);
            if (itemsSaved == null) {
                throw new InternalErrorException(functionalError.SAVE_FAIL("depense", locale));
            }
            List<DepenseDto> itemsDto = (Utilities.isTrue(request.getIsSimpleLoading())) ? DepenseTransformer.INSTANCE.toLiteDtos(itemsSaved) : DepenseTransformer.INSTANCE.toDtos(itemsSaved);

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

        log.info("----end update Depense-----");
        return response;
    }

    /**
     * delete Depense by using DepenseDto as object.
     *
     * @param request
     * @return response
     */
    @Override
    public Response<DepenseDto> delete(Request<DepenseDto> request, Locale locale) {
        log.info("----begin delete Depense-----");

        Response<DepenseDto> response = new Response<DepenseDto>();
        List<Depense>        items    = new ArrayList<Depense>();

        for (DepenseDto dto : request.getDatas()) {
            // Definir les parametres obligatoires
            Map<String, java.lang.Object> fieldsToVerify = new HashMap<String, java.lang.Object>();
            fieldsToVerify.put("id", dto.getId());
            if (!Validate.RequiredValue(fieldsToVerify).isGood()) {
                throw new InvalidEntityException(functionalError.FIELD_EMPTY(Validate.getValidate().getField(), locale));
            }

            // Verifier si la depense existe
            Depense existingEntity = null;
            existingEntity = depenseRepository.findOne(dto.getId(), false);
            if (existingEntity == null) {
                throw new EntityNotFoundException(functionalError.DATA_NOT_EXIST("depense -> " + dto.getId(), locale));
            }

            // -----------------------------------------------------------------------
            // ----------- CHECK IF DATA IS USED
            // -----------------------------------------------------------------------

            // caisse
            List<Caisse> listOfCaisse = caisseRepository.findByIdDepense(existingEntity.getId(), false);
            if (listOfCaisse != null && !listOfCaisse.isEmpty()) {
                throw new InternalErrorException(functionalError.DATA_NOT_DELETABLE("(" + listOfCaisse.size() + ")", locale));
            }


            existingEntity.setIsDeleted(true);
            items.add(existingEntity);
        }

        if (!items.isEmpty()) {
            // supprimer les donnees en base
            depenseRepository.saveAll((Iterable<Depense>) items);

            Status status = new Status();
            status.setCode(StatusCode.SUCCESS);
            status.setMessage(StatusMessage.SUCCESS);

            response.setHasError(false);
            response.setHttpCode(HttpStatus.OK.value());
            response.setStatus(status);
        }

        log.info("----end delete Depense-----");
        return response;
    }

    /**
     * get Depense by using DepenseDto as object.
     *
     * @param request
     * @return response
     */
    @Override
    public Response<DepenseDto> getByCriteria(Request<DepenseDto> request, Locale locale) throws Exception {
        log.info("----begin get Depense-----");

        Response<DepenseDto> response = new Response<DepenseDto>();
        List<Depense>        items    = depenseRepository.getByCriteria(request, em, locale);

        if (items != null && !items.isEmpty()) {
            List<DepenseDto> itemsDto = (Utilities.isTrue(request.getIsSimpleLoading())) ? DepenseTransformer.INSTANCE.toLiteDtos(items) : DepenseTransformer.INSTANCE.toDtos(items);

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
            response.setCount(depenseRepository.count(request, em, locale));
            response.setHasError(false);
        } else {
            response.setStatus(functionalError.DATA_EMPTY("depense", locale));
            response.setHasError(false);
            return response;
        }

        log.info("----end get Depense-----");
        return response;
    }

    /**
     * get full DepenseDto by using Depense as object.
     *
     * @param dto
     * @param size
     * @param isSimpleLoading
     * @param locale
     * @return
     * @throws Exception
     */
    private DepenseDto getFullInfos(DepenseDto dto, Integer size, Boolean isSimpleLoading, Locale locale) throws Exception {
        // put code here

        if (Utilities.isTrue(isSimpleLoading)) {
            return dto;
        }
        if (size > 1) {
            return dto;
        }

        return dto;
    }

    public Response<DepenseDto> custom(Request<DepenseDto> request, Locale locale) {
        log.info("----begin custom DepenseDto-----");
        Response<DepenseDto> response = new Response<DepenseDto>();

        response.setHasError(false);
        response.setCount(1L);
        response.setItems(Arrays.asList(new DepenseDto()));
        log.info("----end custom DepenseDto-----");
        return response;
    }

    public Response<DepenseDto> fixe(Request<DepenseDto> request, Locale locale) throws ParseException {
        log.info("----begin fixe Depense-----");

        Response<DepenseDto> response    = new Response<>();
        List<Depense>        items       = new ArrayList<>();
        List<Caisse>         itemsCaisse = new ArrayList<>();

        for (DepenseDto dto : request.getDatas()) {
            // Definir les parametres obligatoires
            Map<String, java.lang.Object> fieldsToVerify = new HashMap<String, java.lang.Object>();
            fieldsToVerify.put("montant", dto.getMontant());
            fieldsToVerify.put("idChargeFixe", dto.getIdChargeFixe());
            if (!Validate.RequiredValue(fieldsToVerify).isGood()) {
                throw new InvalidEntityException(functionalError.FIELD_EMPTY(Validate.getValidate().getField(), locale));
            }

            // Verify if chargeFixe exist
            ChargeFixe existingChargeFixe = null;
            if (dto.getIdChargeFixe() != null && dto.getIdChargeFixe() > 0) {
                existingChargeFixe = chargeFixeRepository.findOne(dto.getIdChargeFixe(), false);
                if (existingChargeFixe == null) {
                    throw new EntityNotFoundException(functionalError.DATA_NOT_EXIST("chargeFixe idChargeFixe -> " + dto.getIdChargeFixe(), locale));
                }
            }

            Utilisateur utilisateur = utilisateurRepository.findOne(request.getUser(), false);
            ;
            if (utilisateur == null) {
                throw new EntityNotFoundException(functionalError.DATA_NOT_EXIST("Utilisateur est null -> ", locale));
            }


            Depense entityToSave = null;
            entityToSave = DepenseTransformer.INSTANCE.toEntity(dto, null, existingChargeFixe);
            entityToSave.setCode("DEP" + Utilities.generateCode());
            entityToSave.setCreatedAt(Utilities.getCurrentDate());
            entityToSave.setCreatedBy(request.getUser());
            entityToSave.setTypeDepense(TypeDepenseEnum.FIXE);
            entityToSave.setIsDeleted(false);
            items.add(entityToSave);

            Double montantActuel = caisseRepository.montantDisponible();
            if (montantActuel == null || montantActuel <= dto.getMontant()) {
                throw new InternalErrorException(functionalError.DISALLOWED_OPERATION("le montant disponible dans la caisse est inssufisant", locale));
            }

            Caisse caisse = new Caisse();
            caisse.setDepense(entityToSave);
            caisse.setMontantSortie(dto.getMontant());
            caisse.setMontantDisponible(Utilities.subtractMontant(montantActuel, dto.getMontant()));
            caisse.setIsDeleted(false);
            caisse.setType(TypeCaisseEnum.SORTIE);
            caisse.setCreatedAt(entityToSave.getCreatedAt());
            caisse.setCreatedBy(utilisateur.getId());
            caisse.setUtilisateur(utilisateur);
            caisse.setDateMontantActuel(Utilities.getCurrentDate());
            itemsCaisse.add(caisse);

            Calendar calendar = Calendar.getInstance();
            calendar.setTime(entityToSave.getCreatedAt());
            int mois  = calendar.get(Calendar.MONTH) + 1;
            int annee = calendar.get(Calendar.YEAR);

            Depense existingDepense = depenseRepository.findByDateAndIdChargeAndMontant(
                    mois, annee, dto.getIdChargeFixe(), dto.getMontant(), false);
            if (existingDepense != null) {
                throw new InternalErrorException(functionalError.DATA_EXIST("Depense id -> " + existingDepense.getId(), locale));
            }
        }

        if (!items.isEmpty()) {
            List<Depense> itemsSaved = null;
            // inserer les donnees en base de donnees
            itemsSaved = depenseRepository.saveAll((Iterable<Depense>) items);
            if (itemsSaved == null) {
                throw new InternalErrorException(functionalError.SAVE_FAIL("depense", locale));
            }
            List<DepenseDto> itemsDto = (Utilities.isTrue(request.getIsSimpleLoading())) ? DepenseTransformer.INSTANCE.toLiteDtos(itemsSaved) : DepenseTransformer.INSTANCE.toDtos(itemsSaved);

            List<Caisse> itemsCaisseSaved = null;
            itemsCaisseSaved = caisseRepository.saveAll(itemsCaisse);
            if (itemsCaisseSaved == null) {
                throw new InternalErrorException(functionalError.SAVE_FAIL("caisse", locale));
            }
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

        log.info("----end fixe Depense-----");
        return response;
    }

    public Response<DepenseDto> varible(Request<DepenseDto> request, Locale locale) throws ParseException {
        log.info("----begin fixe Depense-----");

        Response<DepenseDto>    response               = new Response<>();
        List<Depense>           items                  = new ArrayList<>();
        List<Caisse>            itemsCaisse            = new ArrayList<>();
        List<Demande>           itemsDemande           = new ArrayList<>();


        for (DepenseDto dto : request.getDatas()) {
            // Definir les parametres obligatoires
            Map<String, java.lang.Object> fieldsToVerify = new HashMap<String, java.lang.Object>();
            fieldsToVerify.put("idDemande", dto.getIdDemande());
            if (!Validate.RequiredValue(fieldsToVerify).isGood()) {
                throw new InvalidEntityException(functionalError.FIELD_EMPTY(Validate.getValidate().getField(), locale));
            }

            // Verify if chargeFixe exist
            Demande existingDemande = null;
            if (dto.getIdDemande() != null && dto.getIdDemande() > 0) {
                existingDemande = demandeRepository.findByIdAndStatut(dto.getIdDemande(), StatutDemandeEnum.VALIDE, false);
                if (existingDemande == null) {
                    throw new EntityNotFoundException(functionalError.DATA_NOT_EXIST("demande idDemande -> " + dto.getIdDemande(), locale));
                }
            }

            Utilisateur utilisateur = utilisateurRepository.findOne(request.getUser(), false);
            if (utilisateur == null) {
                throw new EntityNotFoundException(functionalError.DATA_NOT_EXIST("Utilisateur est null -> ", locale));
            }

            existingDemande.setDateFin(Utilities.getCurrentDate());
            existingDemande.setStatut(StatutDemandeEnum.TERMINE);
            existingDemande.setUtilisateur(utilisateur);
            existingDemande.setUpdatedBy(utilisateur.getId());
            existingDemande.setUpdatedAt(Utilities.getCurrentDate());
            itemsDemande.add(existingDemande);

            Depense entityToSave = null;
            entityToSave = DepenseTransformer.INSTANCE.toEntity(dto, existingDemande, null);
            entityToSave.setCode("DEP" + Utilities.generateCode());
            entityToSave.setCreatedAt(Utilities.getCurrentDate());
            entityToSave.setCreatedBy(request.getUser());
            entityToSave.setTypeDepense(TypeDepenseEnum.DEMANDE);
            entityToSave.setIsDeleted(false);
            entityToSave.setMontant(existingDemande.getMontant());
            items.add(entityToSave);

            Double montantActuel = caisseRepository.montantDisponible();
            if (montantActuel == null || montantActuel <= existingDemande.getMontant()) {
                throw new InternalErrorException(functionalError.DISALLOWED_OPERATION("le montant disponible dans la caisse est inssufisant", locale));
            }

            DemandeHistoriqueDto demandeHistoriqueDto = new DemandeHistoriqueDto();
            demandeHistoriqueDto.setIdDemande(dto.getIdDemande());
            demandeHistoriqueDto.setStatut(existingDemande.getStatut());
            demandeHistoriqueDto.setCreatedBy(request.getUser());
            demandeHistoriqueDto.setIdUtilisateur(request.getUser());

            Request<DemandeHistoriqueDto> requestHistorique = new Request<>();
            requestHistorique.setUser(request.getUser());
            requestHistorique.setDatas(Arrays.asList(demandeHistoriqueDto));
            demandeHistoriqueBusiness.create(requestHistorique, locale);




            Caisse caisse = new Caisse();
            caisse.setDepense(entityToSave);
            caisse.setMontantSortie(existingDemande.getMontant());
            caisse.setMontantDisponible(Utilities.subtractMontant(montantActuel, existingDemande.getMontant()));
            System.out.println("montantActuel : " + montantActuel);
            System.out.println("montantDemande : " + existingDemande.getMontant());
            System.out.println("montant Actuel actualise: "+ Utilities.subtractMontant(montantActuel,existingDemande.getMontant()));
            caisse.setIsDeleted(false);
            caisse.setType(TypeCaisseEnum.SORTIE);
            caisse.setCreatedAt(entityToSave.getCreatedAt());
            caisse.setCreatedBy(utilisateur.getId());
            caisse.setUtilisateur(utilisateur);
            caisse.setDateMontantActuel(Utilities.getCurrentDate());
            itemsCaisse.add(caisse);

            Depense existingDepense = depenseRepository.findByIdDemandeAndType(dto.getIdDemande(), TypeDepenseEnum.DEMANDE, false);
            if (existingDepense != null) {
                throw new InternalErrorException(functionalError.DATA_EXIST("Depense id -> " + existingDepense.getId(), locale));
            }
        }

        if (!items.isEmpty()) {
            List<Depense> itemsSaved = null;
            // inserer les donnees en base de donnees
            itemsSaved = depenseRepository.saveAll((Iterable<Depense>) items);
            if (itemsSaved == null) {
                throw new InternalErrorException(functionalError.SAVE_FAIL("depense", locale));
            }
            List<DepenseDto> itemsDto = (Utilities.isTrue(request.getIsSimpleLoading())) ? DepenseTransformer.INSTANCE.toLiteDtos(itemsSaved) : DepenseTransformer.INSTANCE.toDtos(itemsSaved);

            List<Caisse> itemsCaisseSaved = null;
            itemsCaisseSaved = caisseRepository.saveAll(itemsCaisse);
            if (itemsCaisseSaved == null) {
                throw new InternalErrorException(functionalError.SAVE_FAIL("caisse", locale));
            }
            List<Demande> itemsDemandeSaved = null;
            itemsDemandeSaved = demandeRepository.saveAll(itemsDemande);
            if (itemsDemandeSaved == null) {
                throw new InternalErrorException(functionalError.SAVE_FAIL("Demande", locale));
            }
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

        log.info("----end fixe Depense-----");
        return response;
    }


}
