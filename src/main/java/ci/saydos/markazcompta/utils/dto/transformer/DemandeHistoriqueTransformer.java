

/*
 * Java transformer for entity table demande_historique 
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
 * TRANSFORMER for table "demande_historique"
 * 
 * @author Geo
 *
 */
@Mapper
public interface DemandeHistoriqueTransformer {

	DemandeHistoriqueTransformer INSTANCE = Mappers.getMapper(DemandeHistoriqueTransformer.class);

	@FullTransformerQualifier
	@Mappings({
		@Mapping(source="entity.createdAt", dateFormat="dd/MM/yyyy",target="createdAt"),
		@Mapping(source="entity.updatedAt", dateFormat="dd/MM/yyyy",target="updatedAt"),
		@Mapping(source="entity.utilisateur.id", target="idUtilisateur"),
		@Mapping(source="entity.utilisateur.firstName", target="utilisateurFirstName"),
		@Mapping(source="entity.utilisateur.lastName", target="utilisateurLastName"),
		@Mapping(source="entity.demande.id", target="idDemande"),
		@Mapping(source="entity.demande.code", target="demandeCode"),
	})
	DemandeHistoriqueDto toDto(DemandeHistorique entity) throws ParseException;

	@IterableMapping(qualifiedBy = {FullTransformerQualifier.class})
    List<DemandeHistoriqueDto> toDtos(List<DemandeHistorique> entities) throws ParseException;

    default DemandeHistoriqueDto toLiteDto(DemandeHistorique entity) {
		if (entity == null) {
			return null;
		}
		DemandeHistoriqueDto dto = new DemandeHistoriqueDto();
		dto.setId( entity.getId() );
		return dto;
    }

	default List<DemandeHistoriqueDto> toLiteDtos(List<DemandeHistorique> entities) {
		if (entities == null || entities.stream().allMatch(o -> o == null)) {
			return null;
		}
		List<DemandeHistoriqueDto> dtos = new ArrayList<DemandeHistoriqueDto>();
		for (DemandeHistorique entity : entities) {
			dtos.add(toLiteDto(entity));
		}
		return dtos;
	}

	@Mappings({
		@Mapping(source="dto.id", target="id"),
		@Mapping(source="dto.statut", target="statut"),
		@Mapping(source="dto.createdAt", dateFormat="dd/MM/yyyy",target="createdAt"),
		@Mapping(source="dto.createdBy", target="createdBy"),
		@Mapping(source="dto.isDeleted", target="isDeleted"),
		@Mapping(source="dto.updatedAt", dateFormat="dd/MM/yyyy",target="updatedAt"),
		@Mapping(source="dto.updatedBy", target="updatedBy"),
		@Mapping(source="utilisateur", target="utilisateur"),
		@Mapping(source="demande", target="demande"),
	})
    DemandeHistorique toEntity(DemandeHistoriqueDto dto, Utilisateur utilisateur, Demande demande) throws ParseException;

    //List<DemandeHistorique> toEntities(List<DemandeHistoriqueDto> dtos) throws ParseException;

}
