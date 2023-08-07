                                                    												
/*
 * Java business for entity table stock_historique 
 * Created on 2023-08-06 ( Time 01:31:06 )
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
import ci.saydos.markazcompta.dao.entity.StockHistorique;
import ci.saydos.markazcompta.dao.entity.Stock;
import ci.saydos.markazcompta.dao.entity.*;
import ci.saydos.markazcompta.dao.repository.*;

/**
BUSINESS for table "stock_historique"
 * 
 * @author Geo
 *
 */
@Log
@Component
public class StockHistoriqueBusiness implements IBasicBusiness<Request<StockHistoriqueDto>, Response<StockHistoriqueDto>> {

	private Response<StockHistoriqueDto> response;
	@Autowired
	private StockHistoriqueRepository stockHistoriqueRepository;
	@Autowired
	private StockRepository stockRepository;
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

	public StockHistoriqueBusiness() {
		dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		dateTimeFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
	}
	
	/**
	 * create StockHistorique by using StockHistoriqueDto as object.
	 * 
	 * @param request
	 * @return response
	 * 
	 */
	@Override
	public Response<StockHistoriqueDto> create(Request<StockHistoriqueDto> request, Locale locale)  throws ParseException {
		log.info("----begin create StockHistorique-----");

		Response<StockHistoriqueDto> response = new Response<StockHistoriqueDto>();
		List<StockHistorique>        items    = new ArrayList<StockHistorique>();
			
		for (StockHistoriqueDto dto : request.getDatas()) {
			// Definir les parametres obligatoires
			Map<String, java.lang.Object> fieldsToVerify = new HashMap<String, java.lang.Object>();
			fieldsToVerify.put("intitule", dto.getIntitule());
			fieldsToVerify.put("idStock", dto.getIdStock());
			fieldsToVerify.put("quantite", dto.getQuantite());
			fieldsToVerify.put("etat", dto.getEtat());
			fieldsToVerify.put("dateMvt", dto.getDateMvt());
			if (!Validate.RequiredValue(fieldsToVerify).isGood()) {
				throw new InvalidEntityException(functionalError.FIELD_EMPTY(Validate.getValidate().getField(), locale));
			}

			// Verify if stockHistorique to insert do not exist
			StockHistorique existingEntity = null;
			if (existingEntity != null) {
				throw new InternalErrorException(functionalError.DATA_EXIST("stockHistorique id -> " + dto.getId(), locale));
			}

			// verif unique intitule in db
			existingEntity = stockHistoriqueRepository.findByIntitule(dto.getIntitule(), false);
			if (existingEntity != null) {
				throw new InternalErrorException(functionalError.DATA_EXIST("stockHistorique intitule -> " + dto.getIntitule(), locale));
			}
			// verif unique intitule in items to save
			if (items.stream().anyMatch(a -> a.getIntitule().equalsIgnoreCase(dto.getIntitule()))) {
				throw new InternalErrorException(functionalError.DATA_DUPLICATE(" intitule ", locale));
			}

			// Verify if stock exist
			Stock existingStock = null;
			if (dto.getIdStock() != null && dto.getIdStock() > 0){
				existingStock = stockRepository.findOne(dto.getIdStock(), false);
				if (existingStock == null) {
					throw new EntityNotFoundException(functionalError.DATA_NOT_EXIST("stock idStock -> " + dto.getIdStock(), locale));
				}
			}
				StockHistorique entityToSave = null;
			entityToSave = StockHistoriqueTransformer.INSTANCE.toEntity(dto, existingStock);
			entityToSave.setCreatedAt(Utilities.getCurrentDate());
			entityToSave.setCreatedBy(request.getUser());
			entityToSave.setIsDeleted(false);
			items.add(entityToSave);
		}

		if (!items.isEmpty()) {
			List<StockHistorique> itemsSaved = null;
			// inserer les donnees en base de donnees
			itemsSaved = stockHistoriqueRepository.saveAll((Iterable<StockHistorique>) items);
			if (itemsSaved == null) {
				throw new InternalErrorException(functionalError.SAVE_FAIL("stockHistorique", locale));
			}
			List<StockHistoriqueDto> itemsDto = (Utilities.isTrue(request.getIsSimpleLoading())) ? StockHistoriqueTransformer.INSTANCE.toLiteDtos(itemsSaved) : StockHistoriqueTransformer.INSTANCE.toDtos(itemsSaved);

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

		log.info("----end create StockHistorique-----");
		return response;
	}

	/**
	 * update StockHistorique by using StockHistoriqueDto as object.
	 * 
	 * @param request
	 * @return response
	 * 
	 */
	@Override
	public Response<StockHistoriqueDto> update(Request<StockHistoriqueDto> request, Locale locale)  throws ParseException {
		log.info("----begin update StockHistorique-----");

		Response<StockHistoriqueDto> response = new Response<StockHistoriqueDto>();
		List<StockHistorique>        items    = new ArrayList<StockHistorique>();
			
		for (StockHistoriqueDto dto : request.getDatas()) {
			// Definir les parametres obligatoires
			Map<String, java.lang.Object> fieldsToVerify = new HashMap<String, java.lang.Object>();
			fieldsToVerify.put("id", dto.getId());
			if (!Validate.RequiredValue(fieldsToVerify).isGood()) {
				throw new InvalidEntityException(functionalError.FIELD_EMPTY(Validate.getValidate().getField(), locale));
			}

			// Verifier si la stockHistorique existe
			StockHistorique entityToSave = null;
			entityToSave = stockHistoriqueRepository.findOne(dto.getId(), false);
			if (entityToSave == null) {
				throw new EntityNotFoundException(functionalError.DATA_NOT_EXIST("stockHistorique id -> " + dto.getId(), locale));
			}

			// Verify if stock exist
			if (dto.getIdStock() != null && dto.getIdStock() > 0){
				Stock existingStock = stockRepository.findOne(dto.getIdStock(), false);
				if (existingStock == null) {
					throw new EntityNotFoundException(functionalError.DATA_NOT_EXIST("stock idStock -> " + dto.getIdStock(), locale));
				}
				entityToSave.setStock(existingStock);
			}
			if (Utilities.notBlank(dto.getIntitule())) {
				entityToSave.setIntitule(dto.getIntitule());
			}
			if (dto.getQuantite() != null && dto.getQuantite() > 0) {
				entityToSave.setQuantite(dto.getQuantite());
			}
			if (Utilities.notBlank(dto.getEtat())) {
				entityToSave.setEtat(dto.getEtat());
			}
			if (Utilities.notBlank(dto.getDateMvt())) {
				entityToSave.setDateMvt(dateFormat.parse(dto.getDateMvt()));
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
			List<StockHistorique> itemsSaved = null;
			// maj les donnees en base
			itemsSaved = stockHistoriqueRepository.saveAll((Iterable<StockHistorique>) items);
			if (itemsSaved == null) {
				throw new InternalErrorException(functionalError.SAVE_FAIL("stockHistorique", locale));
			}
			List<StockHistoriqueDto> itemsDto = (Utilities.isTrue(request.getIsSimpleLoading())) ? StockHistoriqueTransformer.INSTANCE.toLiteDtos(itemsSaved) : StockHistoriqueTransformer.INSTANCE.toDtos(itemsSaved);

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

		log.info("----end update StockHistorique-----");
		return response;
	}

	/**
	 * delete StockHistorique by using StockHistoriqueDto as object.
	 * 
	 * @param request
	 * @return response
	 * 
	 */
	@Override
	public Response<StockHistoriqueDto> delete(Request<StockHistoriqueDto> request, Locale locale)  {
		log.info("----begin delete StockHistorique-----");

		Response<StockHistoriqueDto> response = new Response<StockHistoriqueDto>();
		List<StockHistorique>        items    = new ArrayList<StockHistorique>();
			
		for (StockHistoriqueDto dto : request.getDatas()) {
			// Definir les parametres obligatoires
			Map<String, java.lang.Object> fieldsToVerify = new HashMap<String, java.lang.Object>();
			fieldsToVerify.put("id", dto.getId());
			if (!Validate.RequiredValue(fieldsToVerify).isGood()) {
				throw new InvalidEntityException(functionalError.FIELD_EMPTY(Validate.getValidate().getField(), locale));
			}

			// Verifier si la stockHistorique existe
			StockHistorique existingEntity = null;
			existingEntity = stockHistoriqueRepository.findOne(dto.getId(), false);
			if (existingEntity == null) {
				throw new EntityNotFoundException(functionalError.DATA_NOT_EXIST("stockHistorique -> " + dto.getId(), locale));
			}

			// -----------------------------------------------------------------------
			// ----------- CHECK IF DATA IS USED
			// -----------------------------------------------------------------------



			existingEntity.setIsDeleted(true);
			items.add(existingEntity);
		}

		if (!items.isEmpty()) {
			// supprimer les donnees en base
			stockHistoriqueRepository.saveAll((Iterable<StockHistorique>) items);

			Status status = new Status();
			status.setCode(StatusCode.SUCCESS);
			status.setMessage(StatusMessage.SUCCESS);

			response.setHasError(false);
			response.setHttpCode(HttpStatus.OK.value());
			response.setStatus(status);
		}

		log.info("----end delete StockHistorique-----");
		return response;
	}

	/**
	 * get StockHistorique by using StockHistoriqueDto as object.
	 * 
	 * @param request
	 * @return response
	 * 
	 */
	@Override
	public Response<StockHistoriqueDto> getByCriteria(Request<StockHistoriqueDto> request, Locale locale)  throws Exception {
		log.info("----begin get StockHistorique-----");

		Response<StockHistoriqueDto> response = new Response<StockHistoriqueDto>();
		List<StockHistorique> items 			 = stockHistoriqueRepository.getByCriteria(request, em, locale);

		if (items != null && !items.isEmpty()) {
			List<StockHistoriqueDto> itemsDto = (Utilities.isTrue(request.getIsSimpleLoading())) ? StockHistoriqueTransformer.INSTANCE.toLiteDtos(items) : StockHistoriqueTransformer.INSTANCE.toDtos(items);

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
			response.setCount(stockHistoriqueRepository.count(request, em, locale));
			response.setHasError(false);
		} else {
			response.setStatus(functionalError.DATA_EMPTY("stockHistorique", locale));
			response.setHasError(false);
			return response;
		}

		log.info("----end get StockHistorique-----");
		return response;
	}

	/**
	 * get full StockHistoriqueDto by using StockHistorique as object.
	 * 
	 * @param dto
	 * @param size
	 * @param isSimpleLoading
	 * @param locale
	 * @return
	 * @throws Exception
	 */
	private StockHistoriqueDto getFullInfos(StockHistoriqueDto dto, Integer size, Boolean isSimpleLoading, Locale locale) throws Exception {
		// put code here

		if (Utilities.isTrue(isSimpleLoading)) {
			return dto;
		}
		if (size > 1) {
			return dto;
		}

		return dto;
	}

	public Response<StockHistoriqueDto> custom(Request<StockHistoriqueDto> request, Locale locale) {
		log.info("----begin custom StockHistoriqueDto-----");
		Response<StockHistoriqueDto> response = new Response<StockHistoriqueDto>();
		
		response.setHasError(false);
		response.setCount(1L);
		response.setItems(Arrays.asList(new StockHistoriqueDto()));
		log.info("----end custom StockHistoriqueDto-----");
		return response;
	}
}
