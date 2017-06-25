package com.jms.domain.db;
// Generated 2016-3-31 16:15:17 by Hibernate Tools 3.2.2.GA


import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * WTaskParticipantId generated by hbm2java
 */
@Embeddable
public class SMaterialCocompanyId  implements java.io.Serializable {


     private Long idCompany;
     private Long idMat;
     private Long idCocompany;

    public SMaterialCocompanyId() {
    }

    public SMaterialCocompanyId(Long idCompany, Long idMat,Long idCocompany) {
       this.setIdCompany(idCompany);
       this.setIdMat(idMat);
       this.setIdCocompany(idCocompany);
    }
   

    @Column(name="id_company", nullable=false)
    public Long getIdCompany() {
    	return idCompany;
    }

    public void setIdCompany(Long idCompany) {
    	this.idCompany = idCompany;
    }

    @Column(name="id_mat", nullable=false)
    public Long getIdMat() {
    	return idMat;
    }

    public void setIdMat(Long idMat) {
    	this.idMat = idMat;
    }

    @Column(name="id_cocompany", nullable=false)
    public Long getIdCocompany() {
    	return idCocompany;
    }

    public void setIdCocompany(Long idCocompany) {
    	this.idCocompany = idCocompany;
    }   

   public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof SMaterialCocompanyId) ) return false;
		 SMaterialCocompanyId castOther = ( SMaterialCocompanyId ) other; 
         
		 return ( (this.getIdCompany()==castOther.getIdCompany()) || ( this.getIdCompany()!=null && castOther.getIdCompany()!=null && this.getIdCompany().equals(castOther.getIdCompany()) ) )
 && ( (this.getIdCocompany()==castOther.getIdCocompany()) || ( this.getIdCocompany()!=null && castOther.getIdCocompany()!=null && this.getIdCocompany().equals(castOther.getIdCocompany()) ) )
 && ( (this.getIdMat()==castOther.getIdMat()) || ( this.getIdMat()!=null && castOther.getIdMat()!=null && this.getIdMat().equals(castOther.getIdMat()) ) )
 ;
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + ( getIdCompany() == null ? 0 : this.getIdCompany().hashCode() );
         result = 37 * result + ( getIdCocompany() == null ? 0 : this.getIdCocompany().hashCode() );
         result = 37 * result + ( getIdCompany() == null ? 0 : this.getIdCompany().hashCode() );
         return result;
   }




}


