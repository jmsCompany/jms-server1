package com.jms.domain.db;
// Generated 2016-3-31 16:15:17 by Hibernate Tools 3.2.2.GA


import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * GroupAuthorities generated by hbm2java
 */
@Entity
@Table(name="p_cpp_op"
)
public class PCppOp  implements java.io.Serializable {


     private PCppOpId id;

    public PCppOp() {
    }

	
    public PCppOp(PCppOpId id) {
        this.id = id;

    }
   
     @EmbeddedId
    
    @AttributeOverrides( {
        @AttributeOverride(name="idCpp", column=@Column(name="id_cpp", nullable=false) ), 
        @AttributeOverride(name="idUser", column=@Column(name="id_user", nullable=false) ) } )
    public PCppOpId getId() {
        return this.id;
    }
    
    public void setId(PCppOpId id) {
        this.id = id;
    }



}


