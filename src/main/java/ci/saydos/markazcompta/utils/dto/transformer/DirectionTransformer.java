

/*
 * Java transformer for entity table direction 
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
 * TRANSFORMER for table "direction"
 * 
 * @author Geo
 *
 */
@Mapper
public interface DirectionTransformer {

	DirectionTransformer INSTANCE = Mappers.getMapper(DirectionTransformer.class);

	@FullTransformerQualifier
	@Mappings({
		@Mapping(source="entity.createdAt", dateFormat="dd/MM/yyyy",target="createdAt"),
		@Mapping(source="entity.updatedAt", dateFormat="dd/MM/yyyy",target="updatedAt"),
		@Mapping(source="entity.markaz.id", target="idMarkaz"),
		@Mapping(source="entity.markaz.code", target="markazCode"),
		@Mapping(source="entity.markaz.intitule", target="markazIntitule"),
	})
	DirectionDto toDto(Direction entity) throws ParseException;

	@IterableMapping(qualifiedBy = {FullTransformerQualifier.class})
    List<DirectionDto> toDtos(List<Direction> entities) throws ParseException;

    default DirectionDto toLiteDto(Direction entity) {
		if (entity == null) {
			return null;
		}
		DirectionDto dto = new DirectionDto();
		dto.setId( entity.getId() );
		dto.setIntitule( entity.getIntitule() );
		return dto;
    }

	default List<DirectionDto> toLiteDtos(List<Direction> entities) {
		if (entities == null || entities.stream().allMatch(o -> o == null)) {
			return null;
		}
		List<DirectionDto> dtos = new ArrayList<DirectionDto>();
		for (Direction entity : entities) {
			dtos.add(toLiteDto(entity));
		}
		return dtos;
	}

	@Mappings({
		@Mapping(source="dto.id", target="id"),
		@Mapping(source="dto.code", target="code"),
		@Mapping(source="dto.intitule", target="intitule"),
		@Mapping(source="dto.createdAt", dateFormat="dd/MM/yyyy",target="createdAt"),
		@Mapping(source="dto.createdBy", target="createdBy"),
		@Mapping(source="dto.isDeleted", target="isDeleted"),
		@Mapping(source="dto.updatedAt", dateFormat="dd/MM/yyyy",target="updatedAt"),
		@Mapping(source="dto.updatedBy", target="updatedBy"),
		@Mapping(source="markaz", target="markaz"),
	})
    Direction toEntity(DirectionDto dto, Markaz markaz) throws ParseException;

    //List<Direction> toEntities(List<DirectionDto> dtos) throws ParseException;

}
