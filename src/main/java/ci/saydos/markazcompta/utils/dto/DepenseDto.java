
/*
 * Java dto for entity table depense 
 * Created on 2023-08-06 ( Time 01:31:05 )
 * Generator tool : Telosys Tools Generator ( version 3.3.0 )
 * Copyright 2018 Geo. All Rights Reserved.
 */

package ci.saydos.markazcompta.utils.dto;

import java.util.Date;
import java.util.List;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.*;

import ci.saydos.markazcompta.utils.contract.*;
import ci.saydos.markazcompta.utils.dto.customize._DepenseDto;

/**
 * DTO for table "depense"
 *
 * @author Geo
 */
@Data
@ToString
@JsonInclude(Include.NON_NULL)
@JsonPropertyOrder(alphabetic = true)
public class DepenseDto extends _DepenseDto implements Cloneable{

    private Integer    id                   ; // Primary Key

    private String     code                 ;
    private Integer    idChargeFixe         ;
    private Integer    idDemande            ;
    private Double     montant              ;
	private String     createdAt            ;
    private Integer    createdBy            ;
    private Boolean    isDeleted            ;
	private String     updatedAt            ;
    private Integer    updatedBy            ;

    //----------------------------------------------------------------------
    // ENTITY LINKS FIELD ( RELATIONSHIP )
    //----------------------------------------------------------------------
	private String demandeCode;
	private String utilisateurLogin;
	private String utilisateurFirstName;
	private String utilisateurLastName;

	// Search param
	private SearchParam<Integer>  idParam               ;                     
	private SearchParam<String>   codeParam             ;                     
	private SearchParam<Integer>  idChargeFixeParam     ;                     
	private SearchParam<Integer>  idDemandeParam        ;                     
	private SearchParam<Double>   montantParam          ;                     
	private SearchParam<String>   createdAtParam        ;                     
	private SearchParam<Integer>  createdByParam        ;                     
	private SearchParam<Boolean>  isDeletedParam        ;                     
	private SearchParam<String>   updatedAtParam        ;                     
	private SearchParam<Integer>  updatedByParam        ;                     
	private SearchParam<String>   demandeCodeParam      ;                     
	private SearchParam<String>   utilisateurLoginParam ;                     
	private SearchParam<String>   utilisateurFirstNameParam;                     
	private SearchParam<String>   utilisateurLastNameParam;                     
    /**
     * Default constructor
     */
    public DepenseDto()
    {
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
