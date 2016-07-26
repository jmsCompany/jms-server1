package com.jms.domain.db;
// Generated 2016-3-31 16:15:17 by Hibernate Tools 3.2.2.GA


import javax.persistence.Column;
import javax.persistence.Embeddable;


@Embeddable
public class SMaterialBinsId  implements java.io.Serializable {



    private Long idMaterial;
    private Long idBin;

   public SMaterialBinsId() {
   }

   public SMaterialBinsId(Long idMaterial, Long idBin) {
      this.idMaterial = idMaterial;
      this.idBin = idBin;
   }
  

   @Column(name="id_material", nullable=false)
   public Long getIdMaterial() {
       return this.idMaterial;
   }
   
   public void setIdMaterial(Long idMaterial) {
       this.idMaterial = idMaterial;
   }

   @Column(name="id_bin", nullable=false)
   public Long getIdBin() {
       return this.idBin;
   }
   
   public void setIdBin(Long idBin) {
       this.idBin = idBin;
   }


  public boolean equals(Object other) {
        if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof SMaterialBinsId) ) return false;
		 SMaterialBinsId castOther = ( SMaterialBinsId ) other; 
        
		 return ( (this.getIdMaterial()==castOther.getIdMaterial()) || ( this.getIdMaterial()!=null && castOther.getIdMaterial()!=null && this.getIdMaterial().equals(castOther.getIdMaterial()) ) )
&& ( (this.getIdBin()==castOther.getIdBin()) || ( this.getIdBin()!=null && castOther.getIdBin()!=null && this.getIdBin().equals(castOther.getIdBin()) ) );
  }
  
  public int hashCode() {
        int result = 17;
        
        result = 37 * result + ( getIdMaterial() == null ? 0 : this.getIdMaterial().hashCode() );
        result = 37 * result + ( getIdBin() == null ? 0 : this.getIdBin().hashCode() );
        return result;
  }   



}


