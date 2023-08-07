                                        									
/*
 * Java business for entity table utilisateur_role 
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
import ci.saydos.markazcompta.dao.entity.UtilisateurRole;
import ci.saydos.markazcompta.dao.entity.Role;
import ci.saydos.markazcompta.dao.entity.Utilisateur;
import ci.saydos.markazcompta.dao.entity.*;
import ci.saydos.markazcompta.dao.repository.*;

/**
BUSINESS for table "utilisateur_role"
 * 
 * @author Geo
 *
 */
@Log
@Component
public class UtilisateurRoleBusiness implements IBasicBusiness<Request<UtilisateurRoleDto>, Response<UtilisateurRoleDto>> {

	private Response<UtilisateurRoleDto> response;
	@Autowired
	private UtilisateurRoleRepository utilisateurRoleRepository;
	@Autowired
	private RoleRepository roleRepository;
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

	public UtilisateurRoleBusiness() {
		dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		dateTimeFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
	}
	
	/**
	 * create UtilisateurRole by using UtilisateurRoleDto as object.
	 * 
	 * @param request
	 * @return response
	 * 
	 */
	@Override
	public Response<UtilisateurRoleDto> create(Request<UtilisateurRoleDto> request, Locale locale)  throws ParseException {
		log.info("----begin create UtilisateurRole-----");

		Response<UtilisateurRoleDto> response = new Response<UtilisateurRoleDto>();
		List<UtilisateurRole>        items    = new ArrayList<UtilisateurRole>();
			
		for (UtilisateurRoleDto dto : request.getDatas()) {
			// Definir les parametres obligatoires
			Map<String, java.lang.Object> fieldsToVerify = new HashMap<String, java.lang.Object>();
			fieldsToVerify.put("roleId", dto.getRoleId());
			fieldsToVerify.put("utilisateurId", dto.getUtilisateurId());
			if (!Validate.RequiredValue(fieldsToVerify).isGood()) {
				throw new InvalidEntityException(functionalError.FIELD_EMPTY(Validate.getValidate().getField(), locale));
			}

			// Verify if utilisateurRole to insert do not exist
			UtilisateurRole existingEntity = null;
			if (existingEntity != null) {
				throw new InternalErrorException(functionalError.DATA_EXIST("utilisateurRole id -> " + dto.getId(), locale));
			}

			// Verify if role exist
			Role existingRole = null;
			if (dto.getRoleId() != null && dto.getRoleId() > 0){
				existingRole = roleRepository.findOne(dto.getRoleId(), false);
				if (existingRole == null) {
					throw new EntityNotFoundException(functionalError.DATA_NOT_EXIST("role roleId -> " + dto.getRoleId(), locale));
				}
			}
			// Verify if utilisateur exist
			Utilisateur existingUtilisateur = null;
			if (dto.getUtilisateurId() != null && dto.getUtilisateurId() > 0){
				existingUtilisateur = utilisateurRepository.findOne(dto.getUtilisateurId(), false);
				if (existingUtilisateur == null) {
					throw new EntityNotFoundException(functionalError.DATA_NOT_EXIST("utilisateur utilisateurId -> " + dto.getUtilisateurId(), locale));
				}
			}
				UtilisateurRole entityToSave = null;
			entityToSave = UtilisateurRoleTransformer.INSTANCE.toEntity(dto, existingRole, existingUtilisateur);
			entityToSave.setCreatedAt(Utilities.getCurrentDate());
			entityToSave.setCreatedBy(request.getUser());
			entityToSave.setIsDeleted(false);
			items.add(entityToSave);
		}

		if (!items.isEmpty()) {
			List<UtilisateurRole> itemsSaved = null;
			// inserer les donnees en base de donnees
			itemsSaved = utilisateurRoleRepository.saveAll((Iterable<UtilisateurRole>) items);
			if (itemsSaved == null) {
				throw new InternalErrorException(functionalError.SAVE_FAIL("utilisateurRole", locale));
			}
			List<UtilisateurRoleDto> itemsDto = (Utilities.isTrue(request.getIsSimpleLoading())) ? UtilisateurRoleTransformer.INSTANCE.toLiteDtos(itemsSaved) : UtilisateurRoleTransformer.INSTANCE.toDtos(itemsSaved);

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

		log.info("----end create UtilisateurRole-----");
		return response;
	}

	/**
	 * update UtilisateurRole by using UtilisateurRoleDto as object.
	 * 
	 * @param request
	 * @return response
	 * 
	 */
	@Override
	public Response<UtilisateurRoleDto> update(Request<UtilisateurRoleDto> request, Locale locale)  throws ParseException {
		log.info("----begin update UtilisateurRole-----");

		Response<UtilisateurRoleDto> response = new Response<UtilisateurRoleDto>();
		List<UtilisateurRole>        items    = new ArrayList<UtilisateurRole>();
			
		for (UtilisateurRoleDto dto : request.getDatas()) {
			// Definir les parametres obligatoires
			Map<String, java.lang.Object> fieldsToVerify = new HashMap<String, java.lang.Object>();
			fieldsToVerify.put("id", dto.getId());
			if (!Validate.RequiredValue(fieldsToVerify).isGood()) {
				throw new InvalidEntityException(functionalError.FIELD_EMPTY(Validate.getValidate().getField(), locale));
			}

			// Verifier si la utilisateurRole existe
			UtilisateurRole entityToSave = null;
			entityToSave = utilisateurRoleRepository.findOne(dto.getId(), false);
			if (entityToSave == null) {
				throw new EntityNotFoundException(functionalError.DATA_NOT_EXIST("utilisateurRole id -> " + dto.getId(), locale));
			}

			// Verify if role exist
			if (dto.getRoleId() != null && dto.getRoleId() > 0){
				Role existingRole = roleRepository.findOne(dto.getRoleId(), false);
				if (existingRole == null) {
					throw new EntityNotFoundException(functionalError.DATA_NOT_EXIST("role roleId -> " + dto.getRoleId(), locale));
				}
				entityToSave.setRole(existingRole);
			}
			// Verify if utilisateur exist
			if (dto.getUtilisateurId() != null && dto.getUtilisateurId() > 0){
				Utilisateur existingUtilisateur = utilisateurRepository.findOne(dto.getUtilisateurId(), false);
				if (existingUtilisateur == null) {
					throw new EntityNotFoundException(functionalError.DATA_NOT_EXIST("utilisateur utilisateurId -> " + dto.getUtilisateurId(), locale));
				}
				entityToSave.setUtilisateur(existingUtilisateur);
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
			List<UtilisateurRole> itemsSaved = null;
			// maj les donnees en base
			itemsSaved = utilisateurRoleRepository.saveAll((Iterable<UtilisateurRole>) items);
			if (itemsSaved == null) {
				throw new InternalErrorException(functionalError.SAVE_FAIL("utilisateurRole", locale));
			}
			List<UtilisateurRoleDto> itemsDto = (Utilities.isTrue(request.getIsSimpleLoading())) ? UtilisateurRoleTransformer.INSTANCE.toLiteDtos(itemsSaved) : UtilisateurRoleTransformer.INSTANCE.toDtos(itemsSaved);

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

		log.info("----end update UtilisateurRole-----");
		return response;
	}

	/**
	 * delete UtilisateurRole by using UtilisateurRoleDto as object.
	 * 
	 * @param request
	 * @return response
	 * 
	 */
	@Override
	public Response<UtilisateurRoleDto> delete(Request<UtilisateurRoleDto> request, Locale locale)  {
		log.info("----begin delete UtilisateurRole-----");

		Response<UtilisateurRoleDto> response = new Response<UtilisateurRoleDto>();
		List<UtilisateurRole>        items    = new ArrayList<UtilisateurRole>();
			
		for (UtilisateurRoleDto dto : request.getDatas()) {
			// Definir les parametres obligatoires
			Map<String, java.lang.Object> fieldsToVerify = new HashMap<String, java.lang.Object>();
			fieldsToVerify.put("id", dto.getId());
			if (!Validate.RequiredValue(fieldsToVerify).isGood()) {
				throw new InvalidEntityException(functionalError.FIELD_EMPTY(Validate.getValidate().getField(), locale));
			}

			// Verifier si la utilisateurRole existe
			UtilisateurRole existingEntity = null;
			existingEntity = utilisateurRoleRepository.findOne(dto.getId(), false);
			if (existingEntity == null) {
				throw new EntityNotFoundException(functionalError.DATA_NOT_EXIST("utilisateurRole -> " + dto.getId(), locale));
			}

			// -----------------------------------------------------------------------
			// ----------- CHECK IF DATA IS USED
			// -----------------------------------------------------------------------



			existingEntity.setIsDeleted(true);
			items.add(existingEntity);
		}

		if (!items.isEmpty()) {
			// supprimer les donnees en base
			utilisateurRoleRepository.saveAll((Iterable<UtilisateurRole>) items);

			Status status = new Status();
			status.setCode(StatusCode.SUCCESS);
			status.setMessage(StatusMessage.SUCCESS);

			response.setHasError(false);
			response.setHttpCode(HttpStatus.OK.value());
			response.setStatus(status);
		}

		log.info("----end delete UtilisateurRole-----");
		return response;
	}

	/**
	 * get UtilisateurRole by using UtilisateurRoleDto as object.
	 * 
	 * @param request
	 * @return response
	 * 
	 */
	@Override
	public Response<UtilisateurRoleDto> getByCriteria(Request<UtilisateurRoleDto> request, Locale locale)  throws Exception {
		log.info("----begin get UtilisateurRole-----");

		Response<UtilisateurRoleDto> response = new Response<UtilisateurRoleDto>();
		List<UtilisateurRole> items 			 = utilisateurRoleRepository.getByCriteria(request, em, locale);

		if (items != null && !items.isEmpty()) {
			List<UtilisateurRoleDto> itemsDto = (Utilities.isTrue(request.getIsSimpleLoading())) ? UtilisateurRoleTransformer.INSTANCE.toLiteDtos(items) : UtilisateurRoleTransformer.INSTANCE.toDtos(items);

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
			response.setCount(utilisateurRoleRepository.count(request, em, locale));
			response.setHasError(false);
		} else {
			response.setStatus(functionalError.DATA_EMPTY("utilisateurRole", locale));
			response.setHasError(false);
			return response;
		}

		log.info("----end get UtilisateurRole-----");
		return response;
	}

	/**
	 * get full UtilisateurRoleDto by using UtilisateurRole as object.
	 * 
	 * @param dto
	 * @param size
	 * @param isSimpleLoading
	 * @param locale
	 * @return
	 * @throws Exception
	 */
	private UtilisateurRoleDto getFullInfos(UtilisateurRoleDto dto, Integer size, Boolean isSimpleLoading, Locale locale) throws Exception {
		// put code here

		if (Utilities.isTrue(isSimpleLoading)) {
			return dto;
		}
		if (size > 1) {
			return dto;
		}

		return dto;
	}

	public Response<UtilisateurRoleDto> custom(Request<UtilisateurRoleDto> request, Locale locale) {
		log.info("----begin custom UtilisateurRoleDto-----");
		Response<UtilisateurRoleDto> response = new Response<UtilisateurRoleDto>();
		
		response.setHasError(false);
		response.setCount(1L);
		response.setItems(Arrays.asList(new UtilisateurRoleDto()));
		log.info("----end custom UtilisateurRoleDto-----");
		return response;
	}
}
