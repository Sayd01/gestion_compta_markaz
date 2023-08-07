/*
 * Created on 2023-08-06 ( Time 01:31:05 )
 * Generated by Telosys Tools Generator ( version 3.3.0 )
 */
// This Bean has a basic Primary Key (not composite) 

package ci.saydos.markazcompta.dao.entity;

import java.io.Serializable;
import jakarta.persistence.*;

import lombok.*;


import java.util.Date;


/**
 * Persistent class for entity stored in table "direction"
 *
 * @author Telosys Tools Generator
 *
 */
@Data
@ToString
@Entity
@Table(name="direction" )
public class Direction implements Serializable, Cloneable {

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
    @Column(name="code", length=255)
    private String     code         ;

    @Column(name="intitule", length=255)
    private String     intitule     ;

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

	// "idMarkaz" (column "id_markaz") is not defined by itself because used as FK in a link 

    //----------------------------------------------------------------------
    // ENTITY LINKS ( RELATIONSHIP )
    //----------------------------------------------------------------------
    @ManyToOne
    @JoinColumn(name="id_markaz", referencedColumnName="id")
    private Markaz markaz      ;

    //----------------------------------------------------------------------
    // CONSTRUCTOR(S)
    //----------------------------------------------------------------------
    public Direction() {
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
