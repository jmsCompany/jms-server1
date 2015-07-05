package com.jms.domain.db;
// Generated 2015-7-5 7:21:02 by Hibernate Tools 3.2.2.GA


import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * TaskDocId generated by hbm2java
 */
@Embeddable
public class TaskDocId  implements java.io.Serializable {


     private Long idTask;
     private Long idDocument;

    public TaskDocId() {
    }

    public TaskDocId(Long idTask, Long idDocument) {
       this.idTask = idTask;
       this.idDocument = idDocument;
    }
   

    @Column(name="ID_TASK", nullable=false)
    public Long getIdTask() {
        return this.idTask;
    }
    
    public void setIdTask(Long idTask) {
        this.idTask = idTask;
    }

    @Column(name="ID_DOCUMENT", nullable=false)
    public Long getIdDocument() {
        return this.idDocument;
    }
    
    public void setIdDocument(Long idDocument) {
        this.idDocument = idDocument;
    }


   public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof TaskDocId) ) return false;
		 TaskDocId castOther = ( TaskDocId ) other; 
         
		 return ( (this.getIdTask()==castOther.getIdTask()) || ( this.getIdTask()!=null && castOther.getIdTask()!=null && this.getIdTask().equals(castOther.getIdTask()) ) )
 && ( (this.getIdDocument()==castOther.getIdDocument()) || ( this.getIdDocument()!=null && castOther.getIdDocument()!=null && this.getIdDocument().equals(castOther.getIdDocument()) ) );
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + ( getIdTask() == null ? 0 : this.getIdTask().hashCode() );
         result = 37 * result + ( getIdDocument() == null ? 0 : this.getIdDocument().hashCode() );
         return result;
   }   


}


