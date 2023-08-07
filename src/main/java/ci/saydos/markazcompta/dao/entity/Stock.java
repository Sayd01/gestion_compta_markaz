/*
 * Created on 2023-08-06 ( Time 01:31:05 )
 * Generated by Telosys Tools Generator ( version 3.3.0 )
 */
// This Bean has a basic Primary Key (not composite) 

package ci.saydos.markazcompta.dao.entity;

import java.io.Serializable;

import ci.saydos.markazcompta.utils.enums.EtatStockEnum;
import jakarta.persistence.*;

import lombok.*;


import java.util.Date;
import java.util.List;


/**
 * Persistent class for entity stored in table "stock"
 *
 * @author Telosys Tools Generator
 *
 */
@Data
@ToString
@Entity
@Table(name="stock" )
public class Stock implements Serializable, Cloneable {

    private static final long serialVersionUID = 1L;

    //----------------------------------------------------------------------
    // ENTITY PRIMARY KEY ( BASED ON A SINGLE FIELD )
    //----------------------------------------------------------------------
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id", nullable=false)
    private Integer    id           ;


    //----------------------------------------------------------------------
    // ENTITY DATA FIELDS 
    //----------------------------------------------------------------------    
    @Column(name="intitule", length=2555)
    private String     intitule     ;

    @Column(name="quantite")
    private Integer    quantite     ;

    @Enumerated(EnumType.STRING)
    private EtatStockEnum etat         ;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="date_entree")
    private Date       dateEntree   ;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="date_sortie")
    private Date       dateSortie   ;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="created_at")
    private Date       createdAt    ;

    @Column(name="created_by")
    private Integer    createdBy    ;

    @Column(name="is_deleted")
    private Boolean    isDeleted    ;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="updated_at")
    private Date       updatedAt    ;

    @Column(name="updated_by")
    private Integer    updatedBy    ;


    //----------------------------------------------------------------------
    // ENTITY LINKS ( RELATIONSHIP )
    //----------------------------------------------------------------------

    //----------------------------------------------------------------------
    // CONSTRUCTOR(S)
    //----------------------------------------------------------------------
    public Stock() {
		super();
    }
    
	//----------------------------------------------------------------------
    // clone METHOD
    //----------------------------------------------------------------------
	@Override
	public java.lang.Object clone() throws CloneNotSupportedException {
		return super.clone();
	}
}
