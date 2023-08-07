
/*
 * Java dto for entity table demande_historique 
 * Created on 2023-08-06 ( Time 01:31:05 )
 * Generator tool : Telosys Tools Generator ( version 3.3.0 )
 * Copyright 2018 Geo. All Rights Reserved.
 */

package ci.saydos.markazcompta.utils.dto;

import java.util.Date;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.*;

import ci.saydos.markazcompta.utils.contract.*;
import ci.saydos.markazcompta.utils.dto.customize._DemandeHistoriqueDto;

/**
 * DTO for table "demande_historique"
 *
 * @author Geo
 */
@Data
@ToString
@JsonInclude(Include.NON_NULL)
@JsonPropertyOrder(alphabetic = true)
public class DemandeHistoriqueDto extends _DemandeHistoriqueDto implements Cloneable{

    private Integer    id                   ; // Primary Key

    private Integer    idDemande            ;
    private Integer    idUtilisateur        ;
    private String     statut               ;
	private String     createdAt            ;
    private Integer    createdBy            ;
    private Boolean    isDeleted            ;
	private String     updatedAt            ;
    private Integer    updatedBy            ;

    //----------------------------------------------------------------------
    // ENTITY LINKS FIELD ( RELATIONSHIP )
    //----------------------------------------------------------------------
	private String utilisateurLogin;
	private String utilisateurFirstName;
	private String utilisateurLastName;
	private String demandeCode;

	// Search param
	private SearchParam<Integer>  idParam               ;                     
	private SearchParam<Integer>  idDemandeParam        ;                     
	private SearchParam<Integer>  idUtilisateurParam    ;                     
	private SearchParam<String>   statutParam           ;                     
	private SearchParam<String>   createdAtParam        ;                     
	private SearchParam<Integer>  createdByParam        ;                     
	private SearchParam<Boolean>  isDeletedParam        ;                     
	private SearchParam<String>   updatedAtParam        ;                     
	private SearchParam<Integer>  updatedByParam        ;                     
	private SearchParam<String>   utilisateurLoginParam ;                     
	private SearchParam<String>   utilisateurFirstNameParam;                     
	private SearchParam<String>   utilisateurLastNameParam;                     
	private SearchParam<String>   demandeCodeParam      ;                     
    /**
     * Default constructor
     */
    public DemandeHistoriqueDto()
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
