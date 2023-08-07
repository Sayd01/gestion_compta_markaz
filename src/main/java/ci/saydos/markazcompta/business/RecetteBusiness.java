                                        									
/*
 * Java business for entity table recette 
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
import ci.saydos.markazcompta.dao.entity.Recette;
import ci.saydos.markazcompta.dao.entity.*;
import ci.saydos.markazcompta.dao.repository.*;

/**
BUSINESS for table "recette"
 * 
 * @author Geo
 *
 */
@Log
@Component
public class RecetteBusiness implements IBasicBusiness<Request<RecetteDto>, Response<RecetteDto>> {

	private Response<RecetteDto> response;
	@Autowired
	private RecetteRepository recetteRepository;
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

	public RecetteBusiness() {
		dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		dateTimeFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
	}
	
	/**
	 * create Recette by using RecetteDto as object.
	 * 
	 * @param request
	 * @return response
	 * 
	 */
	@Override
	public Response<RecetteDto> create(Request<RecetteDto> request, Locale locale)  throws ParseException {
		log.info("----begin create Recette-----");

		Response<RecetteDto> response = new Response<RecetteDto>();
		List<Recette>        items    = new ArrayList<Recette>();
			
		for (RecetteDto dto : request.getDatas()) {
			// Definir les parametres obligatoires
			Map<String, java.lang.Object> fieldsToVerify = new HashMap<String, java.lang.Object>();
			fieldsToVerify.put("source", dto.getSource());
			fieldsToVerify.put("montant", dto.getMontant());
			if (!Validate.RequiredValue(fieldsToVerify).isGood()) {
				throw new InvalidEntityException(functionalError.FIELD_EMPTY(Validate.getValidate().getField(), locale));
			}

			// Verify if recette to insert do not exist
			Recette existingEntity = null;
			if (existingEntity != null) {
				throw new InternalErrorException(functionalError.DATA_EXIST("recette id -> " + dto.getId(), locale));
			}

				Recette entityToSave = null;
			entityToSave = RecetteTransformer.INSTANCE.toEntity(dto);
			entityToSave.setCreatedAt(Utilities.getCurrentDate());
			entityToSave.setCreatedBy(request.getUser());
			entityToSave.setIsDeleted(false);
			items.add(entityToSave);
		}

		if (!items.isEmpty()) {
			List<Recette> itemsSaved = null;
			// inserer les donnees en base de donnees
			itemsSaved = recetteRepository.saveAll((Iterable<Recette>) items);
			if (itemsSaved == null) {
				throw new InternalErrorException(functionalError.SAVE_FAIL("recette", locale));
			}
			List<RecetteDto> itemsDto = (Utilities.isTrue(request.getIsSimpleLoading())) ? RecetteTransformer.INSTANCE.toLiteDtos(itemsSaved) : RecetteTransformer.INSTANCE.toDtos(itemsSaved);

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

		log.info("----end create Recette-----");
		return response;
	}

	/**
	 * update Recette by using RecetteDto as object.
	 * 
	 * @param request
	 * @return response
	 * 
	 */
	@Override
	public Response<RecetteDto> update(Request<RecetteDto> request, Locale locale)  throws ParseException {
		log.info("----begin update Recette-----");

		Response<RecetteDto> response = new Response<RecetteDto>();
		List<Recette>        items    = new ArrayList<Recette>();
			
		for (RecetteDto dto : request.getDatas()) {
			// Definir les parametres obligatoires
			Map<String, java.lang.Object> fieldsToVerify = new HashMap<String, java.lang.Object>();
			fieldsToVerify.put("id", dto.getId());
			if (!Validate.RequiredValue(fieldsToVerify).isGood()) {
				throw new InvalidEntityException(functionalError.FIELD_EMPTY(Validate.getValidate().getField(), locale));
			}

			// Verifier si la recette existe
			Recette entityToSave = null;
			entityToSave = recetteRepository.findOne(dto.getId(), false);
			if (entityToSave == null) {
				throw new EntityNotFoundException(functionalError.DATA_NOT_EXIST("recette id -> " + dto.getId(), locale));
			}

			if (Utilities.notBlank(dto.getSource())) {
				entityToSave.setSource(dto.getSource());
			}
			if (Utilities.notBlank(dto.getMontant())) {
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
			List<Recette> itemsSaved = null;
			// maj les donnees en base
			itemsSaved = recetteRepository.saveAll((Iterable<Recette>) items);
			if (itemsSaved == null) {
				throw new InternalErrorException(functionalError.SAVE_FAIL("recette", locale));
			}
			List<RecetteDto> itemsDto = (Utilities.isTrue(request.getIsSimpleLoading())) ? RecetteTransformer.INSTANCE.toLiteDtos(itemsSaved) : RecetteTransformer.INSTANCE.toDtos(itemsSaved);

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

		log.info("----end update Recette-----");
		return response;
	}

	/**
	 * delete Recette by using RecetteDto as object.
	 * 
	 * @param request
	 * @return response
	 * 
	 */
	@Override
	public Response<RecetteDto> delete(Request<RecetteDto> request, Locale locale)  {
		log.info("----begin delete Recette-----");

		Response<RecetteDto> response = new Response<RecetteDto>();
		List<Recette>        items    = new ArrayList<Recette>();
			
		for (RecetteDto dto : request.getDatas()) {
			// Definir les parametres obligatoires
			Map<String, java.lang.Object> fieldsToVerify = new HashMap<String, java.lang.Object>();
			fieldsToVerify.put("id", dto.getId());
			if (!Validate.RequiredValue(fieldsToVerify).isGood()) {
				throw new InvalidEntityException(functionalError.FIELD_EMPTY(Validate.getValidate().getField(), locale));
			}

			// Verifier si la recette existe
			Recette existingEntity = null;
			existingEntity = recetteRepository.findOne(dto.getId(), false);
			if (existingEntity == null) {
				throw new EntityNotFoundException(functionalError.DATA_NOT_EXIST("recette -> " + dto.getId(), locale));
			}

			// -----------------------------------------------------------------------
			// ----------- CHECK IF DATA IS USED
			// -----------------------------------------------------------------------



			existingEntity.setIsDeleted(true);
			items.add(existingEntity);
		}

		if (!items.isEmpty()) {
			// supprimer les donnees en base
			recetteRepository.saveAll((Iterable<Recette>) items);

			Status status = new Status();
			status.setCode(StatusCode.SUCCESS);
			status.setMessage(StatusMessage.SUCCESS);

			response.setHasError(false);
			response.setHttpCode(HttpStatus.OK.value());
			response.setStatus(status);
		}

		log.info("----end delete Recette-----");
		return response;
	}

	/**
	 * get Recette by using RecetteDto as object.
	 * 
	 * @param request
	 * @return response
	 * 
	 */
	@Override
	public Response<RecetteDto> getByCriteria(Request<RecetteDto> request, Locale locale)  throws Exception {
		log.info("----begin get Recette-----");

		Response<RecetteDto> response = new Response<RecetteDto>();
		List<Recette> items 			 = recetteRepository.getByCriteria(request, em, locale);

		if (items != null && !items.isEmpty()) {
			List<RecetteDto> itemsDto = (Utilities.isTrue(request.getIsSimpleLoading())) ? RecetteTransformer.INSTANCE.toLiteDtos(items) : RecetteTransformer.INSTANCE.toDtos(items);

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
			response.setCount(recetteRepository.count(request, em, locale));
			response.setHasError(false);
		} else {
			response.setStatus(functionalError.DATA_EMPTY("recette", locale));
			response.setHasError(false);
			return response;
		}

		log.info("----end get Recette-----");
		return response;
	}

	/**
	 * get full RecetteDto by using Recette as object.
	 * 
	 * @param dto
	 * @param size
	 * @param isSimpleLoading
	 * @param locale
	 * @return
	 * @throws Exception
	 */
	private RecetteDto getFullInfos(RecetteDto dto, Integer size, Boolean isSimpleLoading, Locale locale) throws Exception {
		// put code here

		if (Utilities.isTrue(isSimpleLoading)) {
			return dto;
		}
		if (size > 1) {
			return dto;
		}

		return dto;
	}

	public Response<RecetteDto> custom(Request<RecetteDto> request, Locale locale) {
		log.info("----begin custom RecetteDto-----");
		Response<RecetteDto> response = new Response<RecetteDto>();
		
		response.setHasError(false);
		response.setCount(1L);
		response.setItems(Arrays.asList(new RecetteDto()));
		log.info("----end custom RecetteDto-----");
		return response;
	}
}
