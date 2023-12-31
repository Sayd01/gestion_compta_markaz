
/*
 * Java dto for entity table demande
 * Created on 2023-08-08 ( Time 19:27:54 )
 * Generator tool : Telosys Tools Generator ( version 3.3.0 )
 * Copyright 2018 Geo. All Rights Reserved.
 */

package ci.saydos.markazcompta.utils.dto;

import java.util.Date;
import java.util.List;
import java.util.Date;

import ci.saydos.markazcompta.utils.enums.StatutDemandeEnum;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.*;

import ci.saydos.markazcompta.utils.contract.*;
import ci.saydos.markazcompta.utils.dto.customize._DemandeDto;

/**
 * DTO for table "demande"
 *
 * @author Geo
 */
@Data
@ToString
@JsonInclude(Include.NON_NULL)
@JsonPropertyOrder(alphabetic = true)
public class DemandeDto extends _DemandeDto implements Cloneable {

    private Integer id; // Primary Key

    private String            label;
    private String            code;
    private String            description;
    private Double            montant;
    private String            imageUrl;
    private String            dateDebut;
    private String            dateFin;
    private StatutDemandeEnum statut;
    private Integer           idUtilisateur;
    private String            createdAt;
    private Integer           createdBy;
    private Boolean           isDeleted;
    private String            updatedAt;
    private Integer           updatedBy;
    private Integer           idDirection;

    //----------------------------------------------------------------------
    // ENTITY LINKS FIELD ( RELATIONSHIP )
    //----------------------------------------------------------------------
    private String utilisateurLogin;
    private String utilisateurFirstName;
    private String utilisateurLastName;
    private String directionCode;
    private String directionIntitule;

    // Search param
    private SearchParam<Integer>           idParam;
    private SearchParam<String>            labelParam;
    private SearchParam<String>            codeParam;
    private SearchParam<String>            descriptionParam;
    private SearchParam<Double>            montantParam;
    private SearchParam<String>            imageUrlParam;
    private SearchParam<String>            dateDebutParam;
    private SearchParam<String>            dateFinParam;
    private SearchParam<StatutDemandeEnum> statutParam;
    private SearchParam<Integer>           idUtilisateurParam;
    private SearchParam<String>            createdAtParam;
    private SearchParam<Integer>           createdByParam;
    private SearchParam<Boolean>           isDeletedParam;
    private SearchParam<String>            updatedAtParam;
    private SearchParam<Integer>           updatedByParam;
    private SearchParam<Integer>           idDirectionParam;
    private SearchParam<String>            utilisateurLoginParam;
    private SearchParam<String>            utilisateurFirstNameParam;
    private SearchParam<String>            utilisateurLastNameParam;
    private SearchParam<String>            directionCodeParam;
    private SearchParam<String>            directionIntituleParam;

    /**
     * Default constructor
     */
    public DemandeDto() {
        super();
    }

    //----------------------------------------------------------------------
    // clone METHOD
    //----------------------------------------------------------------------
    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
