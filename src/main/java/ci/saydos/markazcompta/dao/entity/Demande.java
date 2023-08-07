/*
 * Created on 2023-08-06 ( Time 01:31:04 )
 * Generated by Telosys Tools Generator ( version 3.3.0 )
 */
// This Bean has a basic Primary Key (not composite) 

package ci.saydos.markazcompta.dao.entity;

import java.io.Serializable;
import jakarta.persistence.*;

import lombok.*;


import java.util.Date;
import java.util.List;


/**
 * Persistent class for entity stored in table "demande"
 *
 * @author Telosys Tools Generator
 *
 */
@Data
@ToString
@Entity
@Table(name="demande" )
public class Demande implements Serializable, Cloneable {

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
    @Column(name="label", length=2555)
    private String     label        ;

    @Column(name="code", length=2555)
    private String     code         ;

    @Column(name="description", length=2555)
    private String     description  ;

    @Column(name="montant")
    private Double     montant      ;

    @Column(name="image_url", length=255)
    private String     imageUrl     ;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="date_debut")
    private Date       dateDebut    ;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="date_fin")
    private Date       dateFin      ;

    @Column(name="statut", length=9)
    private String     statut       ;

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

	// "idUtilisateur" (column "id_utilisateur") is not defined by itself because used as FK in a link 

    //----------------------------------------------------------------------
    // ENTITY LINKS ( RELATIONSHIP )
    //----------------------------------------------------------------------
    @ManyToOne
    @JoinColumn(name="id_utilisateur", referencedColumnName="id")
    private Utilisateur utilisateur ;

    //----------------------------------------------------------------------
    // CONSTRUCTOR(S)
    //----------------------------------------------------------------------
    public Demande() {
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