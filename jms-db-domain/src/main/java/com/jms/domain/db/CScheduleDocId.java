package com.jms.domain.db;
// Generated 2016-3-31 16:15:17 by Hibernate Tools 3.2.2.GA


import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * CScheduleDocId generated by hbm2java
 */
@Embeddable
public class CScheduleDocId  implements java.io.Serializable {


     private Long idSchedule;
     private Long idDocument;

    public CScheduleDocId() {
    }

    public CScheduleDocId(Long idSchedule, Long idDocument) {
       this.idSchedule = idSchedule;
       this.idDocument = idDocument;
    }
   

    @Column(name="id_schedule", nullable=false)
    public Long getIdSchedule() {
        return this.idSchedule;
    }
    
    public void setIdSchedule(Long idSchedule) {
        this.idSchedule = idSchedule;
    }

    @Column(name="id_document", nullable=false)
    public Long getIdDocument() {
        return this.idDocument;
    }
    
    public void setIdDocument(Long idDocument) {
        this.idDocument = idDocument;
    }


   public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof CScheduleDocId) ) return false;
		 CScheduleDocId castOther = ( CScheduleDocId ) other; 
         
		 return ( (this.getIdSchedule()==castOther.getIdSchedule()) || ( this.getIdSchedule()!=null && castOther.getIdSchedule()!=null && this.getIdSchedule().equals(castOther.getIdSchedule()) ) )
 && ( (this.getIdDocument()==castOther.getIdDocument()) || ( this.getIdDocument()!=null && castOther.getIdDocument()!=null && this.getIdDocument().equals(castOther.getIdDocument()) ) );
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + ( getIdSchedule() == null ? 0 : this.getIdSchedule().hashCode() );
         result = 37 * result + ( getIdDocument() == null ? 0 : this.getIdDocument().hashCode() );
         return result;
   }   


}


