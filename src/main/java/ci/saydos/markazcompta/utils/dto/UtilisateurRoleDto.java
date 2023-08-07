
/*
 * Java dto for entity table utilisateur_role 
 * Created on 2023-08-06 ( Time 01:31:06 )
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
import ci.saydos.markazcompta.utils.dto.customize._UtilisateurRoleDto;

/**
 * DTO for table "utilisateur_role"
 *
 * @author Geo
 */
@Data
@ToString
@JsonInclude(Include.NON_NULL)
@JsonPropertyOrder(alphabetic = true)
public class UtilisateurRoleDto extends _UtilisateurRoleDto implements Cloneable{

    private Integer    id                   ; // Primary Key

	private String     createdAt            ;
    private Integer    createdBy            ;
    private Boolean    isDeleted            ;
	private String     updatedAt            ;
    private Integer    updatedBy            ;
    private Integer    roleId               ;
    private Integer    utilisateurId        ;

    //----------------------------------------------------------------------
    // ENTITY LINKS FIELD ( RELATIONSHIP )
    //----------------------------------------------------------------------
	private String roleName;
	private String utilisateurLogin;
	private String utilisateurFirstName;
	private String utilisateurLastName;

	// Search param
	private SearchParam<Integer>  idParam               ;                     
	private SearchParam<String>   createdAtParam        ;                     
	private SearchParam<Integer>  createdByParam        ;                     
	private SearchParam<Boolean>  isDeletedParam        ;                     
	private SearchParam<String>   updatedAtParam        ;                     
	private SearchParam<Integer>  updatedByParam        ;                     
	private SearchParam<Integer>  roleIdParam           ;                     
	private SearchParam<Integer>  utilisateurIdParam    ;                     
	private SearchParam<String>   roleNameParam         ;                     
	private SearchParam<String>   utilisateurLoginParam ;                     
	private SearchParam<String>   utilisateurFirstNameParam;                     
	private SearchParam<String>   utilisateurLastNameParam;                     
    /**
     * Default constructor
     */
    public UtilisateurRoleDto()
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
