package com.jms.domain.db;
// Generated 2015-7-22 10:30:44 by Hibernate Tools 3.2.2.GA


import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * VoteId generated by hbm2java
 */
@Embeddable
public class VoteId  implements java.io.Serializable {


     private Long idParticipant;
     private Long idItem;

    public VoteId() {
    }

    public VoteId(Long idParticipant, Long idItem) {
       this.idParticipant = idParticipant;
       this.idItem = idItem;
    }
   

    @Column(name="ID_PARTICIPANT", nullable=false)
    public Long getIdParticipant() {
        return this.idParticipant;
    }
    
    public void setIdParticipant(Long idParticipant) {
        this.idParticipant = idParticipant;
    }

    @Column(name="ID_ITEM", nullable=false)
    public Long getIdItem() {
        return this.idItem;
    }
    
    public void setIdItem(Long idItem) {
        this.idItem = idItem;
    }


   public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof VoteId) ) return false;
		 VoteId castOther = ( VoteId ) other; 
         
		 return ( (this.getIdParticipant()==castOther.getIdParticipant()) || ( this.getIdParticipant()!=null && castOther.getIdParticipant()!=null && this.getIdParticipant().equals(castOther.getIdParticipant()) ) )
 && ( (this.getIdItem()==castOther.getIdItem()) || ( this.getIdItem()!=null && castOther.getIdItem()!=null && this.getIdItem().equals(castOther.getIdItem()) ) );
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + ( getIdParticipant() == null ? 0 : this.getIdParticipant().hashCode() );
         result = 37 * result + ( getIdItem() == null ? 0 : this.getIdItem().hashCode() );
         return result;
   }   


}


