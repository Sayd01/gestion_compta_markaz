

/*
 * Java transformer for entity table role 
 * Created on 2023-08-06 ( Time 01:31:05 )
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
 * TRANSFORMER for table "role"
 * 
 * @author Geo
 *
 */
@Mapper
public interface RoleTransformer {

	RoleTransformer INSTANCE = Mappers.getMapper(RoleTransformer.class);

	@FullTransformerQualifier
	@Mappings({
		@Mapping(source="entity.createdAt", dateFormat="dd/MM/yyyy",target="createdAt"),
		@Mapping(source="entity.updatedAt", dateFormat="dd/MM/yyyy",target="updatedAt"),
	})
	RoleDto toDto(Role entity) throws ParseException;

	@IterableMapping(qualifiedBy = {FullTransformerQualifier.class})
    List<RoleDto> toDtos(List<Role> entities) throws ParseException;

    default RoleDto toLiteDto(Role entity) {
		if (entity == null) {
			return null;
		}
		RoleDto dto = new RoleDto();
		dto.setId( entity.getId() );
		dto.setName( entity.getName() );
		return dto;
    }

	default List<RoleDto> toLiteDtos(List<Role> entities) {
		if (entities == null || entities.stream().allMatch(o -> o == null)) {
			return null;
		}
		List<RoleDto> dtos = new ArrayList<RoleDto>();
		for (Role entity : entities) {
			dtos.add(toLiteDto(entity));
		}
		return dtos;
	}

	@Mappings({
		@Mapping(source="dto.id", target="id"),
		@Mapping(source="dto.name", target="name"),
		@Mapping(source="dto.createdAt", dateFormat="dd/MM/yyyy",target="createdAt"),
		@Mapping(source="dto.createdBy", target="createdBy"),
		@Mapping(source="dto.isDeleted", target="isDeleted"),
		@Mapping(source="dto.updatedAt", dateFormat="dd/MM/yyyy",target="updatedAt"),
		@Mapping(source="dto.updatedBy", target="updatedBy"),
	})
    Role toEntity(RoleDto dto) throws ParseException;

    //List<Role> toEntities(List<RoleDto> dtos) throws ParseException;

}
