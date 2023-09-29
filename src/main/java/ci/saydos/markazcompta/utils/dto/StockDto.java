
/*
 * Java dto for entity table stock
 * Created on 2023-08-28 ( Time 14:55:50 )
 * Generator tool : Telosys Tools Generator ( version 3.3.0 )
 * Copyright 2018 Geo. All Rights Reserved.
 */

package ci.saydos.markazcompta.utils.dto;

import java.util.Date;
import java.util.List;
import java.util.Date;

import ci.saydos.markazcompta.utils.enums.EtatStockEnum;
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
public class StockDto extends _StockDto implements Cloneable {

    private Integer id; // Primary Key

    private String        createdAt;
    private Integer       createdBy;
    private String        dateEntree;
    private String        dateSortie;
    private EtatStockEnum etat;
    private String        intitule;
    private Boolean       isDeleted;
    private Integer       quantite;
    private String        updatedAt;
    private Integer       updatedBy;
    private Integer       idArticle;

    //----------------------------------------------------------------------
    // ENTITY LINKS FIELD ( RELATIONSHIP )
    //----------------------------------------------------------------------
    private String articleLibelle;

    // Search param
    private SearchParam<Integer>       idParam;
    private SearchParam<String>        createdAtParam;
    private SearchParam<Integer>       createdByParam;
    private SearchParam<String>        dateEntreeParam;
    private SearchParam<String>        dateSortieParam;
    private SearchParam<EtatStockEnum> etatParam;
    private SearchParam<String>        intituleParam;
    private SearchParam<Boolean>       isDeletedParam;
    private SearchParam<Integer>       quantiteParam;
    private SearchParam<String>        updatedAtParam;
    private SearchParam<Integer>       updatedByParam;
    private SearchParam<Integer>       idArticleParam;
    private SearchParam<String>        articleLibelleParam;

    /**
     * Default constructor
     */
    public StockDto() {
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
