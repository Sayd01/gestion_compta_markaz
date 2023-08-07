

/*
 * Java transformer for entity table recette 
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
 * TRANSFORMER for table "recette"
 * 
 * @author Geo
 *
 */
@Mapper
public interface RecetteTransformer {

	RecetteTransformer INSTANCE = Mappers.getMapper(RecetteTransformer.class);

	@FullTransformerQualifier
	@Mappings({
		@Mapping(source="entity.createdAt", dateFormat="dd/MM/yyyy",target="createdAt"),
		@Mapping(source="entity.updatedAt", dateFormat="dd/MM/yyyy",target="updatedAt"),
	})
	RecetteDto toDto(Recette entity) throws ParseException;

	@IterableMapping(qualifiedBy = {FullTransformerQualifier.class})
    List<RecetteDto> toDtos(List<Recette> entities) throws ParseException;

    default RecetteDto toLiteDto(Recette entity) {
		if (entity == null) {
			return null;
		}
		RecetteDto dto = new RecetteDto();
		dto.setId( entity.getId() );
		return dto;
    }

	default List<RecetteDto> toLiteDtos(List<Recette> entities) {
		if (entities == null || entities.stream().allMatch(o -> o == null)) {
			return null;
		}
		List<RecetteDto> dtos = new ArrayList<RecetteDto>();
		for (Recette entity : entities) {
			dtos.add(toLiteDto(entity));
		}
		return dtos;
	}

	@Mappings({
		@Mapping(source="dto.id", target="id"),
		@Mapping(source="dto.source", target="source"),
		@Mapping(source="dto.montant", target="montant"),
		@Mapping(source="dto.createdAt", dateFormat="dd/MM/yyyy",target="createdAt"),
		@Mapping(source="dto.createdBy", target="createdBy"),
		@Mapping(source="dto.isDeleted", target="isDeleted"),
		@Mapping(source="dto.updatedAt", dateFormat="dd/MM/yyyy",target="updatedAt"),
		@Mapping(source="dto.updatedBy", target="updatedBy"),
	})
    Recette toEntity(RecetteDto dto) throws ParseException;

    //List<Recette> toEntities(List<RecetteDto> dtos) throws ParseException;

}
