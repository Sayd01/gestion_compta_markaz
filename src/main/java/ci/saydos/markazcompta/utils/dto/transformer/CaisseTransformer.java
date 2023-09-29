

/*
 * Java transformer for entity table caisse
 * Created on 2023-08-10 ( Time 17:44:55 )
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
 * TRANSFORMER for table "caisse"
 *
 * @author Geo
 *
 */
@Mapper
public interface CaisseTransformer {

    CaisseTransformer INSTANCE = Mappers.getMapper(CaisseTransformer.class);

    @FullTransformerQualifier
    @Mappings({
            @Mapping(source="entity.createdAt", dateFormat="dd/MM/yyyy",target="createdAt"),
            @Mapping(source="entity.updatedAt", dateFormat="dd/MM/yyyy",target="updatedAt"),
            @Mapping(source="entity.utilisateur.id", target="idUtilisateur"),
            @Mapping(source="entity.utilisateur.firstName", target="utilisateurFirstName"),
            @Mapping(source="entity.utilisateur.lastName", target="utilisateurLastName"),
            @Mapping(source="entity.utilisateur.login", target="utilisateurLogin"),
            @Mapping(source="entity.depense.id", target="idDepense"),
            @Mapping(source="entity.depense.code", target="depenseCode"),
    })
    CaisseDto toDto(Caisse entity) throws ParseException;

    @IterableMapping(qualifiedBy = {FullTransformerQualifier.class})
    List<CaisseDto> toDtos(List<Caisse> entities) throws ParseException;

    default CaisseDto toLiteDto(Caisse entity) {
        if (entity == null) {
            return null;
        }
        CaisseDto dto = new CaisseDto();
        dto.setId( entity.getId() );
        return dto;
    }

    default List<CaisseDto> toLiteDtos(List<Caisse> entities) {
        if (entities == null || entities.stream().allMatch(o -> o == null)) {
            return null;
        }
        List<CaisseDto> dtos = new ArrayList<CaisseDto>();
        for (Caisse entity : entities) {
            dtos.add(toLiteDto(entity));
        }
        return dtos;
    }

    @Mappings({
            @Mapping(source="dto.id", target="id"),
            @Mapping(source="dto.createdAt", dateFormat="dd/MM/yyyy",target="createdAt"),
            @Mapping(source="dto.createdBy", target="createdBy"),
            @Mapping(source="dto.isDeleted", target="isDeleted"),
            @Mapping(source="dto.solde", target="solde"),
            @Mapping(source="dto.montantEntre", target="montantEntre"),
            @Mapping(source="dto.type", target="type"),
            @Mapping(source="dto.updatedAt", dateFormat="dd/MM/yyyy",target="updatedAt"),
            @Mapping(source="dto.updatedBy", target="updatedBy"),
            @Mapping(source="dto.montantSortie", target="montantSortie"),
            @Mapping(source="utilisateur", target="utilisateur"),
            @Mapping(source="depense", target="depense"),
    })
    Caisse toEntity(CaisseDto dto, Utilisateur utilisateur, Depense depense) throws ParseException;

    //List<Caisse> toEntities(List<CaisseDto> dtos) throws ParseException;

}
