                                        									
/*
 * Java business for entity table role_permission 
 * Created on 2023-10-07 ( Time 23:33:42 )
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
import ci.saydos.markazcompta.dao.entity.RolePermission;
import ci.saydos.markazcompta.dao.entity.Permission;
import ci.saydos.markazcompta.dao.entity.Role;
import ci.saydos.markazcompta.dao.entity.*;
import ci.saydos.markazcompta.dao.repository.*;

/**
BUSINESS for table "role_permission"
 * 
 * @author Geo
 *
 */
@Log
@Component
public class RolePermissionBusiness implements IBasicBusiness<Request<RolePermissionDto>, Response<RolePermissionDto>> {

	private Response<RolePermissionDto> response;
	@Autowired
	private RolePermissionRepository rolePermissionRepository;
	@Autowired
	private PermissionRepository permissionRepository;
	@Autowired
	private RoleRepository roleRepository;
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

	public RolePermissionBusiness() {
		dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		dateTimeFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
	}
	
	/**
	 * create RolePermission by using RolePermissionDto as object.
	 * 
	 * @param request
	 * @return response
	 * 
	 */
	@Override
	public Response<RolePermissionDto> create(Request<RolePermissionDto> request, Locale locale)  throws ParseException {
		log.info("----begin create RolePermission-----");

		Response<RolePermissionDto> response = new Response<RolePermissionDto>();
		List<RolePermission>        items    = new ArrayList<RolePermission>();
			
		for (RolePermissionDto dto : request.getDatas()) {
			// Definir les parametres obligatoires
			Map<String, java.lang.Object> fieldsToVerify = new HashMap<String, java.lang.Object>();
			fieldsToVerify.put("roleId", dto.getRoleId());
			fieldsToVerify.put("permissionId", dto.getPermissionId());
			if (!Validate.RequiredValue(fieldsToVerify).isGood()) {
				throw new InvalidEntityException(functionalError.FIELD_EMPTY(Validate.getValidate().getField(), locale));
			}

			// Verify if rolePermission to insert do not exist
			RolePermission existingEntity = null;
			if (existingEntity != null) {
				throw new InternalErrorException(functionalError.DATA_EXIST("rolePermission id -> " + dto.getId(), locale));
			}

			// Verify if permission exist
			Permission existingPermission = null;
			if (dto.getPermissionId() != null && dto.getPermissionId() > 0){
				existingPermission = permissionRepository.findOne(dto.getPermissionId(), false);
				if (existingPermission == null) {
					throw new EntityNotFoundException(functionalError.DATA_NOT_EXIST("permission permissionId -> " + dto.getPermissionId(), locale));
				}
			}
			// Verify if role exist
			Role existingRole = null;
			if (dto.getRoleId() != null && dto.getRoleId() > 0){
				existingRole = roleRepository.findOne(dto.getRoleId(), false);
				if (existingRole == null) {
					throw new EntityNotFoundException(functionalError.DATA_NOT_EXIST("role roleId -> " + dto.getRoleId(), locale));
				}
			}
				RolePermission entityToSave = null;
			entityToSave = RolePermissionTransformer.INSTANCE.toEntity(dto, existingPermission, existingRole);
			entityToSave.setCreatedAt(Utilities.getCurrentDate());
			entityToSave.setCreatedBy(request.getUser());
			entityToSave.setIsDeleted(false);
			items.add(entityToSave);
		}

		if (!items.isEmpty()) {
			List<RolePermission> itemsSaved = null;
			// inserer les donnees en base de donnees
			itemsSaved = rolePermissionRepository.saveAll((Iterable<RolePermission>) items);
			if (itemsSaved == null) {
				throw new InternalErrorException(functionalError.SAVE_FAIL("rolePermission", locale));
			}
			List<RolePermissionDto> itemsDto = (Utilities.isTrue(request.getIsSimpleLoading())) ? RolePermissionTransformer.INSTANCE.toLiteDtos(itemsSaved) : RolePermissionTransformer.INSTANCE.toDtos(itemsSaved);

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

		log.info("----end create RolePermission-----");
		return response;
	}

	/**
	 * update RolePermission by using RolePermissionDto as object.
	 * 
	 * @param request
	 * @return response
	 * 
	 */
	@Override
	public Response<RolePermissionDto> update(Request<RolePermissionDto> request, Locale locale)  throws ParseException {
		log.info("----begin update RolePermission-----");

		Response<RolePermissionDto> response = new Response<RolePermissionDto>();
		List<RolePermission>        items    = new ArrayList<RolePermission>();
			
		for (RolePermissionDto dto : request.getDatas()) {
			// Definir les parametres obligatoires
			Map<String, java.lang.Object> fieldsToVerify = new HashMap<String, java.lang.Object>();
			fieldsToVerify.put("id", dto.getId());
			if (!Validate.RequiredValue(fieldsToVerify).isGood()) {
				throw new InvalidEntityException(functionalError.FIELD_EMPTY(Validate.getValidate().getField(), locale));
			}

			// Verifier si la rolePermission existe
			RolePermission entityToSave = null;
			entityToSave = rolePermissionRepository.findOne(dto.getId(), false);
			if (entityToSave == null) {
				throw new EntityNotFoundException(functionalError.DATA_NOT_EXIST("rolePermission id -> " + dto.getId(), locale));
			}

			// Verify if permission exist
			if (dto.getPermissionId() != null && dto.getPermissionId() > 0){
				Permission existingPermission = permissionRepository.findOne(dto.getPermissionId(), false);
				if (existingPermission == null) {
					throw new EntityNotFoundException(functionalError.DATA_NOT_EXIST("permission permissionId -> " + dto.getPermissionId(), locale));
				}
				entityToSave.setPermission(existingPermission);
			}
			// Verify if role exist
			if (dto.getRoleId() != null && dto.getRoleId() > 0){
				Role existingRole = roleRepository.findOne(dto.getRoleId(), false);
				if (existingRole == null) {
					throw new EntityNotFoundException(functionalError.DATA_NOT_EXIST("role roleId -> " + dto.getRoleId(), locale));
				}
				entityToSave.setRole(existingRole);
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
			List<RolePermission> itemsSaved = null;
			// maj les donnees en base
			itemsSaved = rolePermissionRepository.saveAll((Iterable<RolePermission>) items);
			if (itemsSaved == null) {
				throw new InternalErrorException(functionalError.SAVE_FAIL("rolePermission", locale));
			}
			List<RolePermissionDto> itemsDto = (Utilities.isTrue(request.getIsSimpleLoading())) ? RolePermissionTransformer.INSTANCE.toLiteDtos(itemsSaved) : RolePermissionTransformer.INSTANCE.toDtos(itemsSaved);

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

		log.info("----end update RolePermission-----");
		return response;
	}

	/**
	 * delete RolePermission by using RolePermissionDto as object.
	 * 
	 * @param request
	 * @return response
	 * 
	 */
	@Override
	public Response<RolePermissionDto> delete(Request<RolePermissionDto> request, Locale locale)  {
		log.info("----begin delete RolePermission-----");

		Response<RolePermissionDto> response = new Response<RolePermissionDto>();
		List<RolePermission>        items    = new ArrayList<RolePermission>();
			
		for (RolePermissionDto dto : request.getDatas()) {
			// Definir les parametres obligatoires
			Map<String, java.lang.Object> fieldsToVerify = new HashMap<String, java.lang.Object>();
			fieldsToVerify.put("id", dto.getId());
			if (!Validate.RequiredValue(fieldsToVerify).isGood()) {
				throw new InvalidEntityException(functionalError.FIELD_EMPTY(Validate.getValidate().getField(), locale));
			}

			// Verifier si la rolePermission existe
			RolePermission existingEntity = null;
			existingEntity = rolePermissionRepository.findOne(dto.getId(), false);
			if (existingEntity == null) {
				throw new EntityNotFoundException(functionalError.DATA_NOT_EXIST("rolePermission -> " + dto.getId(), locale));
			}

			// -----------------------------------------------------------------------
			// ----------- CHECK IF DATA IS USED
			// -----------------------------------------------------------------------



			existingEntity.setIsDeleted(true);
			items.add(existingEntity);
		}

		if (!items.isEmpty()) {
			// supprimer les donnees en base
			rolePermissionRepository.saveAll((Iterable<RolePermission>) items);

			Status status = new Status();
			status.setCode(StatusCode.SUCCESS);
			status.setMessage(StatusMessage.SUCCESS);

			response.setHasError(false);
			response.setHttpCode(HttpStatus.OK.value());
			response.setStatus(status);
		}

		log.info("----end delete RolePermission-----");
		return response;
	}

	/**
	 * get RolePermission by using RolePermissionDto as object.
	 * 
	 * @param request
	 * @return response
	 * 
	 */
	@Override
	public Response<RolePermissionDto> getByCriteria(Request<RolePermissionDto> request, Locale locale)  throws Exception {
		log.info("----begin get RolePermission-----");

		Response<RolePermissionDto> response = new Response<RolePermissionDto>();
		List<RolePermission> items 			 = rolePermissionRepository.getByCriteria(request, em, locale);

		if (items != null && !items.isEmpty()) {
			List<RolePermissionDto> itemsDto = (Utilities.isTrue(request.getIsSimpleLoading())) ? RolePermissionTransformer.INSTANCE.toLiteDtos(items) : RolePermissionTransformer.INSTANCE.toDtos(items);

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
			response.setCount(rolePermissionRepository.count(request, em, locale));
			response.setHasError(false);
		} else {
			response.setStatus(functionalError.DATA_EMPTY("rolePermission", locale));
			response.setHasError(false);
			return response;
		}

		log.info("----end get RolePermission-----");
		return response;
	}

	/**
	 * get full RolePermissionDto by using RolePermission as object.
	 * 
	 * @param dto
	 * @param size
	 * @param isSimpleLoading
	 * @param locale
	 * @return
	 * @throws Exception
	 */
	private RolePermissionDto getFullInfos(RolePermissionDto dto, Integer size, Boolean isSimpleLoading, Locale locale) throws Exception {
		// put code here

		if (Utilities.isTrue(isSimpleLoading)) {
			return dto;
		}
		if (size > 1) {
			return dto;
		}

		return dto;
	}

	public Response<RolePermissionDto> custom(Request<RolePermissionDto> request, Locale locale) {
		log.info("----begin custom RolePermissionDto-----");
		Response<RolePermissionDto> response = new Response<RolePermissionDto>();
		
		response.setHasError(false);
		response.setCount(1L);
		response.setItems(Arrays.asList(new RolePermissionDto()));
		log.info("----end custom RolePermissionDto-----");
		return response;
	}
}
