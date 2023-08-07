                                                            														
/*
 * Java business for entity table caisse 
 * Created on 2023-08-06 ( Time 01:31:04 )
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
import ci.saydos.markazcompta.dao.entity.Caisse;
import ci.saydos.markazcompta.dao.entity.Depense;
import ci.saydos.markazcompta.dao.entity.Markaz;
import ci.saydos.markazcompta.dao.entity.Utilisateur;
import ci.saydos.markazcompta.dao.entity.*;
import ci.saydos.markazcompta.dao.repository.*;

/**
BUSINESS for table "caisse"
 * 
 * @author Geo
 *
 */
@Log
@Component
public class CaisseBusiness implements IBasicBusiness<Request<CaisseDto>, Response<CaisseDto>> {

	private Response<CaisseDto> response;
	@Autowired
	private CaisseRepository caisseRepository;
	@Autowired
	private DepenseRepository depenseRepository;
	@Autowired
	private MarkazRepository markazRepository;
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

	public CaisseBusiness() {
		dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		dateTimeFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
	}
	
	/**
	 * create Caisse by using CaisseDto as object.
	 * 
	 * @param request
	 * @return response
	 * 
	 */
	@Override
	public Response<CaisseDto> create(Request<CaisseDto> request, Locale locale)  throws ParseException {
		log.info("----begin create Caisse-----");

		Response<CaisseDto> response = new Response<CaisseDto>();
		List<Caisse>        items    = new ArrayList<Caisse>();
			
		for (CaisseDto dto : request.getDatas()) {
			// Definir les parametres obligatoires
			Map<String, java.lang.Object> fieldsToVerify = new HashMap<String, java.lang.Object>();
			fieldsToVerify.put("idMarkaz", dto.getIdMarkaz());
			fieldsToVerify.put("idUtilisateur", dto.getIdUtilisateur());
			fieldsToVerify.put("idDepense", dto.getIdDepense());
			fieldsToVerify.put("montant", dto.getMontant());
			fieldsToVerify.put("montantApprovisionnement", dto.getMontantApprovisionnement());
			fieldsToVerify.put("dateApprovisionnement", dto.getDateApprovisionnement());
			fieldsToVerify.put("type", dto.getType());
			if (!Validate.RequiredValue(fieldsToVerify).isGood()) {
				throw new InvalidEntityException(functionalError.FIELD_EMPTY(Validate.getValidate().getField(), locale));
			}

			// Verify if caisse to insert do not exist
			Caisse existingEntity = null;
			if (existingEntity != null) {
				throw new InternalErrorException(functionalError.DATA_EXIST("caisse id -> " + dto.getId(), locale));
			}

			// Verify if depense exist
			Depense existingDepense = null;
			if (dto.getIdDepense() != null && dto.getIdDepense() > 0){
				existingDepense = depenseRepository.findOne(dto.getIdDepense(), false);
				if (existingDepense == null) {
					throw new EntityNotFoundException(functionalError.DATA_NOT_EXIST("depense idDepense -> " + dto.getIdDepense(), locale));
				}
			}
			// Verify if markaz exist
			Markaz existingMarkaz = null;
			if (dto.getIdMarkaz() != null && dto.getIdMarkaz() > 0){
				existingMarkaz = markazRepository.findOne(dto.getIdMarkaz(), false);
				if (existingMarkaz == null) {
					throw new EntityNotFoundException(functionalError.DATA_NOT_EXIST("markaz idMarkaz -> " + dto.getIdMarkaz(), locale));
				}
			}
			// Verify if utilisateur exist
			Utilisateur existingUtilisateur = null;
			if (dto.getIdUtilisateur() != null && dto.getIdUtilisateur() > 0){
				existingUtilisateur = utilisateurRepository.findOne(dto.getIdUtilisateur(), false);
				if (existingUtilisateur == null) {
					throw new EntityNotFoundException(functionalError.DATA_NOT_EXIST("utilisateur idUtilisateur -> " + dto.getIdUtilisateur(), locale));
				}
			}
				Caisse entityToSave = null;
			entityToSave = CaisseTransformer.INSTANCE.toEntity(dto, existingDepense, existingMarkaz, existingUtilisateur);
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

		log.info("----end create Caisse-----");
		return response;
	}

	/**
	 * update Caisse by using CaisseDto as object.
	 * 
	 * @param request
	 * @return response
	 * 
	 */
	@Override
	public Response<CaisseDto> update(Request<CaisseDto> request, Locale locale)  throws ParseException {
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

			// Verify if depense exist
			if (dto.getIdDepense() != null && dto.getIdDepense() > 0){
				Depense existingDepense = depenseRepository.findOne(dto.getIdDepense(), false);
				if (existingDepense == null) {
					throw new EntityNotFoundException(functionalError.DATA_NOT_EXIST("depense idDepense -> " + dto.getIdDepense(), locale));
				}
				entityToSave.setDepense(existingDepense);
			}
			// Verify if markaz exist
			if (dto.getIdMarkaz() != null && dto.getIdMarkaz() > 0){
				Markaz existingMarkaz = markazRepository.findOne(dto.getIdMarkaz(), false);
				if (existingMarkaz == null) {
					throw new EntityNotFoundException(functionalError.DATA_NOT_EXIST("markaz idMarkaz -> " + dto.getIdMarkaz(), locale));
				}
				entityToSave.setMarkaz(existingMarkaz);
			}
			// Verify if utilisateur exist
			if (dto.getIdUtilisateur() != null && dto.getIdUtilisateur() > 0){
				Utilisateur existingUtilisateur = utilisateurRepository.findOne(dto.getIdUtilisateur(), false);
				if (existingUtilisateur == null) {
					throw new EntityNotFoundException(functionalError.DATA_NOT_EXIST("utilisateur idUtilisateur -> " + dto.getIdUtilisateur(), locale));
				}
				entityToSave.setUtilisateur(existingUtilisateur);
			}
			if (dto.getMontant() != null && dto.getMontant() > 0) {
				entityToSave.setMontant(dto.getMontant());
			}
			if (dto.getMontantApprovisionnement() != null && dto.getMontantApprovisionnement() > 0) {
				entityToSave.setMontantApprovisionnement(dto.getMontantApprovisionnement());
			}
			if (Utilities.notBlank(dto.getDateApprovisionnement())) {
				entityToSave.setDateApprovisionnement(dateFormat.parse(dto.getDateApprovisionnement()));
			}
			if (Utilities.notBlank(dto.getType().toString())) {
				entityToSave.setType(dto.getType());
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
			List<Caisse> itemsSaved = null;
			// maj les donnees en base
			itemsSaved = caisseRepository.saveAll((Iterable<Caisse>) items);
			if (itemsSaved == null) {
				throw new InternalErrorException(functionalError.SAVE_FAIL("caisse", locale));
			}
			List<CaisseDto> itemsDto = (Utilities.isTrue(request.getIsSimpleLoading())) ? CaisseTransformer.INSTANCE.toLiteDtos(itemsSaved) : CaisseTransformer.INSTANCE.toDtos(itemsSaved);

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

		log.info("----end update Caisse-----");
		return response;
	}

	/**
	 * delete Caisse by using CaisseDto as object.
	 * 
	 * @param request
	 * @return response
	 * 
	 */
	@Override
	public Response<CaisseDto> delete(Request<CaisseDto> request, Locale locale)  {
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
	 * 
	 */
	@Override
	public Response<CaisseDto> getByCriteria(Request<CaisseDto> request, Locale locale)  throws Exception {
		log.info("----begin get Caisse-----");

		Response<CaisseDto> response = new Response<CaisseDto>();
		List<Caisse> items 			 = caisseRepository.getByCriteria(request, em, locale);

		if (items != null && !items.isEmpty()) {
			List<CaisseDto> itemsDto = (Utilities.isTrue(request.getIsSimpleLoading())) ? CaisseTransformer.INSTANCE.toLiteDtos(items) : CaisseTransformer.INSTANCE.toDtos(items);

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
}
