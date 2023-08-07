

/*
 * Java transformer for entity table stock_historique 
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
 * TRANSFORMER for table "stock_historique"
 * 
 * @author Geo
 *
 */
@Mapper
public interface StockHistoriqueTransformer {

	StockHistoriqueTransformer INSTANCE = Mappers.getMapper(StockHistoriqueTransformer.class);

	@FullTransformerQualifier
	@Mappings({
		@Mapping(source="entity.dateMvt", dateFormat="dd/MM/yyyy",target="dateMvt"),
		@Mapping(source="entity.createdAt", dateFormat="dd/MM/yyyy",target="createdAt"),
		@Mapping(source="entity.updatedAt", dateFormat="dd/MM/yyyy",target="updatedAt"),
		@Mapping(source="entity.stock.id", target="idStock"),
		@Mapping(source="entity.stock.intitule", target="stockIntitule"),
	})
	StockHistoriqueDto toDto(StockHistorique entity) throws ParseException;

	@IterableMapping(qualifiedBy = {FullTransformerQualifier.class})
    List<StockHistoriqueDto> toDtos(List<StockHistorique> entities) throws ParseException;

    default StockHistoriqueDto toLiteDto(StockHistorique entity) {
		if (entity == null) {
			return null;
		}
		StockHistoriqueDto dto = new StockHistoriqueDto();
		dto.setId( entity.getId() );
		dto.setIntitule( entity.getIntitule() );
		return dto;
    }

	default List<StockHistoriqueDto> toLiteDtos(List<StockHistorique> entities) {
		if (entities == null || entities.stream().allMatch(o -> o == null)) {
			return null;
		}
		List<StockHistoriqueDto> dtos = new ArrayList<StockHistoriqueDto>();
		for (StockHistorique entity : entities) {
			dtos.add(toLiteDto(entity));
		}
		return dtos;
	}

	@Mappings({
		@Mapping(source="dto.id", target="id"),
		@Mapping(source="dto.intitule", target="intitule"),
		@Mapping(source="dto.quantite", target="quantite"),
		@Mapping(source="dto.etat", target="etat"),
		@Mapping(source="dto.dateMvt", dateFormat="dd/MM/yyyy",target="dateMvt"),
		@Mapping(source="dto.createdAt", dateFormat="dd/MM/yyyy",target="createdAt"),
		@Mapping(source="dto.createdBy", target="createdBy"),
		@Mapping(source="dto.isDeleted", target="isDeleted"),
		@Mapping(source="dto.updatedAt", dateFormat="dd/MM/yyyy",target="updatedAt"),
		@Mapping(source="dto.updatedBy", target="updatedBy"),
		@Mapping(source="stock", target="stock"),
	})
    StockHistorique toEntity(StockHistoriqueDto dto, Stock stock) throws ParseException;

    //List<StockHistorique> toEntities(List<StockHistoriqueDto> dtos) throws ParseException;

}
