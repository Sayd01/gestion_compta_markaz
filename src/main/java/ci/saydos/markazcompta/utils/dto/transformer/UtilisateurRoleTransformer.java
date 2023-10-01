

/*
 * Java transformer for entity table utilisateur_role 
 * Created on 2023-08-06 ( Time 01:31:06 )
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
 * TRANSFORMER for table "utilisateur_role"
 * 
 * @author Geo
 *
 */
@Mapper
public interface UtilisateurRoleTransformer {

	UtilisateurRoleTransformer INSTANCE = Mappers.getMapper(UtilisateurRoleTransformer.class);

	@FullTransformerQualifier
	@Mappings({
		@Mapping(source="entity.createdAt", dateFormat="dd/MM/yyyy",target="createdAt"),
		@Mapping(source="entity.updatedAt", dateFormat="dd/MM/yyyy",target="updatedAt"),
		@Mapping(source="entity.role.id", target="roleId"),
		@Mapping(source="entity.role.name", target="roleName"),
		@Mapping(source="entity.utilisateur.id", target="utilisateurId"),
		@Mapping(source="entity.utilisateur.firstName", target="utilisateurFirstName"),
		@Mapping(source="entity.utilisateur.lastName", target="utilisateurLastName"),
	})
	UtilisateurRoleDto toDto(UtilisateurRole entity) throws ParseException;

	@IterableMapping(qualifiedBy = {FullTransformerQualifier.class})
    List<UtilisateurRoleDto> toDtos(List<UtilisateurRole> entities) throws ParseException;

    default UtilisateurRoleDto toLiteDto(UtilisateurRole entity) {
		if (entity == null) {
			return null;
		}
		UtilisateurRoleDto dto = new UtilisateurRoleDto();
		dto.setId( entity.getId() );
		return dto;
    }

	default List<UtilisateurRoleDto> toLiteDtos(List<UtilisateurRole> entities) {
		if (entities == null || entities.stream().allMatch(o -> o == null)) {
			return null;
		}
		List<UtilisateurRoleDto> dtos = new ArrayList<UtilisateurRoleDto>();
		for (UtilisateurRole entity : entities) {
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
		@Mapping(source="role", target="role"),
		@Mapping(source="utilisateur", target="utilisateur"),
	})
    UtilisateurRole toEntity(UtilisateurRoleDto dto, Role role, Utilisateur utilisateur) throws ParseException;

    //List<UtilisateurRole> toEntities(List<UtilisateurRoleDto> dtos) throws ParseException;

}
