package com.jms.domain.db;
// Generated 2016-3-9 10:11:30 by Hibernate Tools 3.2.2.GA


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * DFaiInFinalD generated by hbm2java
 */
@Entity
@Table(name="d_fai_in_final_d"
    ,catalog="jms4"
)
public class DFaiInFinalD  implements java.io.Serializable {


     private Long idFaiInFinalD;

    public DFaiInFinalD() {
    }

    public DFaiInFinalD(Long idFaiInFinalD) {
       this.idFaiInFinalD = idFaiInFinalD;
    }
   
     @Id 
    
    @Column(name="id_fai_in_final_d", unique=true, nullable=false)
    public Long getIdFaiInFinalD() {
        return this.idFaiInFinalD;
    }
    
    public void setIdFaiInFinalD(Long idFaiInFinalD) {
        this.idFaiInFinalD = idFaiInFinalD;
    }




}


