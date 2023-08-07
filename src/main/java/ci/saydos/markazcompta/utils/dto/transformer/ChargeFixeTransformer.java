

/*
 * Java transformer for entity table charge_fixe 
 * Created on 2023-08-06 ( Time 01:31:04 )
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
 * TRANSFORMER for table "charge_fixe"
 * 
 * @author Geo
 *
 */
@Mapper
public interface ChargeFixeTransformer {

	ChargeFixeTransformer INSTANCE = Mappers.getMapper(ChargeFixeTransformer.class);

	@FullTransformerQualifier
	@Mappings({
		@Mapping(source="entity.createdAt", dateFormat="dd/MM/yyyy",target="createdAt"),
		@Mapping(source="entity.updatedAt", dateFormat="dd/MM/yyyy",target="updatedAt"),
	})
	ChargeFixeDto toDto(ChargeFixe entity) throws ParseException;

	@IterableMapping(qualifiedBy = {FullTransformerQualifier.class})
    List<ChargeFixeDto> toDtos(List<ChargeFixe> entities) throws ParseException;

    default ChargeFixeDto toLiteDto(ChargeFixe entity) {
		if (entity == null) {
			return null;
		}
		ChargeFixeDto dto = new ChargeFixeDto();
		dto.setId( entity.getId() );
		return dto;
    }

	default List<ChargeFixeDto> toLiteDtos(List<ChargeFixe> entities) {
		if (entities == null || entities.stream().allMatch(o -> o == null)) {
			return null;
		}
		List<ChargeFixeDto> dtos = new ArrayList<ChargeFixeDto>();
		for (ChargeFixe entity : entities) {
			dtos.add(toLiteDto(entity));
		}
		return dtos;
	}

	@Mappings({
		@Mapping(source="dto.id", target="id"),
		@Mapping(source="dto.code", target="code"),
		@Mapping(source="dto.label", target="label"),
		@Mapping(source="dto.createdAt", dateFormat="dd/MM/yyyy",target="createdAt"),
		@Mapping(source="dto.createdBy", target="createdBy"),
		@Mapping(source="dto.isDeleted", target="isDeleted"),
		@Mapping(source="dto.updatedAt", dateFormat="dd/MM/yyyy",target="updatedAt"),
		@Mapping(source="dto.updatedBy", target="updatedBy"),
	})
    ChargeFixe toEntity(ChargeFixeDto dto) throws ParseException;

    //List<ChargeFixe> toEntities(List<ChargeFixeDto> dtos) throws ParseException;

}
