

/*
 * Java transformer for entity table stock 
 * Created on 2023-08-28 ( Time 14:55:50 )
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
 * TRANSFORMER for table "stock"
 * 
 * @author Geo
 *
 */
@Mapper
public interface StockTransformer {

	StockTransformer INSTANCE = Mappers.getMapper(StockTransformer.class);

	@FullTransformerQualifier
	@Mappings({
		@Mapping(source="entity.createdAt", dateFormat="dd/MM/yyyy",target="createdAt"),
		@Mapping(source="entity.dateEntree", dateFormat="dd/MM/yyyy",target="dateEntree"),
		@Mapping(source="entity.dateSortie", dateFormat="dd/MM/yyyy",target="dateSortie"),
		@Mapping(source="entity.updatedAt", dateFormat="dd/MM/yyyy",target="updatedAt"),
		@Mapping(source="entity.article.id", target="idArticle"),
		@Mapping(source="entity.article.libelle", target="articleLibelle"),
	})
	StockDto toDto(Stock entity) throws ParseException;

	@IterableMapping(qualifiedBy = {FullTransformerQualifier.class})
    List<StockDto> toDtos(List<Stock> entities) throws ParseException;

    default StockDto toLiteDto(Stock entity) {
		if (entity == null) {
			return null;
		}
		StockDto dto = new StockDto();
		dto.setId( entity.getId() );
		dto.setIntitule( entity.getIntitule() );
		return dto;
    }

	default List<StockDto> toLiteDtos(List<Stock> entities) {
		if (entities == null || entities.stream().allMatch(o -> o == null)) {
			return null;
		}
		List<StockDto> dtos = new ArrayList<StockDto>();
		for (Stock entity : entities) {
			dtos.add(toLiteDto(entity));
		}
		return dtos;
	}

	@Mappings({
		@Mapping(source="dto.id", target="id"),
		@Mapping(source="dto.createdAt", dateFormat="dd/MM/yyyy",target="createdAt"),
		@Mapping(source="dto.createdBy", target="createdBy"),
		@Mapping(source="dto.dateEntree", dateFormat="dd/MM/yyyy",target="dateEntree"),
		@Mapping(source="dto.dateSortie", dateFormat="dd/MM/yyyy",target="dateSortie"),
		@Mapping(source="dto.etat", target="etat"),
		@Mapping(source="dto.intitule", target="intitule"),
		@Mapping(source="dto.isDeleted", target="isDeleted"),
		@Mapping(source="dto.quantite", target="quantite"),
		@Mapping(source="dto.updatedAt", dateFormat="dd/MM/yyyy",target="updatedAt"),
		@Mapping(source="dto.updatedBy", target="updatedBy"),
		@Mapping(source="article", target="article"),
	})
    Stock toEntity(StockDto dto, Article article) throws ParseException;

    //List<Stock> toEntities(List<StockDto> dtos) throws ParseException;

}
