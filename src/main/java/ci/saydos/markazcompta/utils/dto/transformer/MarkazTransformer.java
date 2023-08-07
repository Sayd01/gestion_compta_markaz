

/*
 * Java transformer for entity table markaz 
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
 * TRANSFORMER for table "markaz"
 * 
 * @author Geo
 *
 */
@Mapper
public interface MarkazTransformer {

	MarkazTransformer INSTANCE = Mappers.getMapper(MarkazTransformer.class);

	@FullTransformerQualifier
	@Mappings({
		@Mapping(source="entity.createdAt", dateFormat="dd/MM/yyyy",target="createdAt"),
		@Mapping(source="entity.updatedAt", dateFormat="dd/MM/yyyy",target="updatedAt"),
	})
	MarkazDto toDto(Markaz entity) throws ParseException;

	@IterableMapping(qualifiedBy = {FullTransformerQualifier.class})
    List<MarkazDto> toDtos(List<Markaz> entities) throws ParseException;

    default MarkazDto toLiteDto(Markaz entity) {
		if (entity == null) {
			return null;
		}
		MarkazDto dto = new MarkazDto();
		dto.setId( entity.getId() );
		dto.setIntitule( entity.getIntitule() );
		return dto;
    }

	default List<MarkazDto> toLiteDtos(List<Markaz> entities) {
		if (entities == null || entities.stream().allMatch(o -> o == null)) {
			return null;
		}
		List<MarkazDto> dtos = new ArrayList<MarkazDto>();
		for (Markaz entity : entities) {
			dtos.add(toLiteDto(entity));
		}
		return dtos;
	}

	@Mappings({
		@Mapping(source="dto.id", target="id"),
		@Mapping(source="dto.code", target="code"),
		@Mapping(source="dto.intitule", target="intitule"),
		@Mapping(source="dto.adresse", target="adresse"),
		@Mapping(source="dto.createdAt", dateFormat="dd/MM/yyyy",target="createdAt"),
		@Mapping(source="dto.createdBy", target="createdBy"),
		@Mapping(source="dto.isDeleted", target="isDeleted"),
		@Mapping(source="dto.updatedAt", dateFormat="dd/MM/yyyy",target="updatedAt"),
		@Mapping(source="dto.updatedBy", target="updatedBy"),
	})
    Markaz toEntity(MarkazDto dto) throws ParseException;

    //List<Markaz> toEntities(List<MarkazDto> dtos) throws ParseException;

}
