

/*
 * Java transformer for entity table role_permission 
 * Created on 2023-10-07 ( Time 23:33:42 )
 * Generator tool : Telosys Tools Generator ( version 3.3.0 )
 * Copyright 2018 Geo. All Rights Reserved.
 */

package ci.saydos.markazcompta.utils.dto.transformer;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import ci.saydos.markazcompta.utils.contract.*;
import ci.saydos.markazcompta.utils.dto.*;
import ci.saydos.markazcompta.dao.entity.*;


/**
 * TRANSFORMER for table "role_permission"
 * 
 * @author Geo
 *
 */
@Mapper
public interface RolePermissionTransformer {

	RolePermissionTransformer INSTANCE = Mappers.getMapper(RolePermissionTransformer.class);

	@FullTransformerQualifier
	@Mappings({
		@Mapping(source="entity.createdAt", dateFormat="dd/MM/yyyy",target="createdAt"),
		@Mapping(source="entity.updatedAt", dateFormat="dd/MM/yyyy",target="updatedAt"),
		@Mapping(source="entity.permission.id", target="permissionId"),
		@Mapping(source="entity.permission.name", target="permissionName"),
		@Mapping(source="entity.role.id", target="roleId"),
		@Mapping(source="entity.role.name", target="roleName"),
	})
	RolePermissionDto toDto(RolePermission entity) throws ParseException;

	@IterableMapping(qualifiedBy = {FullTransformerQualifier.class})
    List<RolePermissionDto> toDtos(List<RolePermission> entities) throws ParseException;

    default RolePermissionDto toLiteDto(RolePermission entity) {
		if (entity == null) {
			return null;
		}
		RolePermissionDto dto = new RolePermissionDto();
		dto.setId( entity.getId() );
		return dto;
    }

	default List<RolePermissionDto> toLiteDtos(List<RolePermission> entities) {
		if (entities == null || entities.stream().allMatch(o -> o == null)) {
			return null;
		}
		List<RolePermissionDto> dtos = new ArrayList<RolePermissionDto>();
		for (RolePermission entity : entities) {
			dtos.add(toLiteDto(entity));
		}
		return dtos;
	}

	@Mappings({
		@Mapping(source="dto.id", target="id"),
		@Mapping(source="dto.createdAt", dateFormat="dd/MM/yyyy",target="createdAt"),
		@Mapping(source="dto.createdBy", target="createdBy"),
		@Mapping(source="dto.isDeleted", target="isDeleted"),
		@Mapping(source="dto.updatedAt", dateFormat="dd/MM/yyyy",target="updatedAt"),
		@Mapping(source="dto.updatedBy", target="updatedBy"),
		@Mapping(source="permission", target="permission"),
		@Mapping(source="role", target="role"),
	})
    RolePermission toEntity(RolePermissionDto dto, Permission permission, Role role) throws ParseException;

    //List<RolePermission> toEntities(List<RolePermissionDto> dtos) throws ParseException;

}
