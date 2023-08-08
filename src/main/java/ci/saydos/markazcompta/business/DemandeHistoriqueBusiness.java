                                            										
/*
 * Java business for entity table demande_historique 
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
import ci.saydos.markazcompta.dao.entity.DemandeHistorique;
import ci.saydos.markazcompta.dao.entity.Utilisateur;
import ci.saydos.markazcompta.dao.entity.Demande;
import ci.saydos.markazcompta.dao.entity.*;
import ci.saydos.markazcompta.dao.repository.*;

/**
BUSINESS for table "demande_historique"
 * 
 * @author Geo
 *
 */
@Log
@Component
public class DemandeHistoriqueBusiness implements IBasicBusiness<Request<DemandeHistoriqueDto>, Response<DemandeHistoriqueDto>> {

	private Response<DemandeHistoriqueDto> response;
	@Autowired
	private DemandeHistoriqueRepository demandeHistoriqueRepository;
	@Autowired
	private UtilisateurRepository utilisateurRepository;
	@Autowired
	private DemandeRepository demandeRepository;
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

	public DemandeHistoriqueBusiness() {
		dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		dateTimeFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
	}
	
	/**
	 * create DemandeHistorique by using DemandeHistoriqueDto as object.
	 * 
	 * @param request
	 * @return response
	 * 
	 */
	@Override
	public Response<DemandeHistoriqueDto> create(Request<DemandeHistoriqueDto> request, Locale locale)  throws ParseException {
		log.info("----begin create DemandeHistorique-----");

		Response<DemandeHistoriqueDto> response = new Response<DemandeHistoriqueDto>();
		List<DemandeHistorique>        items    = new ArrayList<DemandeHistorique>();

		System.out.println(request.getDatas());
			
		for (DemandeHistoriqueDto dto : request.getDatas()) {
			// Definir les parametres obligatoires
			Map<String, java.lang.Object> fieldsToVerify = new HashMap<String, java.lang.Object>();
			fieldsToVerify.put("idDemande", dto.getIdDemande());
			fieldsToVerify.put("statut", dto.getStatut());
			if (!Validate.RequiredValue(fieldsToVerify).isGood()) {
				throw new InvalidEntityException(functionalError.FIELD_EMPTY(Validate.getValidate().getField(), locale));
			}

			// Verify if demandeHistorique to insert do not exist
			DemandeHistorique existingEntity = null;
			if (existingEntity != null) {
				throw new InternalErrorException(functionalError.DATA_EXIST("demandeHistorique id -> " + dto.getId(), locale));
			}

			// Verify if utilisateur exist
			Utilisateur existingUtilisateur = null;
			if (request.getUser() != null && request.getUser() > 0){
				existingUtilisateur = utilisateurRepository.findOne(request.getUser(), false);
				if (existingUtilisateur == null) {
					throw new EntityNotFoundException(functionalError.DATA_NOT_EXIST("utilisateur idUtilisateur -> " + request.getUser(), locale));
				}
			}
			// Verify if demande exist
			Demande existingDemande = null;
			if (dto.getIdDemande() != null && dto.getIdDemande() > 0){
				existingDemande = demandeRepository.findOne(dto.getIdDemande(), false);
				if (existingDemande == null) {
					throw new EntityNotFoundException(functionalError.DATA_NOT_EXIST("demande idDemande -> " + dto.getIdDemande(), locale));
				}
			}
			DemandeHistorique entityToSave = null;
			entityToSave = DemandeHistoriqueTransformer.INSTANCE.toEntity(dto, existingUtilisateur, existingDemande);
			entityToSave.setCreatedAt(Utilities.getCurrentDate());
			entityToSave.setCreatedBy(request.getUser());
			entityToSave.setIsDeleted(false);
			items.add(entityToSave);
		}

		if (!items.isEmpty()) {
			List<DemandeHistorique> itemsSaved = null;
			// inserer les donnees en base de donnees
			itemsSaved = demandeHistoriqueRepository.saveAll((Iterable<DemandeHistorique>) items);
			if (itemsSaved == null) {
				throw new InternalErrorException(functionalError.SAVE_FAIL("demandeHistorique", locale));
			}
			List<DemandeHistoriqueDto> itemsDto = (Utilities.isTrue(request.getIsSimpleLoading())) ? DemandeHistoriqueTransformer.INSTANCE.toLiteDtos(itemsSaved) : DemandeHistoriqueTransformer.INSTANCE.toDtos(itemsSaved);

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

		log.info("----end create DemandeHistorique-----");
		return response;
	}

	/**
	 * update DemandeHistorique by using DemandeHistoriqueDto as object.
	 * 
	 * @param request
	 * @return response
	 * 
	 */
	@Override
	public Response<DemandeHistoriqueDto> update(Request<DemandeHistoriqueDto> request, Locale locale)  throws ParseException {
		log.info("----begin update DemandeHistorique-----");

		Response<DemandeHistoriqueDto> response = new Response<DemandeHistoriqueDto>();
		List<DemandeHistorique>        items    = new ArrayList<DemandeHistorique>();
			
		for (DemandeHistoriqueDto dto : request.getDatas()) {
			// Definir les parametres obligatoires
			Map<String, java.lang.Object> fieldsToVerify = new HashMap<String, java.lang.Object>();
			fieldsToVerify.put("id", dto.getId());
			if (!Validate.RequiredValue(fieldsToVerify).isGood()) {
				throw new InvalidEntityException(functionalError.FIELD_EMPTY(Validate.getValidate().getField(), locale));
			}

			// Verifier si la demandeHistorique existe
			DemandeHistorique entityToSave = null;
			entityToSave = demandeHistoriqueRepository.findOne(dto.getId(), false);
			if (entityToSave == null) {
				throw new EntityNotFoundException(functionalError.DATA_NOT_EXIST("demandeHistorique id -> " + dto.getId(), locale));
			}

			// Verify if utilisateur exist
			if (dto.getIdUtilisateur() != null && dto.getIdUtilisateur() > 0){
				Utilisateur existingUtilisateur = utilisateurRepository.findOne(dto.getIdUtilisateur(), false);
				if (existingUtilisateur == null) {
					throw new EntityNotFoundException(functionalError.DATA_NOT_EXIST("utilisateur idUtilisateur -> " + dto.getIdUtilisateur(), locale));
				}
				entityToSave.setUtilisateur(existingUtilisateur);
			}
			// Verify if demande exist
			if (dto.getIdDemande() != null && dto.getIdDemande() > 0){
				Demande existingDemande = demandeRepository.findOne(dto.getIdDemande(), false);
				if (existingDemande == null) {
					throw new EntityNotFoundException(functionalError.DATA_NOT_EXIST("demande idDemande -> " + dto.getIdDemande(), locale));
				}
				entityToSave.setDemande(existingDemande);
			}
			if (Utilities.notBlank(dto.getStatut().toString())) {
				entityToSave.setStatut(dto.getStatut());
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
			List<DemandeHistorique> itemsSaved = null;
			// maj les donnees en base
			itemsSaved = demandeHistoriqueRepository.saveAll((Iterable<DemandeHistorique>) items);
			if (itemsSaved == null) {
				throw new InternalErrorException(functionalError.SAVE_FAIL("demandeHistorique", locale));
			}
			List<DemandeHistoriqueDto> itemsDto = (Utilities.isTrue(request.getIsSimpleLoading())) ? DemandeHistoriqueTransformer.INSTANCE.toLiteDtos(itemsSaved) : DemandeHistoriqueTransformer.INSTANCE.toDtos(itemsSaved);

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

		log.info("----end update DemandeHistorique-----");
		return response;
	}

	/**
	 * delete DemandeHistorique by using DemandeHistoriqueDto as object.
	 * 
	 * @param request
	 * @return response
	 * 
	 */
	@Override
	public Response<DemandeHistoriqueDto> delete(Request<DemandeHistoriqueDto> request, Locale locale)  {
		log.info("----begin delete DemandeHistorique-----");

		Response<DemandeHistoriqueDto> response = new Response<DemandeHistoriqueDto>();
		List<DemandeHistorique>        items    = new ArrayList<DemandeHistorique>();
			
		for (DemandeHistoriqueDto dto : request.getDatas()) {
			// Definir les parametres obligatoires
			Map<String, java.lang.Object> fieldsToVerify = new HashMap<String, java.lang.Object>();
			fieldsToVerify.put("id", dto.getId());
			if (!Validate.RequiredValue(fieldsToVerify).isGood()) {
				throw new InvalidEntityException(functionalError.FIELD_EMPTY(Validate.getValidate().getField(), locale));
			}

			// Verifier si la demandeHistorique existe
			DemandeHistorique existingEntity = null;
			existingEntity = demandeHistoriqueRepository.findOne(dto.getId(), false);
			if (existingEntity == null) {
				throw new EntityNotFoundException(functionalError.DATA_NOT_EXIST("demandeHistorique -> " + dto.getId(), locale));
			}

			// -----------------------------------------------------------------------
			// ----------- CHECK IF DATA IS USED
			// -----------------------------------------------------------------------



			existingEntity.setIsDeleted(true);
			items.add(existingEntity);
		}

		if (!items.isEmpty()) {
			// supprimer les donnees en base
			demandeHistoriqueRepository.saveAll((Iterable<DemandeHistorique>) items);

			Status status = new Status();
			status.setCode(StatusCode.SUCCESS);
			status.setMessage(StatusMessage.SUCCESS);

			response.setHasError(false);
			response.setHttpCode(HttpStatus.OK.value());
			response.setStatus(status);
		}

		log.info("----end delete DemandeHistorique-----");
		return response;
	}

	/**
	 * get DemandeHistorique by using DemandeHistoriqueDto as object.
	 * 
	 * @param request
	 * @return response
	 * 
	 */
	@Override
	public Response<DemandeHistoriqueDto> getByCriteria(Request<DemandeHistoriqueDto> request, Locale locale)  throws Exception {
		log.info("----begin get DemandeHistorique-----");

		Response<DemandeHistoriqueDto> response = new Response<DemandeHistoriqueDto>();
		List<DemandeHistorique> items 			 = demandeHistoriqueRepository.getByCriteria(request, em, locale);

		if (items != null && !items.isEmpty()) {
			List<DemandeHistoriqueDto> itemsDto = (Utilities.isTrue(request.getIsSimpleLoading())) ? DemandeHistoriqueTransformer.INSTANCE.toLiteDtos(items) : DemandeHistoriqueTransformer.INSTANCE.toDtos(items);

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
			response.setCount(demandeHistoriqueRepository.count(request, em, locale));
			response.setHasError(false);
		} else {
			response.setStatus(functionalError.DATA_EMPTY("demandeHistorique", locale));
			response.setHasError(false);
			return response;
		}

		log.info("----end get DemandeHistorique-----");
		return response;
	}

	/**
	 * get full DemandeHistoriqueDto by using DemandeHistorique as object.
	 * 
	 * @param dto
	 * @param size
	 * @param isSimpleLoading
	 * @param locale
	 * @return
	 * @throws Exception
	 */
	private DemandeHistoriqueDto getFullInfos(DemandeHistoriqueDto dto, Integer size, Boolean isSimpleLoading, Locale locale) throws Exception {
		// put code here

		if (Utilities.isTrue(isSimpleLoading)) {
			return dto;
		}
		if (size > 1) {
			return dto;
		}

		return dto;
	}

	public Response<DemandeHistoriqueDto> custom(Request<DemandeHistoriqueDto> request, Locale locale) {
		log.info("----begin custom DemandeHistoriqueDto-----");
		Response<DemandeHistoriqueDto> response = new Response<DemandeHistoriqueDto>();
		
		response.setHasError(false);
		response.setCount(1L);
		response.setItems(Arrays.asList(new DemandeHistoriqueDto()));
		log.info("----end custom DemandeHistoriqueDto-----");
		return response;
	}
}
