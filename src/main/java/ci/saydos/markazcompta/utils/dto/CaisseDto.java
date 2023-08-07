
/*
 * Java dto for entity table caisse
 * Created on 2023-08-06 ( Time 01:31:04 )
 * Generator tool : Telosys Tools Generator ( version 3.3.0 )
 * Copyright 2018 Geo. All Rights Reserved.
 */

package ci.saydos.markazcompta.utils.dto;

import java.util.Date;
import java.util.Date;

import ci.saydos.markazcompta.utils.enums.TypeCaisseEnum;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.*;

import ci.saydos.markazcompta.utils.contract.*;
import ci.saydos.markazcompta.utils.dto.customize._CaisseDto;

/**
 * DTO for table "caisse"
 *
 * @author Geo
 */
@Data
@ToString
@JsonInclude(Include.NON_NULL)
@JsonPropertyOrder(alphabetic = true)
public class CaisseDto extends _CaisseDto implements Cloneable {

    private Integer id; // Primary Key

    private Integer        idMarkaz;
    private Integer        idUtilisateur;
    private Integer        idDepense;
    private Double         montant;
    private Double         montantApprovisionnement;
    private String         dateApprovisionnement;
    private TypeCaisseEnum type;
    private String         createdAt;
    private Integer        createdBy;
    private Boolean        isDeleted;
    private String         updatedAt;
    private Integer        updatedBy;

    //----------------------------------------------------------------------
    // ENTITY LINKS FIELD ( RELATIONSHIP )
    //----------------------------------------------------------------------
    private String depenseCode;
    private String markazCode;
    private String markazIntitule;
    private String utilisateurLogin;
    private String utilisateurFirstName;
    private String utilisateurLastName;

    // Search param
    private SearchParam<Integer>        idParam;
    private SearchParam<Integer>        idMarkazParam;
    private SearchParam<Integer>        idUtilisateurParam;
    private SearchParam<Integer>        idDepenseParam;
    private SearchParam<Double>         montantParam;
    private SearchParam<Double>         montantApprovisionnementParam;
    private SearchParam<String>         dateApprovisionnementParam;
    private SearchParam<TypeCaisseEnum> typeParam;
    private SearchParam<String>         createdAtParam;
    private SearchParam<Integer>        createdByParam;
    private SearchParam<Boolean>        isDeletedParam;
    private SearchParam<String>         updatedAtParam;
    private SearchParam<Integer>        updatedByParam;
    private SearchParam<String>         depenseCodeParam;
    private SearchParam<String>         markazCodeParam;
    private SearchParam<String>         markazIntituleParam;
    private SearchParam<String>         utilisateurLoginParam;
    private SearchParam<String>         utilisateurFirstNameParam;
    private SearchParam<String>         utilisateurLastNameParam;

    /**
     * Default constructor
     */
    public CaisseDto() {
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
