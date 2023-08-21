

/*
 * Java transformer for entity table depense 
 * Created on 2023-08-10 ( Time 14:07:29 )
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
 * TRANSFORMER for table "depense"
 * 
 * @author Geo
 *
 */
@Mapper
public interface DepenseTransformer {

	DepenseTransformer INSTANCE = Mappers.getMapper(DepenseTransformer.class);

	@FullTransformerQualifier
	@Mappings({
		@Mapping(source="entity.createdAt", dateFormat="dd/MM/yyyy",target="createdAt"),
		@Mapping(source="entity.updatedAt", dateFormat="dd/MM/yyyy",target="updatedAt"),
		@Mapping(source="entity.demande.id", target="idDemande"),
		@Mapping(source="entity.demande.code", target="demandeCode"),
		@Mapping(source="entity.chargeFixe.id", target="idChargeFixe"),
		@Mapping(source="entity.chargeFixe.code", target="chargeFixeCode"),
	})
	DepenseDto toDto(Depense entity) throws ParseException;

	@IterableMapping(qualifiedBy = {FullTransformerQualifier.class})
    List<DepenseDto> toDtos(List<Depense> entities) throws ParseException;

    default DepenseDto toLiteDto(Depense entity) {
		if (entity == null) {
			return null;
		}
		DepenseDto dto = new DepenseDto();
		dto.setId( entity.getId() );
		return dto;
    }

	default List<DepenseDto> toLiteDtos(List<Depense> entities) {
		if (entities == null || entities.stream().allMatch(o -> o == null)) {
			return null;
		}
		List<DepenseDto> dtos = new ArrayList<DepenseDto>();
		for (Depense entity : entities) {
			dtos.add(toLiteDto(entity));
		}
		return dtos;
	}

	@Mappings({
		@Mapping(source="dto.id", target="id"),
		@Mapping(source="dto.code", target="code"),
		@Mapping(source="dto.createdAt", dateFormat="dd/MM/yyyy",target="createdAt"),
		@Mapping(source="dto.createdBy", target="createdBy"),
		@Mapping(source="dto.isDeleted", target="isDeleted"),
		@Mapping(source="dto.montant", target="montant"),
		@Mapping(source="dto.updatedAt", dateFormat="dd/MM/yyyy",target="updatedAt"),
		@Mapping(source="dto.updatedBy", target="updatedBy"),
		@Mapping(source="dto.typeDepense", target="typeDepense"),
		@Mapping(source="demande", target="demande"),
		@Mapping(source="chargeFixe", target="chargeFixe"),
	})
    Depense toEntity(DepenseDto dto, Demande demande, ChargeFixe chargeFixe) throws ParseException;

    //List<Depense> toEntities(List<DepenseDto> dtos) throws ParseException;

}
