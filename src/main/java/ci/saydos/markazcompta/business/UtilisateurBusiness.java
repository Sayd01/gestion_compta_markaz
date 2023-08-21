                                                            														
/*
 * Java business for entity table utilisateur 
 * Created on 2023-08-08 ( Time 19:02:56 )
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
import ci.saydos.markazcompta.dao.entity.Utilisateur;
import ci.saydos.markazcompta.dao.entity.Markaz;
import ci.saydos.markazcompta.dao.entity.*;
import ci.saydos.markazcompta.dao.repository.*;

/**
BUSINESS for table "utilisateur"
 * 
 * @author Geo
 *
 */
@Log
@Component
public class UtilisateurBusiness implements IBasicBusiness<Request<UtilisateurDto>, Response<UtilisateurDto>> {

	private Response<UtilisateurDto> response;
	@Autowired
	private UtilisateurRepository utilisateurRepository;
	@Autowired
	private DepenseRepository depenseRepository;
	@Autowired
	private DemandeHistoriqueRepository demandeHistoriqueRepository;
	@Autowired
	private UtilisateurRoleRepository utilisateurRoleRepository;
	@Autowired
	private CaisseRepository caisseRepository;
	@Autowired
	private DemandeRepository demandeRepository;
	@Autowired
	private UtilisateurDirectionRepository utilisateurDirectionRepository;
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

	public UtilisateurBusiness() {
		dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		dateTimeFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
	}
	
