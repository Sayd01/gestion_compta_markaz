/*
 * Created on 2023-08-08 ( Time 19:02:56 )
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
 * Persistent class for entity stored in table "utilisateur"
 *
 * @author Telosys Tools Generator
 *
 */
@Data
@ToString
@Entity
@Table(name="utilisateur" )
public class Utilisateur implements Serializable, Cloneable {

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

    @Column(name="first_name", length=255)
    private String     firstName    ;

    @Column(name="last_name", length=255)
    private String     lastName     ;

    @Column(name="email", length=255)
    private String     email        ;

    @Column(name="image_url", length=255)
    private String     imageUrl     ;

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

    @Column(name="password", length=255)
    private String     password     ;


    @Column(name="is_fist_connexion")
    private Boolean            isFirstConnexion = true;

	// "idMarkaz" (column "id_markaz") is not defined by itself because used as FK in a link 



    //----------------------------------------------------------------------
    // CONSTRUCTOR(S)
    //----------------------------------------------------------------------
    public Utilisateur() {
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
