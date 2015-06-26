package com.jms.domain.db;
// Generated 2015-6-25 9:56:24 by Hibernate Tools 3.2.2.GA


import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * SectorMemberId generated by hbm2java
 */
@Embeddable
public class SectorMemberId  implements java.io.Serializable {


     private Long idSector;
     private Long idUser;

    public SectorMemberId() {
    }

    public SectorMemberId(Long idSector, Long idUser) {
       this.idSector = idSector;
       this.idUser = idUser;
    }
   

    @Column(name="ID_SECTOR", nullable=false)
    public Long getIdSector() {
        return this.idSector;
    }
    
    public void setIdSector(Long idSector) {
        this.idSector = idSector;
    }

    @Column(name="ID_USER", nullable=false)
    public Long getIdUser() {
        return this.idUser;
    }
    
    public void setIdUser(Long idUser) {
        this.idUser = idUser;
    }


   public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof SectorMemberId) ) return false;
		 SectorMemberId castOther = ( SectorMemberId ) other; 
         
		 return ( (this.getIdSector()==castOther.getIdSector()) || ( this.getIdSector()!=null && castOther.getIdSector()!=null && this.getIdSector().equals(castOther.getIdSector()) ) )
 && ( (this.getIdUser()==castOther.getIdUser()) || ( this.getIdUser()!=null && castOther.getIdUser()!=null && this.getIdUser().equals(castOther.getIdUser()) ) );
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + ( getIdSector() == null ? 0 : this.getIdSector().hashCode() );
         result = 37 * result + ( getIdUser() == null ? 0 : this.getIdUser().hashCode() );
         return result;
   }   


}


