                                                    												
/*
 * Java business for entity table stock 
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
import ci.saydos.markazcompta.dao.entity.Stock;
import ci.saydos.markazcompta.dao.entity.*;
import ci.saydos.markazcompta.dao.repository.*;

/**
BUSINESS for table "stock"
 * 
 * @author Geo
 *
 */
@Log
@Component
public class StockBusiness implements IBasicBusiness<Request<StockDto>, Response<StockDto>> {

	private Response<StockDto> response;
	@Autowired
	private StockRepository stockRepository;
	@Autowired
	private StockHistoriqueRepository stockHistoriqueRepository;
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

	public StockBusiness() {
		dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		dateTimeFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
	}
	
	/**
	 * create Stock by using StockDto as object.
	 * 
	 * @param request
	 * @return response
	 * 
	 */
	@Override
	public Response<StockDto> create(Request<StockDto> request, Locale locale)  throws ParseException {
		log.info("----begin create Stock-----");

		Response<StockDto> response = new Response<StockDto>();
		List<Stock>        items    = new ArrayList<Stock>();
			
		for (StockDto dto : request.getDatas()) {
			// Definir les parametres obligatoires
			Map<String, java.lang.Object> fieldsToVerify = new HashMap<String, java.lang.Object>();
			fieldsToVerify.put("intitule", dto.getIntitule());
			fieldsToVerify.put("quantite", dto.getQuantite());
			fieldsToVerify.put("etat", dto.getEtat());
			fieldsToVerify.put("dateEntree", dto.getDateEntree());
			fieldsToVerify.put("dateSortie", dto.getDateSortie());
			if (!Validate.RequiredValue(fieldsToVerify).isGood()) {
				throw new InvalidEntityException(functionalError.FIELD_EMPTY(Validate.getValidate().getField(), locale));
			}

			// Verify if stock to insert do not exist
			Stock existingEntity = null;
			if (existingEntity != null) {
				throw new InternalErrorException(functionalError.DATA_EXIST("stock id -> " + dto.getId(), locale));
			}

			// verif unique intitule in db
			existingEntity = stockRepository.findByIntitule(dto.getIntitule(), false);
			if (existingEntity != null) {
				throw new InternalErrorException(functionalError.DATA_EXIST("stock intitule -> " + dto.getIntitule(), locale));
			}
			// verif unique intitule in items to save
			if (items.stream().anyMatch(a -> a.getIntitule().equalsIgnoreCase(dto.getIntitule()))) {
				throw new InternalErrorException(functionalError.DATA_DUPLICATE(" intitule ", locale));
			}

				Stock entityToSave = null;
			entityToSave = StockTransformer.INSTANCE.toEntity(dto);
			entityToSave.setCreatedAt(Utilities.getCurrentDate());
			entityToSave.setCreatedBy(request.getUser());
			entityToSave.setIsDeleted(false);
			items.add(entityToSave);
		}

		if (!items.isEmpty()) {
			List<Stock> itemsSaved = null;
			// inserer les donnees en base de donnees
			itemsSaved = stockRepository.saveAll((Iterable<Stock>) items);
			if (itemsSaved == null) {
				throw new InternalErrorException(functionalError.SAVE_FAIL("stock", locale));
			}
			List<StockDto> itemsDto = (Utilities.isTrue(request.getIsSimpleLoading())) ? StockTransformer.INSTANCE.toLiteDtos(itemsSaved) : StockTransformer.INSTANCE.toDtos(itemsSaved);

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

		log.info("----end create Stock-----");
		return response;
	}

	/**
	 * update Stock by using StockDto as object.
	 * 
	 * @param request
	 * @return response
	 * 
	 */
	@Override
	public Response<StockDto> update(Request<StockDto> request, Locale locale)  throws ParseException {
		log.info("----begin update Stock-----");

		Response<StockDto> response = new Response<StockDto>();
		List<Stock>        items    = new ArrayList<Stock>();
			
		for (StockDto dto : request.getDatas()) {
			// Definir les parametres obligatoires
			Map<String, java.lang.Object> fieldsToVerify = new HashMap<String, java.lang.Object>();
			fieldsToVerify.put("id", dto.getId());
			if (!Validate.RequiredValue(fieldsToVerify).isGood()) {
				throw new InvalidEntityException(functionalError.FIELD_EMPTY(Validate.getValidate().getField(), locale));
			}

			// Verifier si la stock existe
			Stock entityToSave = null;
			entityToSave = stockRepository.findOne(dto.getId(), false);
			if (entityToSave == null) {
				throw new EntityNotFoundException(functionalError.DATA_NOT_EXIST("stock id -> " + dto.getId(), locale));
			}

			if (Utilities.notBlank(dto.getIntitule())) {
				entityToSave.setIntitule(dto.getIntitule());
			}
			if (dto.getQuantite() != null && dto.getQuantite() > 0) {
				entityToSave.setQuantite(dto.getQuantite());
			}
			if (Utilities.notBlank(dto.getEtat().toString())) {
				entityToSave.setEtat(dto.getEtat());
			}
			if (Utilities.notBlank(dto.getDateEntree())) {
				entityToSave.setDateEntree(dateFormat.parse(dto.getDateEntree()));
			}
			if (Utilities.notBlank(dto.getDateSortie())) {
				entityToSave.setDateSortie(dateFormat.parse(dto.getDateSortie()));
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
			List<Stock> itemsSaved = null;
			// maj les donnees en base
			itemsSaved = stockRepository.saveAll((Iterable<Stock>) items);
			if (itemsSaved == null) {
				throw new InternalErrorException(functionalError.SAVE_FAIL("stock", locale));
			}
			List<StockDto> itemsDto = (Utilities.isTrue(request.getIsSimpleLoading())) ? StockTransformer.INSTANCE.toLiteDtos(itemsSaved) : StockTransformer.INSTANCE.toDtos(itemsSaved);

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

		log.info("----end update Stock-----");
		return response;
	}

	/**
	 * delete Stock by using StockDto as object.
	 * 
	 * @param request
	 * @return response
	 * 
	 */
	@Override
	public Response<StockDto> delete(Request<StockDto> request, Locale locale)  {
		log.info("----begin delete Stock-----");

		Response<StockDto> response = new Response<StockDto>();
		List<Stock>        items    = new ArrayList<Stock>();
			
		for (StockDto dto : request.getDatas()) {
			// Definir les parametres obligatoires
			Map<String, java.lang.Object> fieldsToVerify = new HashMap<String, java.lang.Object>();
			fieldsToVerify.put("id", dto.getId());
			if (!Validate.RequiredValue(fieldsToVerify).isGood()) {
				throw new InvalidEntityException(functionalError.FIELD_EMPTY(Validate.getValidate().getField(), locale));
			}

			// Verifier si la stock existe
			Stock existingEntity = null;
			existingEntity = stockRepository.findOne(dto.getId(), false);
			if (existingEntity == null) {
				throw new EntityNotFoundException(functionalError.DATA_NOT_EXIST("stock -> " + dto.getId(), locale));
			}

			// -----------------------------------------------------------------------
			// ----------- CHECK IF DATA IS USED
			// -----------------------------------------------------------------------

			// stockHistorique
			List<StockHistorique> listOfStockHistorique = stockHistoriqueRepository.findByIdStock(existingEntity.getId(), false);
			if (listOfStockHistorique != null && !listOfStockHistorique.isEmpty()){
				throw new InternalErrorException(functionalError.DATA_NOT_DELETABLE("(" + listOfStockHistorique.size() + ")", locale));
			}


			existingEntity.setIsDeleted(true);
			items.add(existingEntity);
		}

		if (!items.isEmpty()) {
			// supprimer les donnees en base
			stockRepository.saveAll((Iterable<Stock>) items);

			Status status = new Status();
			status.setCode(StatusCode.SUCCESS);
			status.setMessage(StatusMessage.SUCCESS);

			response.setHasError(false);
			response.setHttpCode(HttpStatus.OK.value());
			response.setStatus(status);
		}

		log.info("----end delete Stock-----");
		return response;
	}

	/**
	 * get Stock by using StockDto as object.
	 * 
	 * @param request
	 * @return response
	 * 
	 */
	@Override
	public Response<StockDto> getByCriteria(Request<StockDto> request, Locale locale)  throws Exception {
		log.info("----begin get Stock-----");

		Response<StockDto> response = new Response<StockDto>();
		List<Stock> items 			 = stockRepository.getByCriteria(request, em, locale);

		if (items != null && !items.isEmpty()) {
			List<StockDto> itemsDto = (Utilities.isTrue(request.getIsSimpleLoading())) ? StockTransformer.INSTANCE.toLiteDtos(items) : StockTransformer.INSTANCE.toDtos(items);

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
			response.setCount(stockRepository.count(request, em, locale));
			response.setHasError(false);
		} else {
			response.setStatus(functionalError.DATA_EMPTY("stock", locale));
			response.setHasError(false);
			return response;
		}

		log.info("----end get Stock-----");
		return response;
	}

	/**
	 * get full StockDto by using Stock as object.
	 * 
	 * @param dto
	 * @param size
	 * @param isSimpleLoading
	 * @param locale
	 * @return
	 * @throws Exception
	 */
	private StockDto getFullInfos(StockDto dto, Integer size, Boolean isSimpleLoading, Locale locale) throws Exception {
		// put code here

		if (Utilities.isTrue(isSimpleLoading)) {
			return dto;
		}
		if (size > 1) {
			return dto;
		}

		return dto;
	}

	public Response<StockDto> custom(Request<StockDto> request, Locale locale) {
		log.info("----begin custom StockDto-----");
		Response<StockDto> response = new Response<StockDto>();
		
		response.setHasError(false);
		response.setCount(1L);
		response.setItems(Arrays.asList(new StockDto()));
		log.info("----end custom StockDto-----");
		return response;
	}
}
