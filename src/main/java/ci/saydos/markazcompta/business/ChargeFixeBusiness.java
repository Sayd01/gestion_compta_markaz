                                        									
/*
 * Java business for entity table charge_fixe 
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
import ci.saydos.markazcompta.dao.entity.ChargeFixe;
import ci.saydos.markazcompta.dao.entity.*;
import ci.saydos.markazcompta.dao.repository.*;

/**
BUSINESS for table "charge_fixe"
 * 
 * @author Geo
 *
 */
@Log
@Component
public class ChargeFixeBusiness implements IBasicBusiness<Request<ChargeFixeDto>, Response<ChargeFixeDto>> {

	private Response<ChargeFixeDto> response;
	@Autowired
	private ChargeFixeRepository chargeFixeRepository;
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

	public ChargeFixeBusiness() {
		dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		dateTimeFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
	}
	
	/**
	 * create ChargeFixe by using ChargeFixeDto as object.
	 * 
	 * @param request
	 * @return response
	 * 
	 */
	@Override
	public Response<ChargeFixeDto> create(Request<ChargeFixeDto> request, Locale locale)  throws ParseException {
		log.info("----begin create ChargeFixe-----");

		Response<ChargeFixeDto> response = new Response<ChargeFixeDto>();
		List<ChargeFixe>        items    = new ArrayList<ChargeFixe>();
			
		for (ChargeFixeDto dto : request.getDatas()) {
			// Definir les parametres obligatoires
			Map<String, java.lang.Object> fieldsToVerify = new HashMap<String, java.lang.Object>();
			fieldsToVerify.put("label", dto.getLabel());
			if (!Validate.RequiredValue(fieldsToVerify).isGood()) {
				throw new InvalidEntityException(functionalError.FIELD_EMPTY(Validate.getValidate().getField(), locale));
			}

			// Verify if chargeFixe to insert do not exist
			ChargeFixe existingEntity = null;
			if (existingEntity != null) {
				throw new InternalErrorException(functionalError.DATA_EXIST("chargeFixe id -> " + dto.getId(), locale));
			}

			// verif unique code in db
			existingEntity = chargeFixeRepository.findByCode(dto.getCode(), false);
			if (existingEntity != null) {
				throw new InternalErrorException(functionalError.DATA_EXIST("chargeFixe code -> " + dto.getCode(), locale));
			}
			// verif unique code in items to save
			if (items.stream().anyMatch(a -> a.getCode().equalsIgnoreCase(dto.getCode()))) {
				throw new InternalErrorException(functionalError.DATA_DUPLICATE(" code ", locale));
			}

			ChargeFixe entityToSave = null;
			entityToSave = ChargeFixeTransformer.INSTANCE.toEntity(dto);
			entityToSave.setCreatedAt(Utilities.getCurrentDate());
			entityToSave.setCreatedBy(request.getUser());
			entityToSave.setCode("chf"+Utilities.generateCode());
			entityToSave.setIsDeleted(false);
			items.add(entityToSave);
		}

		if (!items.isEmpty()) {
			List<ChargeFixe> itemsSaved = null;
			// inserer les donnees en base de donnees
			itemsSaved = chargeFixeRepository.saveAll((Iterable<ChargeFixe>) items);
			if (itemsSaved == null) {
				throw new InternalErrorException(functionalError.SAVE_FAIL("chargeFixe", locale));
			}
			List<ChargeFixeDto> itemsDto = (Utilities.isTrue(request.getIsSimpleLoading())) ? ChargeFixeTransformer.INSTANCE.toLiteDtos(itemsSaved) : ChargeFixeTransformer.INSTANCE.toDtos(itemsSaved);

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

		log.info("----end create ChargeFixe-----");
		return response;
	}

	/**
	 * update ChargeFixe by using ChargeFixeDto as object.
	 * 
	 * @param request
	 * @return response
	 * 
	 */
	@Override
	public Response<ChargeFixeDto> update(Request<ChargeFixeDto> request, Locale locale)  throws ParseException {
		log.info("----begin update ChargeFixe-----");

		Response<ChargeFixeDto> response = new Response<ChargeFixeDto>();
		List<ChargeFixe>        items    = new ArrayList<ChargeFixe>();
			
		for (ChargeFixeDto dto : request.getDatas()) {
			// Definir les parametres obligatoires
			Map<String, java.lang.Object> fieldsToVerify = new HashMap<String, java.lang.Object>();
			fieldsToVerify.put("id", dto.getId());
			if (!Validate.RequiredValue(fieldsToVerify).isGood()) {
				throw new InvalidEntityException(functionalError.FIELD_EMPTY(Validate.getValidate().getField(), locale));
			}

			// Verifier si la chargeFixe existe
			ChargeFixe entityToSave = null;
			entityToSave = chargeFixeRepository.findOne(dto.getId(), false);
			if (entityToSave == null) {
				throw new EntityNotFoundException(functionalError.DATA_NOT_EXIST("chargeFixe id -> " + dto.getId(), locale));
			}

			if (Utilities.notBlank(dto.getCode())) {
				entityToSave.setCode(dto.getCode());
			}
			if (Utilities.notBlank(dto.getLabel())) {
				entityToSave.setLabel(dto.getLabel());
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
			List<ChargeFixe> itemsSaved = null;
			// maj les donnees en base
			itemsSaved = chargeFixeRepository.saveAll((Iterable<ChargeFixe>) items);
			if (itemsSaved == null) {
				throw new InternalErrorException(functionalError.SAVE_FAIL("chargeFixe", locale));
			}
			List<ChargeFixeDto> itemsDto = (Utilities.isTrue(request.getIsSimpleLoading())) ? ChargeFixeTransformer.INSTANCE.toLiteDtos(itemsSaved) : ChargeFixeTransformer.INSTANCE.toDtos(itemsSaved);

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

		log.info("----end update ChargeFixe-----");
		return response;
	}

	/**
	 * delete ChargeFixe by using ChargeFixeDto as object.
	 * 
	 * @param request
	 * @return response
	 * 
	 */
	@Override
	public Response<ChargeFixeDto> delete(Request<ChargeFixeDto> request, Locale locale)  {
		log.info("----begin delete ChargeFixe-----");

		Response<ChargeFixeDto> response = new Response<ChargeFixeDto>();
		List<ChargeFixe>        items    = new ArrayList<ChargeFixe>();
			
		for (ChargeFixeDto dto : request.getDatas()) {
			// Definir les parametres obligatoires
			Map<String, java.lang.Object> fieldsToVerify = new HashMap<String, java.lang.Object>();
			fieldsToVerify.put("id", dto.getId());
			if (!Validate.RequiredValue(fieldsToVerify).isGood()) {
				throw new InvalidEntityException(functionalError.FIELD_EMPTY(Validate.getValidate().getField(), locale));
			}

			// Verifier si la chargeFixe existe
			ChargeFixe existingEntity = null;
			existingEntity = chargeFixeRepository.findOne(dto.getId(), false);
			if (existingEntity == null) {
				throw new EntityNotFoundException(functionalError.DATA_NOT_EXIST("chargeFixe -> " + dto.getId(), locale));
			}

			// -----------------------------------------------------------------------
			// ----------- CHECK IF DATA IS USED
			// -----------------------------------------------------------------------



			existingEntity.setIsDeleted(true);
			items.add(existingEntity);
		}

		if (!items.isEmpty()) {
			// supprimer les donnees en base
			chargeFixeRepository.saveAll((Iterable<ChargeFixe>) items);

			Status status = new Status();
			status.setCode(StatusCode.SUCCESS);
			status.setMessage(StatusMessage.SUCCESS);

			response.setHasError(false);
			response.setHttpCode(HttpStatus.OK.value());
			response.setStatus(status);
		}

		log.info("----end delete ChargeFixe-----");
		return response;
	}

	/**
	 * get ChargeFixe by using ChargeFixeDto as object.
	 * 
	 * @param request
	 * @return response
	 * 
	 */
	@Override
	public Response<ChargeFixeDto> getByCriteria(Request<ChargeFixeDto> request, Locale locale)  throws Exception {
		log.info("----begin get ChargeFixe-----");

		Response<ChargeFixeDto> response = new Response<ChargeFixeDto>();
		List<ChargeFixe> items 			 = chargeFixeRepository.getByCriteria(request, em, locale);

		if (items != null && !items.isEmpty()) {
			List<ChargeFixeDto> itemsDto = (Utilities.isTrue(request.getIsSimpleLoading())) ? ChargeFixeTransformer.INSTANCE.toLiteDtos(items) : ChargeFixeTransformer.INSTANCE.toDtos(items);

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
			response.setCount(chargeFixeRepository.count(request, em, locale));
			response.setHasError(false);
		} else {
			response.setStatus(functionalError.DATA_EMPTY("chargeFixe", locale));
			response.setHasError(false);
			return response;
		}

		log.info("----end get ChargeFixe-----");
		return response;
	}

	/**
	 * get full ChargeFixeDto by using ChargeFixe as object.
	 * 
	 * @param dto
	 * @param size
	 * @param isSimpleLoading
	 * @param locale
	 * @return
	 * @throws Exception
	 */
	private ChargeFixeDto getFullInfos(ChargeFixeDto dto, Integer size, Boolean isSimpleLoading, Locale locale) throws Exception {
		// put code here

		if (Utilities.isTrue(isSimpleLoading)) {
			return dto;
		}
		if (size > 1) {
			return dto;
		}

		return dto;
	}

	public Response<ChargeFixeDto> custom(Request<ChargeFixeDto> request, Locale locale) {
		log.info("----begin custom ChargeFixeDto-----");
		Response<ChargeFixeDto> response = new Response<ChargeFixeDto>();
		
		response.setHasError(false);
		response.setCount(1L);
		response.setItems(Arrays.asList(new ChargeFixeDto()));
		log.info("----end custom ChargeFixeDto-----");
		return response;
	}
}
