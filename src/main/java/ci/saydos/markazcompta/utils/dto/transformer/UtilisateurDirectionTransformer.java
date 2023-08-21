

/*
 * Java transformer for entity table utilisateur_direction 
 * Created on 2023-08-08 ( Time 19:02:57 )
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
 * TRANSFORMER for table "utilisateur_direction"
 * 
 * @author Geo
 *
 */
@Mapper
public interface UtilisateurDirectionTransformer {

	UtilisateurDirectionTransformer INSTANCE = Mappers.getMapper(UtilisateurDirectionTransformer.class);

	@FullTransformerQualifier
	@Mappings({
		@Mapping(source="entity.direction.id", target="idDirection"),
		@Mapping(source="entity.direction.code", target="directionCode"),
		@Mapping(source="entity.direction.intitule", target="directionIntitule"),
		@Mapping(source="entity.utilisateur.id", target="idUtilisateur"),
		@Mapping(source="entity.utilisateur.login", target="utilisateurLogin"),
		@Mapping(source="entity.utilisateur.firstName", target="utilisateurFirstName"),
		@Mapping(source="entity.utilisateur.lastName", target="utilisateurLastName"),
	})
	UtilisateurDirectionDto toDto(UtilisateurDirection entity) throws ParseException;

	@IterableMapping(qualifiedBy = {FullTransformerQualifier.class})
    List<UtilisateurDirectionDto> toDtos(List<UtilisateurDirection> entities) throws ParseException;

    default UtilisateurDirectionDto toLiteDto(UtilisateurDirection entity) {
		if (entity == null) {
			return null;
		}
		UtilisateurDirectionDto dto = new UtilisateurDirectionDto();
		dto.setId( entity.getId() );
		return dto;
    }

	default List<UtilisateurDirectionDto> toLiteDtos(List<UtilisateurDirection> entities) {
		if (entities == null || entities.stream().allMatch(o -> o == null)) {
			return null;
		}
		List<UtilisateurDirectionDto> dtos = new ArrayList<UtilisateurDirectionDto>();
		for (UtilisateurDirection entity : entities) {
			dtos.add(toLiteDto(entity));
		}
		return dtos;
	}

	@Mappings({
		@Mapping(source="dto.id", target="id"),
		@Mapping(source="direction", target="direction"),
		@Mapping(source="utilisateur", target="utilisateur"),
	})
    UtilisateurDirection toEntity(UtilisateurDirectionDto dto, Direction direction, Utilisateur utilisateur) throws ParseException;

    //List<UtilisateurDirection> toEntities(List<UtilisateurDirectionDto> dtos) throws ParseException;

}
