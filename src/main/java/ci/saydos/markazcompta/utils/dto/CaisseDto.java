
/*
 * Java dto for entity table caisse
 * Created on 2023-08-10 ( Time 18:38:42 )
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

    private String         createdAt;
    private Integer        createdBy;
    private Boolean        isDeleted;
    private TypeCaisseEnum type;
    private String         updatedAt;
    private String         libelle;
    private Integer        updatedBy;
    private Integer        idDepense;
    private Integer        idUtilisateur;
    private Double         montantEntre;
    private Double         montantSortie;
    private Double         solde;

    //----------------------------------------------------------------------
    // ENTITY LINKS FIELD ( RELATIONSHIP )
    //----------------------------------------------------------------------
    private String utilisateurFirstName;
    private String utilisateurLastName;
    private String depenseCode;

    // Search param
    private SearchParam<Integer>        idParam;
    private SearchParam<String>         createdAtParam;
    private SearchParam<Integer>        createdByParam;
    private SearchParam<Boolean>        isDeletedParam;
    private SearchParam<Double>         soldeParam;
    private SearchParam<Double>         montantEntreParam;
    private SearchParam<TypeCaisseEnum> typeParam;
    private SearchParam<String>         updatedAtParam;
    private SearchParam<Integer>        updatedByParam;
    private SearchParam<Integer>        idDepenseParam;
    private SearchParam<Integer>        idUtilisateurParam;
    private SearchParam<Double>         montantSortieParam;
    private SearchParam<String>         utilisateurFirstNameParam;
    private SearchParam<String>         utilisateurLastNameParam;
    private SearchParam<String>         depenseCodeParam;

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