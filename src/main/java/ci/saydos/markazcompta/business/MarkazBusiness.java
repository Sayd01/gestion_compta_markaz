                                            										
/*
 * Java business for entity table markaz 
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
import ci.saydos.markazcompta.dao.entity.Markaz;
import ci.saydos.markazcompta.dao.entity.*;
import ci.saydos.markazcompta.dao.repository.*;

/**
BUSINESS for table "markaz"
 * 
 * @author Geo
 *
 */
@Log
@Component
public class MarkazBusiness implements IBasicBusiness<Request<MarkazDto>, Response<MarkazDto>> {

	private Response<MarkazDto> response;
	@Autowired
	private MarkazRepository markazRepository;
	@Autowired
	private CaisseRepository caisseRepository;
	@Autowired
	private UtilisateurRepository utilisateurRepository;
	@Autowired
	private DirectionRepository directionRepository;
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

	public MarkazBusiness() {
		dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		dateTimeFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
	}
	
	/**
	 * create Markaz by using MarkazDto as object.
	 * 
	 * @param request
	 * @return response
	 * 
	 */
	@Override
	public Response<MarkazDto> create(Request<MarkazDto> request, Locale locale)  throws ParseException {
		log.info("----begin create Markaz-----");

		Response<MarkazDto> response = new Response<MarkazDto>();
		List<Markaz>        items    = new ArrayList<Markaz>();
			
		for (MarkazDto dto : request.getDatas()) {
			// Definir les parametres obligatoires
			Map<String, java.lang.Object> fieldsToVerify = new HashMap<String, java.lang.Object>();
			fieldsToVerify.put("code", dto.getCode());
			fieldsToVerify.put("intitule", dto.getIntitule());
			fieldsToVerify.put("adresse", dto.getAdresse());
			if (!Validate.RequiredValue(fieldsToVerify).isGood()) {
				throw new InvalidEntityException(functionalError.FIELD_EMPTY(Validate.getValidate().getField(), locale));
			}

			// Verify if markaz to insert do not exist
			Markaz existingEntity = null;
			if (existingEntity != null) {
				throw new InternalErrorException(functionalError.DATA_EXIST("markaz id -> " + dto.getId(), locale));
			}

			// verif unique code in db
			existingEntity = markazRepository.findByCode(dto.getCode(), false);
			if (existingEntity != null) {
				throw new InternalErrorException(functionalError.DATA_EXIST("markaz code -> " + dto.getCode(), locale));
			}
			// verif unique code in items to save
			if (items.stream().anyMatch(a -> a.getCode().equalsIgnoreCase(dto.getCode()))) {
				throw new InternalErrorException(functionalError.DATA_DUPLICATE(" code ", locale));
			}

			// verif unique intitule in db
			existingEntity = markazRepository.findByIntitule(dto.getIntitule(), false);
			if (existingEntity != null) {
				throw new InternalErrorException(functionalError.DATA_EXIST("markaz intitule -> " + dto.getIntitule(), locale));
			}
			// verif unique intitule in items to save
			if (items.stream().anyMatch(a -> a.getIntitule().equalsIgnoreCase(dto.getIntitule()))) {
				throw new InternalErrorException(functionalError.DATA_DUPLICATE(" intitule ", locale));
			}

				Markaz entityToSave = null;
			entityToSave = MarkazTransformer.INSTANCE.toEntity(dto);
			entityToSave.setCreatedAt(Utilities.getCurrentDate());
			entityToSave.setCreatedBy(request.getUser());
			entityToSave.setIsDeleted(false);
			items.add(entityToSave);
		}

		if (!items.isEmpty()) {
			List<Markaz> itemsSaved = null;
			// inserer les donnees en base de donnees
			itemsSaved = markazRepository.saveAll((Iterable<Markaz>) items);
			if (itemsSaved == null) {
				throw new InternalErrorException(functionalError.SAVE_FAIL("markaz", locale));
			}
			List<MarkazDto> itemsDto = (Utilities.isTrue(request.getIsSimpleLoading())) ? MarkazTransformer.INSTANCE.toLiteDtos(itemsSaved) : MarkazTransformer.INSTANCE.toDtos(itemsSaved);

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

		log.info("----end create Markaz-----");
		return response;
	}

	/**
	 * update Markaz by using MarkazDto as object.
	 * 
	 * @param request
	 * @return response
	 * 
	 */
	@Override
	public Response<MarkazDto> update(Request<MarkazDto> request, Locale locale)  throws ParseException {
		log.info("----begin update Markaz-----");

		Response<MarkazDto> response = new Response<MarkazDto>();
		List<Markaz>        items    = new ArrayList<Markaz>();
			
		for (MarkazDto dto : request.getDatas()) {
			// Definir les parametres obligatoires
			Map<String, java.lang.Object> fieldsToVerify = new HashMap<String, java.lang.Object>();
			fieldsToVerify.put("id", dto.getId());
			if (!Validate.RequiredValue(fieldsToVerify).isGood()) {
				throw new InvalidEntityException(functionalError.FIELD_EMPTY(Validate.getValidate().getField(), locale));
			}

			// Verifier si la markaz existe
			Markaz entityToSave = null;
			entityToSave = markazRepository.findOne(dto.getId(), false);
			if (entityToSave == null) {
				throw new EntityNotFoundException(functionalError.DATA_NOT_EXIST("markaz id -> " + dto.getId(), locale));
			}

			if (Utilities.notBlank(dto.getCode())) {
				entityToSave.setCode(dto.getCode());
			}
			if (Utilities.notBlank(dto.getIntitule())) {
				entityToSave.setIntitule(dto.getIntitule());
			}
			if (Utilities.notBlank(dto.getAdresse())) {
				entityToSave.setAdresse(dto.getAdresse());
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
			List<Markaz> itemsSaved = null;
			// maj les donnees en base
			itemsSaved = markazRepository.saveAll((Iterable<Markaz>) items);
			if (itemsSaved == null) {
				throw new InternalErrorException(functionalError.SAVE_FAIL("markaz", locale));
			}
			List<MarkazDto> itemsDto = (Utilities.isTrue(request.getIsSimpleLoading())) ? MarkazTransformer.INSTANCE.toLiteDtos(itemsSaved) : MarkazTransformer.INSTANCE.toDtos(itemsSaved);

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

		log.info("----end update Markaz-----");
		return response;
	}

	/**
	 * delete Markaz by using MarkazDto as object.
	 * 
	 * @param request
	 * @return response
	 * 
	 */
	@Override
	public Response<MarkazDto> delete(Request<MarkazDto> request, Locale locale)  {
		log.info("----begin delete Markaz-----");

		Response<MarkazDto> response = new Response<MarkazDto>();
		List<Markaz>        items    = new ArrayList<Markaz>();
			
		for (MarkazDto dto : request.getDatas()) {
			// Definir les parametres obligatoires
			Map<String, java.lang.Object> fieldsToVerify = new HashMap<String, java.lang.Object>();
			fieldsToVerify.put("id", dto.getId());
			if (!Validate.RequiredValue(fieldsToVerify).isGood()) {
				throw new InvalidEntityException(functionalError.FIELD_EMPTY(Validate.getValidate().getField(), locale));
			}

			// Verifier si la markaz existe
			Markaz existingEntity = null;
			existingEntity = markazRepository.findOne(dto.getId(), false);
			if (existingEntity == null) {
				throw new EntityNotFoundException(functionalError.DATA_NOT_EXIST("markaz -> " + dto.getId(), locale));
			}

			// -----------------------------------------------------------------------
			// ----------- CHECK IF DATA IS USED
			// -----------------------------------------------------------------------

			// caisse

			// utilisateur
			List<Utilisateur> listOfUtilisateur = utilisateurRepository.findByIdMarkaz(existingEntity.getId(), false);
			if (listOfUtilisateur != null && !listOfUtilisateur.isEmpty()){
				throw new InternalErrorException(functionalError.DATA_NOT_DELETABLE("(" + listOfUtilisateur.size() + ")", locale));
			}
			// direction
			List<Direction> listOfDirection = directionRepository.findByIdMarkaz(existingEntity.getId(), false);
			if (listOfDirection != null && !listOfDirection.isEmpty()){
				throw new InternalErrorException(functionalError.DATA_NOT_DELETABLE("(" + listOfDirection.size() + ")", locale));
			}


			existingEntity.setIsDeleted(true);
			items.add(existingEntity);
		}

		if (!items.isEmpty()) {
			// supprimer les donnees en base
			markazRepository.saveAll((Iterable<Markaz>) items);

			Status status = new Status();
			status.setCode(StatusCode.SUCCESS);
			status.setMessage(StatusMessage.SUCCESS);

			response.setHasError(false);
			response.setHttpCode(HttpStatus.OK.value());
			response.setStatus(status);
		}

		log.info("----end delete Markaz-----");
		return response;
	}

	/**
	 * get Markaz by using MarkazDto as object.
	 * 
	 * @param request
	 * @return response
	 * 
	 */
	@Override
	public Response<MarkazDto> getByCriteria(Request<MarkazDto> request, Locale locale)  throws Exception {
		log.info("----begin get Markaz-----");

		Response<MarkazDto> response = new Response<MarkazDto>();
		List<Markaz> items 			 = markazRepository.getByCriteria(request, em, locale);

		if (items != null && !items.isEmpty()) {
			List<MarkazDto> itemsDto = (Utilities.isTrue(request.getIsSimpleLoading())) ? MarkazTransformer.INSTANCE.toLiteDtos(items) : MarkazTransformer.INSTANCE.toDtos(items);

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
			response.setCount(markazRepository.count(request, em, locale));
			response.setHasError(false);
		} else {
			response.setStatus(functionalError.DATA_EMPTY("markaz", locale));
			response.setHasError(false);
			return response;
		}

		log.info("----end get Markaz-----");
		return response;
	}

	/**
	 * get full MarkazDto by using Markaz as object.
	 * 
	 * @param dto
	 * @param size
	 * @param isSimpleLoading
	 * @param locale
	 * @return
	 * @throws Exception
	 */
	private MarkazDto getFullInfos(MarkazDto dto, Integer size, Boolean isSimpleLoading, Locale locale) throws Exception {
		// put code here

		if (Utilities.isTrue(isSimpleLoading)) {
			return dto;
		}
		if (size > 1) {
			return dto;
		}

		return dto;
	}

	public Response<MarkazDto> custom(Request<MarkazDto> request, Locale locale) {
		log.info("----begin custom MarkazDto-----");
		Response<MarkazDto> response = new Response<MarkazDto>();
		
		response.setHasError(false);
		response.setCount(1L);
		response.setItems(Arrays.asList(new MarkazDto()));
		log.info("----end custom MarkazDto-----");
		return response;
	}
}
