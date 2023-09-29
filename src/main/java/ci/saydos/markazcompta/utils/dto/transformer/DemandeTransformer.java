

/*
 * Java transformer for entity table demande
 * Created on 2023-08-08 ( Time 19:27:54 )
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
 * TRANSFORMER for table "demande"
 *
 * @author Geo
 */
@Mapper
public interface DemandeTransformer {

    DemandeTransformer INSTANCE = Mappers.getMapper(DemandeTransformer.class);

    @FullTransformerQualifier
    @Mappings({
            @Mapping(source = "entity.dateDebut", dateFormat = "dd/MM/yyyy HH:mm:ss", target = "dateDebut"),
            @Mapping(source = "entity.dateFin", dateFormat = "dd/MM/yyyy HH:mm:ss", target = "dateFin"),
            @Mapping(source = "entity.createdAt", dateFormat = "dd/MM/yyyy HH:mm:ss", target = "createdAt"),
            @Mapping(source = "entity.updatedAt", dateFormat = "dd/MM/yyyy", target = "updatedAt"),
            @Mapping(source = "entity.utilisateur.id", target = "idUtilisateur"),
            @Mapping(source = "entity.utilisateur.login", target = "utilisateurLogin"),
            @Mapping(source = "entity.utilisateur.firstName", target = "utilisateurFirstName"),
            @Mapping(source = "entity.utilisateur.lastName", target = "utilisateurLastName"),
            @Mapping(source = "entity.direction.id", target = "idDirection"),
            @Mapping(source = "entity.direction.code", target = "directionCode"),
            @Mapping(source = "entity.direction.intitule", target = "directionIntitule"),
    })
    DemandeDto toDto(Demande entity) throws ParseException;

    @IterableMapping(qualifiedBy = {FullTransformerQualifier.class})
    List<DemandeDto> toDtos(List<Demande> entities) throws ParseException;

    default DemandeDto toLiteDto(Demande entity) {
        if (entity == null) {
            return null;
        }
        DemandeDto dto = new DemandeDto();
        dto.setId(entity.getId());
        return dto;
    }

    default List<DemandeDto> toLiteDtos(List<Demande> entities) {
        if (entities == null || entities.stream().allMatch(o -> o == null)) {
            return null;
        }
        List<DemandeDto> dtos = new ArrayList<DemandeDto>();
        for (Demande entity : entities) {
            dtos.add(toLiteDto(entity));
        }
        return dtos;
    }

    @Mappings({
            @Mapping(source = "dto.id", target = "id"),
            @Mapping(source = "dto.label", target = "label"),
            @Mapping(source = "dto.code", target = "code"),
            @Mapping(source = "dto.description", target = "description"),
            @Mapping(source = "dto.montant", target = "montant"),
            @Mapping(source = "dto.imageUrl", target = "imageUrl"),
            @Mapping(source = "dto.dateDebut", dateFormat = "dd/MM/yyyy", target = "dateDebut"),
            @Mapping(source = "dto.dateFin", dateFormat = "dd/MM/yyyy", target = "dateFin"),
            @Mapping(source = "dto.statut", target = "statut"),
            @Mapping(source = "dto.createdAt", dateFormat = "dd/MM/yyyy", target = "createdAt"),
            @Mapping(source = "dto.createdBy", target = "createdBy"),
            @Mapping(source = "dto.isDeleted", target = "isDeleted"),
            @Mapping(source = "dto.updatedAt", dateFormat = "dd/MM/yyyy", target = "updatedAt"),
            @Mapping(source = "dto.updatedBy", target = "updatedBy"),
            @Mapping(source = "utilisateur", target = "utilisateur"),
            @Mapping(source = "direction", target = "direction"),
    })
    Demande toEntity(DemandeDto dto, Utilisateur utilisateur, Direction direction) throws ParseException;

    //List<Demande> toEntities(List<DemandeDto> dtos) throws ParseException;

}
