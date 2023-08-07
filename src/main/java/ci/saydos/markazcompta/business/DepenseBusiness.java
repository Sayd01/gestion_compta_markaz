                                                											
/*
 * Java business for entity table depense 
 * Created on 2023-08-06 ( Time 01:31:05 )
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
import ci.saydos.markazcompta.dao.entity.Depense;
import ci.saydos.markazcompta.dao.entity.Demande;
import ci.saydos.markazcompta.dao.entity.Utilisateur;
import ci.saydos.markazcompta.dao.entity.*;
import ci.saydos.markazcompta.dao.repository.*;

/**
BUSINESS for table "depense"
 * 
 * @author Geo
 *
 */
@Log
@Component
public class DepenseBusiness implements IBasicBusiness<Request<DepenseDto>, Response<DepenseDto>> {

	private Response<DepenseDto> response;
	@Autowired
	private DepenseRepository depenseRepository;
	@Autowired
	private DemandeRepository demandeRepository;
	@Autowired
	private CaisseRepository caisseRepository;
	@Autowired
	private UtilisateurRepository utilisateurRepository;
	@Autowired
	private FunctionalError functionalError;
	@Autowired
	private TechnicalError technicalError;
	@Autowired
	private ExceptionUtils exceptionUtils;
	@PersistenceContext
	private EntityManager em;

	private SimpleDateFormat dateFormat;
	private SimpleDateFormat dateTimeFormat;

	public DepenseBusiness() {
		dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		dateTimeFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
	}
	
	/**
	 * create Depense by using DepenseDto as object.
	 * 
	 * @param request
	 * @return response
	 * 
	 */
	@Override
	public Response<DepenseDto> create(Request<DepenseDto> request, Locale locale)  throws ParseException {
		log.info("----begin create Depense-----");

		Response<DepenseDto> response = new Response<DepenseDto>();
		List<Depense>        items    = new ArrayList<Depense>();
			
		for (DepenseDto dto : request.getDatas()) {
			// Definir les parametres obligatoires
			Map<String, java.lang.Object> fieldsToVerify = new HashMap<String, java.lang.Object>();
			fieldsToVerify.put("code", dto.getCode());
			fieldsToVerify.put("idChargeFixe", dto.getIdChargeFixe());
			fieldsToVerify.put("idDemande", dto.getIdDemande());
			fieldsToVerify.put("montant", dto.getMontant());
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
			if (dto.getIdDemande() != null && dto.getIdDemande() > 0){
				existingDemande = demandeRepository.findOne(dto.getIdDemande(), false);
				if (existingDemande == null) {
					throw new EntityNotFoundException(functionalError.DATA_NOT_EXIST("demande idDemande -> " + dto.getIdDemande(), locale));
				}
			}
			// Verify if utilisateur exist
			Utilisateur existingUtilisateur = null;
			if (dto.getIdChargeFixe() != null && dto.getIdChargeFixe() > 0){
				existingUtilisateur = utilisateurRepository.findOne(dto.getIdChargeFixe(), false);
				if (existingUtilisateur == null) {
					throw new EntityNotFoundException(functionalError.DATA_NOT_EXIST("utilisateur idChargeFixe -> " + dto.getIdChargeFixe(), locale));
				}
			}
				Depense entityToSave = null;
			entityToSave = DepenseTransformer.INSTANCE.toEntity(dto, existingDemande, existingUtilisateur);
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

			final int size = itemsSaved.size();
			List<String>  listOfError      = Collections.synchronizedList(new ArrayList<String>());
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
	 * 
	 */
	@Override
	public Response<DepenseDto> update(Request<DepenseDto> request, Locale locale)  throws ParseException {
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
			if (dto.getIdDemande() != null && dto.getIdDemande() > 0){
				Demande existingDemande = demandeRepository.findOne(dto.getIdDemande(), false);
				if (existingDemande == null) {
					throw new EntityNotFoundException(functionalError.DATA_NOT_EXIST("demande idDemande -> " + dto.getIdDemande(), locale));
				}
				entityToSave.setDemande(existingDemande);
			}
			// Verify if utilisateur exist
			if (dto.getIdChargeFixe() != null && dto.getIdChargeFixe() > 0){
				Utilisateur existingUtilisateur = utilisateurRepository.findOne(dto.getIdChargeFixe(), false);
				if (existingUtilisateur == null) {
					throw new EntityNotFoundException(functionalError.DATA_NOT_EXIST("utilisateur idChargeFixe -> " + dto.getIdChargeFixe(), locale));
				}
				entityToSave.setUtilisateur(existingUtilisateur);
			}
			if (Utilities.notBlank(dto.getCode())) {
				entityToSave.setCode(dto.getCode());
			}
			if (dto.getMontant() != null && dto.getMontant() > 0) {
				entityToSave.setMontant(dto.getMontant());
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
			List<Depense> itemsSaved = null;
			// maj les donnees en base
			itemsSaved = depenseRepository.saveAll((Iterable<Depense>) items);
			if (itemsSaved == null) {
				throw new InternalErrorException(functionalError.SAVE_FAIL("depense", locale));
			}
			List<DepenseDto> itemsDto = (Utilities.isTrue(request.getIsSimpleLoading())) ? DepenseTransformer.INSTANCE.toLiteDtos(itemsSaved) : DepenseTransformer.INSTANCE.toDtos(itemsSaved);

			final int size = itemsSaved.size();
			List<String>  listOfError      = Collections.synchronizedList(new ArrayList<String>());
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
	 * 
	 */
	@Override
	public Response<DepenseDto> delete(Request<DepenseDto> request, Locale locale)  {
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
			if (listOfCaisse != null && !listOfCaisse.isEmpty()){
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
	 * 
	 */
	@Override
	public Response<DepenseDto> getByCriteria(Request<DepenseDto> request, Locale locale)  throws Exception {
		log.info("----begin get Depense-----");

		Response<DepenseDto> response = new Response<DepenseDto>();
		List<Depense> items 			 = depenseRepository.getByCriteria(request, em, locale);

		if (items != null && !items.isEmpty()) {
			List<DepenseDto> itemsDto = (Utilities.isTrue(request.getIsSimpleLoading())) ? DepenseTransformer.INSTANCE.toLiteDtos(items) : DepenseTransformer.INSTANCE.toDtos(items);

			final int size = items.size();
			List<String>  listOfError      = Collections.synchronizedList(new ArrayList<String>());
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
}