	/**
	 * create Utilisateur by using UtilisateurDto as object.
	 * 
	 * @param request
	 * @return response
	 * 
	 */
	@Override
	public Response<UtilisateurDto> create(Request<UtilisateurDto> request, Locale locale)  throws ParseException {
		log.info("----begin create Utilisateur-----");

		Response<UtilisateurDto> response = new Response<UtilisateurDto>();
		List<Utilisateur>        items    = new ArrayList<Utilisateur>();
			
		for (UtilisateurDto dto : request.getDatas()) {
			// Definir les parametres obligatoires
			Map<String, java.lang.Object> fieldsToVerify = new HashMap<String, java.lang.Object>();
			fieldsToVerify.put("login", dto.getLogin());
			fieldsToVerify.put("firstName", dto.getFirstName());
			fieldsToVerify.put("lastName", dto.getLastName());
			fieldsToVerify.put("email", dto.getEmail());
			fieldsToVerify.put("imageUrl", dto.getImageUrl());
			fieldsToVerify.put("idMarkaz", dto.getIdMarkaz());
			fieldsToVerify.put("password", dto.getPassword());
			if (!Validate.RequiredValue(fieldsToVerify).isGood()) {
				throw new InvalidEntityException(functionalError.FIELD_EMPTY(Validate.getValidate().getField(), locale));
			}

			// Verify if utilisateur to insert do not exist
			Utilisateur existingEntity = null;
			if (existingEntity != null) {
				throw new InternalErrorException(functionalError.DATA_EXIST("utilisateur id -> " + dto.getId(), locale));
			}

			// verif unique login in db
			existingEntity = utilisateurRepository.findByLogin(dto.getLogin(), false);
			if (existingEntity != null) {
				throw new InternalErrorException(functionalError.DATA_EXIST("utilisateur login -> " + dto.getLogin(), locale));
			}
			// verif unique login in items to save
			if (items.stream().anyMatch(a -> a.getLogin().equalsIgnoreCase(dto.getLogin()))) {
				throw new InternalErrorException(functionalError.DATA_DUPLICATE(" login ", locale));
			}

			// verif unique email in db
			existingEntity = utilisateurRepository.findByEmail(dto.getEmail(), false);
			if (existingEntity != null) {
				throw new InternalErrorException(functionalError.DATA_EXIST("utilisateur email -> " + dto.getEmail(), locale));
			}
			// verif unique email in items to save
			if (items.stream().anyMatch(a -> a.getEmail().equalsIgnoreCase(dto.getEmail()))) {
				throw new InternalErrorException(functionalError.DATA_DUPLICATE(" email ", locale));
			}

			// Verify if markaz exist
			Markaz existingMarkaz = null;
			if (dto.getIdMarkaz() != null && dto.getIdMarkaz() > 0){
				existingMarkaz = markazRepository.findOne(dto.getIdMarkaz(), false);
				if (existingMarkaz == null) {
					throw new EntityNotFoundException(functionalError.DATA_NOT_EXIST("markaz idMarkaz -> " + dto.getIdMarkaz(), locale));
				}
			}
				Utilisateur entityToSave = null;
			entityToSave = UtilisateurTransformer.INSTANCE.toEntity(dto, existingMarkaz);
			entityToSave.setCreatedAt(Utilities.getCurrentDate());
			entityToSave.setCreatedBy(request.getUser());
			entityToSave.setIsDeleted(false);
			items.add(entityToSave);
		}

		if (!items.isEmpty()) {
			List<Utilisateur> itemsSaved = null;
			// inserer les donnees en base de donnees
			itemsSaved = utilisateurRepository.saveAll((Iterable<Utilisateur>) items);
			if (itemsSaved == null) {
				throw new InternalErrorException(functionalError.SAVE_FAIL("utilisateur", locale));
			}
			List<UtilisateurDto> itemsDto = (Utilities.isTrue(request.getIsSimpleLoading())) ? UtilisateurTransformer.INSTANCE.toLiteDtos(itemsSaved) : UtilisateurTransformer.INSTANCE.toDtos(itemsSaved);

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

		log.info("----end create Utilisateur-----");
		return response;
	}

	/**
	 * update Utilisateur by using UtilisateurDto as object.
	 * 
	 * @param request
	 * @return response
	 * 
	 */
	@Override
	public Response<UtilisateurDto> update(Request<UtilisateurDto> request, Locale locale)  throws ParseException {
		log.info("----begin update Utilisateur-----");

		Response<UtilisateurDto> response = new Response<UtilisateurDto>();
		List<Utilisateur>        items    = new ArrayList<Utilisateur>();
			
		for (UtilisateurDto dto : request.getDatas()) {
			// Definir les parametres obligatoires
			Map<String, java.lang.Object> fieldsToVerify = new HashMap<String, java.lang.Object>();
			fieldsToVerify.put("id", dto.getId());
			if (!Validate.RequiredValue(fieldsToVerify).isGood()) {
				throw new InvalidEntityException(functionalError.FIELD_EMPTY(Validate.getValidate().getField(), locale));
			}

			// Verifier si la utilisateur existe
			Utilisateur entityToSave = null;
			entityToSave = utilisateurRepository.findOne(dto.getId(), false);
			if (entityToSave == null) {
				throw new EntityNotFoundException(functionalError.DATA_NOT_EXIST("utilisateur id -> " + dto.getId(), locale));
			}

			// Verify if markaz exist
			if (dto.getIdMarkaz() != null && dto.getIdMarkaz() > 0){
				Markaz existingMarkaz = markazRepository.findOne(dto.getIdMarkaz(), false);
				if (existingMarkaz == null) {
					throw new EntityNotFoundException(functionalError.DATA_NOT_EXIST("markaz idMarkaz -> " + dto.getIdMarkaz(), locale));
				}
				entityToSave.setMarkaz(existingMarkaz);
			}
			if (Utilities.notBlank(dto.getLogin())) {
				entityToSave.setLogin(dto.getLogin());
			}
			if (Utilities.notBlank(dto.getFirstName())) {
				entityToSave.setFirstName(dto.getFirstName());
			}
			if (Utilities.notBlank(dto.getLastName())) {
				entityToSave.setLastName(dto.getLastName());
			}
			if (Utilities.notBlank(dto.getEmail())) {
				entityToSave.setEmail(dto.getEmail());
			}
			if (Utilities.notBlank(dto.getImageUrl())) {
				entityToSave.setImageUrl(dto.getImageUrl());
			}
			if (dto.getCreatedBy() != null && dto.getCreatedBy() > 0) {
				entityToSave.setCreatedBy(dto.getCreatedBy());
			}
			if (dto.getUpdatedBy() != null && dto.getUpdatedBy() > 0) {
				entityToSave.setUpdatedBy(dto.getUpdatedBy());
			}
			if (Utilities.notBlank(dto.getPassword())) {
				entityToSave.setPassword(dto.getPassword());
			}
			entityToSave.setUpdatedAt(Utilities.getCurrentDate());
			entityToSave.setUpdatedBy(request.getUser());
			items.add(entityToSave);
		}

		if (!items.isEmpty()) {
			List<Utilisateur> itemsSaved = null;
			// maj les donnees en base
			itemsSaved = utilisateurRepository.saveAll((Iterable<Utilisateur>) items);
			if (itemsSaved == null) {
				throw new InternalErrorException(functionalError.SAVE_FAIL("utilisateur", locale));
			}
			List<UtilisateurDto> itemsDto = (Utilities.isTrue(request.getIsSimpleLoading())) ? UtilisateurTransformer.INSTANCE.toLiteDtos(itemsSaved) : UtilisateurTransformer.INSTANCE.toDtos(itemsSaved);

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

		log.info("----end update Utilisateur-----");
		return response;
	}

	/**
	 * delete Utilisateur by using UtilisateurDto as object.
	 * 
	 * @param request
	 * @return response
	 * 
	 */
	@Override
	public Response<UtilisateurDto> delete(Request<UtilisateurDto> request, Locale locale)  {
		log.info("----begin delete Utilisateur-----");

		Response<UtilisateurDto> response = new Response<UtilisateurDto>();
		List<Utilisateur>        items    = new ArrayList<Utilisateur>();
			
		for (UtilisateurDto dto : request.getDatas()) {
			// Definir les parametres obligatoires
			Map<String, java.lang.Object> fieldsToVerify = new HashMap<String, java.lang.Object>();
			fieldsToVerify.put("id", dto.getId());
			if (!Validate.RequiredValue(fieldsToVerify).isGood()) {
				throw new InvalidEntityException(functionalError.FIELD_EMPTY(Validate.getValidate().getField(), locale));
			}

			// Verifier si la utilisateur existe
			Utilisateur existingEntity = null;
			existingEntity = utilisateurRepository.findOne(dto.getId(), false);
			if (existingEntity == null) {
				throw new EntityNotFoundException(functionalError.DATA_NOT_EXIST("utilisateur -> " + dto.getId(), locale));
			}

			// -----------------------------------------------------------------------
			// ----------- CHECK IF DATA IS USED
			// -----------------------------------------------------------------------

			// depense
			List<Depense> listOfDepense = depenseRepository.findByIdChargeFixe(existingEntity.getId(), false);
			if (listOfDepense != null && !listOfDepense.isEmpty()){
				throw new InternalErrorException(functionalError.DATA_NOT_DELETABLE("(" + listOfDepense.size() + ")", locale));
			}
			// demandeHistorique
			List<DemandeHistorique> listOfDemandeHistorique = demandeHistoriqueRepository.findByIdUtilisateur(existingEntity.getId(), false);
			if (listOfDemandeHistorique != null && !listOfDemandeHistorique.isEmpty()){
				throw new InternalErrorException(functionalError.DATA_NOT_DELETABLE("(" + listOfDemandeHistorique.size() + ")", locale));
			}
			// utilisateurRole
			List<UtilisateurRole> listOfUtilisateurRole = utilisateurRoleRepository.findByUtilisateurId(existingEntity.getId(), false);
			if (listOfUtilisateurRole != null && !listOfUtilisateurRole.isEmpty()){
				throw new InternalErrorException(functionalError.DATA_NOT_DELETABLE("(" + listOfUtilisateurRole.size() + ")", locale));
			}
			// caisse
			List<Caisse> listOfCaisse = caisseRepository.findByIdUtilisateur(existingEntity.getId(), false);
			if (listOfCaisse != null && !listOfCaisse.isEmpty()){
				throw new InternalErrorException(functionalError.DATA_NOT_DELETABLE("(" + listOfCaisse.size() + ")", locale));
			}
			// demande
			List<Demande> listOfDemande = demandeRepository.findByIdUtilisateur(existingEntity.getId(), false);
			if (listOfDemande != null && !listOfDemande.isEmpty()){
				throw new InternalErrorException(functionalError.DATA_NOT_DELETABLE("(" + listOfDemande.size() + ")", locale));
			}
			// utilisateurDirection
			List<UtilisateurDirection> listOfUtilisateurDirection = utilisateurDirectionRepository.findByIdUtilisateur(existingEntity.getId());
			if (listOfUtilisateurDirection != null && !listOfUtilisateurDirection.isEmpty()){
				throw new InternalErrorException(functionalError.DATA_NOT_DELETABLE("(" + listOfUtilisateurDirection.size() + ")", locale));
			}


			existingEntity.setIsDeleted(true);
			items.add(existingEntity);
		}

		if (!items.isEmpty()) {
			// supprimer les donnees en base
			utilisateurRepository.saveAll((Iterable<Utilisateur>) items);

			Status status = new Status();
			status.setCode(StatusCode.SUCCESS);
			status.setMessage(StatusMessage.SUCCESS);

			response.setHasError(false);
			response.setHttpCode(HttpStatus.OK.value());
			response.setStatus(status);
		}

		log.info("----end delete Utilisateur-----");
		return response;
	}

	/**
	 * get Utilisateur by using UtilisateurDto as object.
	 * 
	 * @param request
	 * @return response
	 * 
	 */
	@Override
	public Response<UtilisateurDto> getByCriteria(Request<UtilisateurDto> request, Locale locale)  throws Exception {
		log.info("----begin get Utilisateur-----");

		Response<UtilisateurDto> response = new Response<UtilisateurDto>();
		List<Utilisateur> items 			 = utilisateurRepository.getByCriteria(request, em, locale);

		if (items != null && !items.isEmpty()) {
			List<UtilisateurDto> itemsDto = (Utilities.isTrue(request.getIsSimpleLoading())) ? UtilisateurTransformer.INSTANCE.toLiteDtos(items) : UtilisateurTransformer.INSTANCE.toDtos(items);

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
			response.setCount(utilisateurRepository.count(request, em, locale));
			response.setHasError(false);
		} else {
			response.setStatus(functionalError.DATA_EMPTY("utilisateur", locale));
			response.setHasError(false);
			return response;
		}

		log.info("----end get Utilisateur-----");
		return response;
	}

	/**
	 * get full UtilisateurDto by using Utilisateur as object.
	 * 
	 * @param dto
	 * @param size
	 * @param isSimpleLoading
	 * @param locale
	 * @return
	 * @throws Exception
	 */
	private UtilisateurDto getFullInfos(UtilisateurDto dto, Integer size, Boolean isSimpleLoading, Locale locale) throws Exception {
		// put code here

		if (Utilities.isTrue(isSimpleLoading)) {
			return dto;
		}
		if (size > 1) {
			return dto;
		}

		return dto;
	}

	public Response<UtilisateurDto> custom(Request<UtilisateurDto> request, Locale locale) {
		log.info("----begin custom UtilisateurDto-----");
		Response<UtilisateurDto> response = new Response<UtilisateurDto>();
		
		response.setHasError(false);
		response.setCount(1L);
		response.setItems(Arrays.asList(new UtilisateurDto()));
		log.info("----end custom UtilisateurDto-----");
		return response;
	}
}
