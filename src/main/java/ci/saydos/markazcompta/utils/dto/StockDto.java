
/*
 * Java dto for entity table stock 
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
import ci.saydos.markazcompta.utils.dto.customize._StockDto;

/**
 * DTO for table "stock"
 *
 * @author Geo
 */
@Data
@ToString
@JsonInclude(Include.NON_NULL)
@JsonPropertyOrder(alphabetic = true)
public class StockDto extends _StockDto implements Cloneable{

    private Integer    id                   ; // Primary Key

    private String     intitule             ;
    private Integer    quantite             ;
    private String     etat                 ;
	private String     dateEntree           ;
	private String     dateSortie           ;
	private String     createdAt            ;
    private Integer    createdBy            ;
    private Boolean    isDeleted            ;
	private String     updatedAt            ;
    private Integer    updatedBy            ;

    //----------------------------------------------------------------------
    // ENTITY LINKS FIELD ( RELATIONSHIP )
    //----------------------------------------------------------------------

	// Search param
	private SearchParam<Integer>  idParam               ;                     
	private SearchParam<String>   intituleParam         ;                     
	private SearchParam<Integer>  quantiteParam         ;                     
	private SearchParam<String>   etatParam             ;                     
	private SearchParam<String>   dateEntreeParam       ;                     
	private SearchParam<String>   dateSortieParam       ;                     
	private SearchParam<String>   createdAtParam        ;                     
	private SearchParam<Integer>  createdByParam        ;                     
	private SearchParam<Boolean>  isDeletedParam        ;                     
	private SearchParam<String>   updatedAtParam        ;                     
	private SearchParam<Integer>  updatedByParam        ;                     
    /**
     * Default constructor
     */
    public StockDto()
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
