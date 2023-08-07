
/*
 * Java dto for entity table direction 
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
import ci.saydos.markazcompta.utils.dto.customize._DirectionDto;

/**
 * DTO for table "direction"
 *
 * @author Geo
 */
@Data
@ToString
@JsonInclude(Include.NON_NULL)
@JsonPropertyOrder(alphabetic = true)
public class DirectionDto extends _DirectionDto implements Cloneable{

    private Integer    id                   ; // Primary Key

    private Integer    idMarkaz             ;
    private String     code                 ;
    private String     intitule             ;
	private String     createdAt            ;
    private Integer    createdBy            ;
    private Boolean    isDeleted            ;
	private String     updatedAt            ;
    private Integer    updatedBy            ;

    //----------------------------------------------------------------------
    // ENTITY LINKS FIELD ( RELATIONSHIP )
    //----------------------------------------------------------------------
	private String markazCode;
	private String markazIntitule;

	// Search param
	private SearchParam<Integer>  idParam               ;                     
	private SearchParam<Integer>  idMarkazParam         ;                     
	private SearchParam<String>   codeParam             ;                     
	private SearchParam<String>   intituleParam         ;                     
	private SearchParam<String>   createdAtParam        ;                     
	private SearchParam<Integer>  createdByParam        ;                     
	private SearchParam<Boolean>  isDeletedParam        ;                     
	private SearchParam<String>   updatedAtParam        ;                     
	private SearchParam<Integer>  updatedByParam        ;                     
	private SearchParam<String>   markazCodeParam       ;                     
	private SearchParam<String>   markazIntituleParam   ;                     
    /**
     * Default constructor
     */
    public DirectionDto()
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
