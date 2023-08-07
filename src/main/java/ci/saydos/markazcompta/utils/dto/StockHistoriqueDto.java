
/*
 * Java dto for entity table stock_historique
 * Created on 2023-08-06 ( Time 01:31:06 )
 * Generator tool : Telosys Tools Generator ( version 3.3.0 )
 * Copyright 2018 Geo. All Rights Reserved.
 */

package ci.saydos.markazcompta.utils.dto;

import java.util.Date;
import java.util.Date;

import ci.saydos.markazcompta.utils.enums.EtatStockEnum;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.*;

import ci.saydos.markazcompta.utils.contract.*;
import ci.saydos.markazcompta.utils.dto.customize._StockHistoriqueDto;

/**
 * DTO for table "stock_historique"
 *
 * @author Geo
 */
@Data
@ToString
@JsonInclude(Include.NON_NULL)
@JsonPropertyOrder(alphabetic = true)
public class StockHistoriqueDto extends _StockHistoriqueDto implements Cloneable {

    private Integer id; // Primary Key

    private String        intitule;
    private Integer       idStock;
    private Integer       quantite;
    private EtatStockEnum etat;
    private String        dateMvt;
    private String        createdAt;
    private Integer       createdBy;
    private Boolean       isDeleted;
    private String        updatedAt;
    private Integer       updatedBy;

    //----------------------------------------------------------------------
    // ENTITY LINKS FIELD ( RELATIONSHIP )
    //----------------------------------------------------------------------
    private String stockIntitule;

    // Search param
    private SearchParam<Integer>       idParam;
    private SearchParam<String>        intituleParam;
    private SearchParam<Integer>       idStockParam;
    private SearchParam<Integer>       quantiteParam;
    private SearchParam<EtatStockEnum> etatParam;
    private SearchParam<String>        dateMvtParam;
    private SearchParam<String>        createdAtParam;
    private SearchParam<Integer>       createdByParam;
    private SearchParam<Boolean>       isDeletedParam;
    private SearchParam<String>        updatedAtParam;
    private SearchParam<Integer>       updatedByParam;
    private SearchParam<String>        stockIntituleParam;

    /**
     * Default constructor
     */
    public StockHistoriqueDto() {
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
