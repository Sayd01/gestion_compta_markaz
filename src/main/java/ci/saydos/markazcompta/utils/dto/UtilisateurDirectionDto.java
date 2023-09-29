
/*
 * Java dto for entity table utilisateur_direction 
 * Created on 2023-08-29 ( Time 13:35:25 )
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
import ci.saydos.markazcompta.utils.dto.customize._UtilisateurDirectionDto;

/**
 * DTO for table "utilisateur_direction"
 *
 * @author Geo
 */
@Data
@ToString
@JsonInclude(Include.NON_NULL)
@JsonPropertyOrder(alphabetic = true)
public class UtilisateurDirectionDto extends _UtilisateurDirectionDto implements Cloneable{

    private Integer    id                   ; // Primary Key

    private Integer    idDirection          ;
    private Integer    idUtilisateur        ;
	private String     createdAt            ;
    private Integer    createdBy            ;
    private Boolean    isDeleted            ;
	private String     updatedAt            ;
    private Integer    updatedBy            ;

    //----------------------------------------------------------------------
    // ENTITY LINKS FIELD ( RELATIONSHIP )
    //----------------------------------------------------------------------
	private String utilisateurFirstName;
	private String utilisateurLastName;
	private String utilisateurLogin;
	private String utilisateurEmail;
	private String directionCode;
	private String directionIntitule;

	// Search param
	private SearchParam<Integer>  idParam               ;                     
	private SearchParam<Integer>  idDirectionParam      ;                     
	private SearchParam<Integer>  idUtilisateurParam    ;                     
	private SearchParam<String>   createdAtParam        ;                     
	private SearchParam<Integer>  createdByParam        ;                     
	private SearchParam<Boolean>  isDeletedParam        ;                     
	private SearchParam<String>   updatedAtParam        ;                     
	private SearchParam<Integer>  updatedByParam        ;                     
	private SearchParam<String>   utilisateurFirstNameParam;                     
	private SearchParam<String>   utilisateurLastNameParam;                     
	private SearchParam<String>   utilisateurLoginParam ;                     
	private SearchParam<String>   directionCodeParam    ;                     
	private SearchParam<String>   directionIntituleParam;                     
    /**
     * Default constructor
     */
    public UtilisateurDirectionDto()
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
