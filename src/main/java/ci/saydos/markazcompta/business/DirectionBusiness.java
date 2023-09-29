                                            										
/*
 * Java business for entity table direction 
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
import ci.saydos.markazcompta.dao.entity.Direction;
import ci.saydos.markazcompta.dao.entity.Markaz;
import ci.saydos.markazcompta.dao.entity.*;
import ci.saydos.markazcompta.dao.repository.*;

/**
BUSINESS for table "direction"
 * 
 * @author Geo
 *
 */
@Log
@Component
public class DirectionBusiness implements IBasicBusiness<Request<DirectionDto>, Response<DirectionDto>> {

	private Response<DirectionDto> response;
	@Autowired
	private DirectionRepository directionRepository;
	@Autowired
	private MarkazRepository markazRepository;
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

	public DirectionBusiness() {
		dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		dateTimeFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
	}
	
	/**
	 * create Direction by using DirectionDto as object.
	 * 
	 * @param request
	 * @return response
	 * 
	 */
	@Override
	public Response<DirectionDto> create(Request<DirectionDto> request, Locale locale)  throws ParseException {
		log.info("----begin create Direction-----");

		Response<DirectionDto> response = new Response<DirectionDto>();
		List<Direction>        items    = new ArrayList<Direction>();
			
		for (DirectionDto dto : request.getDatas()) {
			// Definir les parametres obligatoires
			Map<String, java.lang.Object> fieldsToVerify = new HashMap<String, java.lang.Object>();
			fieldsToVerify.put("idMarkaz", dto.getIdMarkaz());
			fieldsToVerify.put("intitule", dto.getIntitule());
			if (!Validate.RequiredValue(fieldsToVerify).isGood()) {
				throw new InvalidEntityException(functionalError.FIELD_EMPTY(Validate.getValidate().getField(), locale));
			}

			// Verify if direction to insert do not exist
			Direction existingEntity = null;
			if (existingEntity != null) {
				throw new InternalErrorException(functionalError.DATA_EXIST("direction id -> " + dto.getId(), locale));
			}

			// verif unique code in db
			existingEntity = directionRepository.findByCode(dto.getCode(), false);
			if (existingEntity != null) {
				throw new InternalErrorException(functionalError.DATA_EXIST("direction code -> " + dto.getCode(), locale));
			}
			// verif unique code in items to save
			if (items.stream().anyMatch(a -> a.getCode().equalsIgnoreCase(dto.getCode()))) {
				throw new InternalErrorException(functionalError.DATA_DUPLICATE(" code ", locale));
			}

			// verif unique intitule in db
			existingEntity = directionRepository.findByIntitule(dto.getIntitule(), false);
			if (existingEntity != null) {
				throw new InternalErrorException(functionalError.DATA_EXIST("direction intitule -> " + dto.getIntitule(), locale));
			}
			// verif unique intitule in items to save
			if (items.stream().anyMatch(a -> a.getIntitule().equalsIgnoreCase(dto.getIntitule()))) {
				throw new InternalErrorException(functionalError.DATA_DUPLICATE(" intitule ", locale));
			}

			// Verify if markaz exist
			Markaz existingMarkaz = null;
			if (dto.getIdMarkaz() != null && dto.getIdMarkaz() > 0){
				existingMarkaz = markazRepository.findOne(dto.getIdMarkaz(), false);
				if (existingMarkaz == null) {
					throw new EntityNotFoundException(functionalError.DATA_NOT_EXIST("markaz idMarkaz -> " + dto.getIdMarkaz(), locale));
				}
			}
			Direction entityToSave = null;
			entityToSave = DirectionTransformer.INSTANCE.toEntity(dto, existingMarkaz);
			entityToSave.setCreatedAt(Utilities.getCurrentDate());
			entityToSave.setCreatedBy(request.getUser());
			entityToSave.setCode("D-"+Utilities.generateCode());
			entityToSave.setIsDeleted(false);
			items.add(entityToSave);
		}

		if (!items.isEmpty()) {
			List<Direction> itemsSaved = null;
			// inserer les donnees en base de donnees
			itemsSaved = directionRepository.saveAll((Iterable<Direction>) items);
			if (itemsSaved == null) {
				throw new InternalErrorException(functionalError.SAVE_FAIL("direction", locale));
			}
			List<DirectionDto> itemsDto = (Utilities.isTrue(request.getIsSimpleLoading())) ? DirectionTransformer.INSTANCE.toLiteDtos(itemsSaved) : DirectionTransformer.INSTANCE.toDtos(itemsSaved);

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

		log.info("----end create Direction-----");
		return response;
	}

	/**
	 * update Direction by using DirectionDto as object.
	 * 
	 * @param request
	 * @return response
	 * 
	 */
	@Override
	public Response<DirectionDto> update(Request<DirectionDto> request, Locale locale)  throws ParseException {
		log.info("----begin update Direction-----");

		Response<DirectionDto> response = new Response<DirectionDto>();
		List<Direction>        items    = new ArrayList<Direction>();
			
		for (DirectionDto dto : request.getDatas()) {
			// Definir les parametres obligatoires
			Map<String, java.lang.Object> fieldsToVerify = new HashMap<String, java.lang.Object>();
			fieldsToVerify.put("id", dto.getId());
			if (!Validate.RequiredValue(fieldsToVerify).isGood()) {
				throw new InvalidEntityException(functionalError.FIELD_EMPTY(Validate.getValidate().getField(), locale));
			}

			// Verifier si la direction existe
			Direction entityToSave = null;
			entityToSave = directionRepository.findOne(dto.getId(), false);
			if (entityToSave == null) {
				throw new EntityNotFoundException(functionalError.DATA_NOT_EXIST("direction id -> " + dto.getId(), locale));
			}

			// Verify if markaz exist
			if (dto.getIdMarkaz() != null && dto.getIdMarkaz() > 0){
				Markaz existingMarkaz = markazRepository.findOne(dto.getIdMarkaz(), false);
				if (existingMarkaz == null) {
					throw new EntityNotFoundException(functionalError.DATA_NOT_EXIST("markaz idMarkaz -> " + dto.getIdMarkaz(), locale));
				}
				entityToSave.setMarkaz(existingMarkaz);
			}
			if (Utilities.notBlank(dto.getCode())) {
				entityToSave.setCode(dto.getCode());
			}
			if (Utilities.notBlank(dto.getIntitule())) {
				entityToSave.setIntitule(dto.getIntitule());
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
			List<Direction> itemsSaved = null;
			// maj les donnees en base
			itemsSaved = directionRepository.saveAll((Iterable<Direction>) items);
			if (itemsSaved == null) {
				throw new InternalErrorException(functionalError.SAVE_FAIL("direction", locale));
			}
			List<DirectionDto> itemsDto = (Utilities.isTrue(request.getIsSimpleLoading())) ? DirectionTransformer.INSTANCE.toLiteDtos(itemsSaved) : DirectionTransformer.INSTANCE.toDtos(itemsSaved);

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

		log.info("----end update Direction-----");
		return response;
	}

	/**
	 * delete Direction by using DirectionDto as object.
	 * 
	 * @param request
	 * @return response
	 * 
	 */
	@Override
	public Response<DirectionDto> delete(Request<DirectionDto> request, Locale locale)  {
		log.info("----begin delete Direction-----");

		Response<DirectionDto> response = new Response<DirectionDto>();
		List<Direction>        items    = new ArrayList<Direction>();
			
		for (DirectionDto dto : request.getDatas()) {
			// Definir les parametres obligatoires
			Map<String, java.lang.Object> fieldsToVerify = new HashMap<String, java.lang.Object>();
			fieldsToVerify.put("id", dto.getId());
			if (!Validate.RequiredValue(fieldsToVerify).isGood()) {
				throw new InvalidEntityException(functionalError.FIELD_EMPTY(Validate.getValidate().getField(), locale));
			}

			// Verifier si la direction existe
			Direction existingEntity = null;
			existingEntity = directionRepository.findOne(dto.getId(), false);
			if (existingEntity == null) {
				throw new EntityNotFoundException(functionalError.DATA_NOT_EXIST("direction -> " + dto.getId(), locale));
			}

			// -----------------------------------------------------------------------
			// ----------- CHECK IF DATA IS USED
			// -----------------------------------------------------------------------



			existingEntity.setIsDeleted(true);
			items.add(existingEntity);
		}

		if (!items.isEmpty()) {
			// supprimer les donnees en base
			directionRepository.saveAll((Iterable<Direction>) items);

			Status status = new Status();
			status.setCode(StatusCode.SUCCESS);
			status.setMessage(StatusMessage.SUCCESS);

			response.setHasError(false);
			response.setHttpCode(HttpStatus.OK.value());
			response.setStatus(status);
		}

		log.info("----end delete Direction-----");
		return response;
	}

	/**
	 * get Direction by using DirectionDto as object.
	 * 
	 * @param request
	 * @return response
	 * 
	 */
	@Override
	public Response<DirectionDto> getByCriteria(Request<DirectionDto> request, Locale locale)  throws Exception {
		log.info("----begin get Direction-----");

		Response<DirectionDto> response = new Response<DirectionDto>();
		List<Direction> items 			 = directionRepository.getByCriteria(request, em, locale);

		if (items != null && !items.isEmpty()) {
			List<DirectionDto> itemsDto = (Utilities.isTrue(request.getIsSimpleLoading())) ? DirectionTransformer.INSTANCE.toLiteDtos(items) : DirectionTransformer.INSTANCE.toDtos(items);

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
			response.setCount(directionRepository.count(request, em, locale));
			response.setHasError(false);
		} else {
			response.setStatus(functionalError.DATA_EMPTY("direction", locale));
			response.setHasError(false);
			return response;
		}

		log.info("----end get Direction-----");
		return response;
	}

	/**
	 * get full DirectionDto by using Direction as object.
	 * 
	 * @param dto
	 * @param size
	 * @param isSimpleLoading
	 * @param locale
	 * @return
	 * @throws Exception
	 */
	private DirectionDto getFullInfos(DirectionDto dto, Integer size, Boolean isSimpleLoading, Locale locale) throws Exception {
		// put code here

		if (Utilities.isTrue(isSimpleLoading)) {
			return dto;
		}
		if (size > 1) {
			return dto;
		}

		return dto;
	}

	public Response<DirectionDto> custom(Request<DirectionDto> request, Locale locale) {
		log.info("----begin custom DirectionDto-----");
		Response<DirectionDto> response = new Response<DirectionDto>();
		
		response.setHasError(false);
		response.setCount(1L);
		response.setItems(Arrays.asList(new DirectionDto()));
		log.info("----end custom DirectionDto-----");
		return response;
	}
}
