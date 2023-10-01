

/*
 * Java transformer for entity table utilisateur 
 * Created on 2023-08-08 ( Time 19:02:56 )
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
 * TRANSFORMER for table "utilisateur"
 * 
 * @author Geo
 *
 */
@Mapper
public interface UtilisateurTransformer {

	UtilisateurTransformer INSTANCE = Mappers.getMapper(UtilisateurTransformer.class);

	@FullTransformerQualifier
	@Mappings({
		@Mapping(source="entity.createdAt", dateFormat="dd/MM/yyyy",target="createdAt"),
		@Mapping(source="entity.updatedAt", dateFormat="dd/MM/yyyy",target="updatedAt"),
	})
	UtilisateurDto toDto(Utilisateur entity) throws ParseException;

	@IterableMapping(qualifiedBy = {FullTransformerQualifier.class})
    List<UtilisateurDto> toDtos(List<Utilisateur> entities) throws ParseException;

    default UtilisateurDto toLiteDto(Utilisateur entity) {
		if (entity == null) {
			return null;
		}
		UtilisateurDto dto = new UtilisateurDto();
		dto.setId( entity.getId() );
		dto.setFirstName( entity.getFirstName() );
		dto.setLastName( entity.getLastName() );
		return dto;
    }

	default List<UtilisateurDto> toLiteDtos(List<Utilisateur> entities) {
		if (entities == null || entities.stream().allMatch(o -> o == null)) {
			return null;
		}
		List<UtilisateurDto> dtos = new ArrayList<UtilisateurDto>();
		for (Utilisateur entity : entities) {
			dtos.add(toLiteDto(entity));
		}
		return dtos;
	}

	@Mappings({
		@Mapping(source="dto.id", target="id"),
		@Mapping(source="dto.firstName", target="firstName"),
		@Mapping(source="dto.lastName", target="lastName"),
		@Mapping(source="dto.email", target="email"),
		@Mapping(source="dto.imageUrl", target="imageUrl"),
		@Mapping(source="dto.createdAt", dateFormat="dd/MM/yyyy",target="createdAt"),
		@Mapping(source="dto.createdBy", target="createdBy"),
		@Mapping(source="dto.isDeleted", target="isDeleted"),
		@Mapping(source="dto.updatedAt", dateFormat="dd/MM/yyyy",target="updatedAt"),
		@Mapping(source="dto.updatedBy", target="updatedBy"),
		@Mapping(source="dto.password", target="password"),
	})
    Utilisateur toEntity(UtilisateurDto dto) throws ParseException;

    //List<Utilisateur> toEntities(List<UtilisateurDto> dtos) throws ParseException;

}
