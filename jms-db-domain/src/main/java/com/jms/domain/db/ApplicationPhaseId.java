package com.jms.domain.db;
// Generated 2015-7-5 10:54:07 by Hibernate Tools 3.2.2.GA


import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * ApplicationPhaseId generated by hbm2java
 */
@Embeddable
public class ApplicationPhaseId  implements java.io.Serializable {


     private Long idApplication;
     private Long idApprovalPhase;

    public ApplicationPhaseId() {
    }

    public ApplicationPhaseId(Long idApplication, Long idApprovalPhase) {
       this.idApplication = idApplication;
       this.idApprovalPhase = idApprovalPhase;
    }
   

    @Column(name="ID_APPLICATION", nullable=false)
    public Long getIdApplication() {
        return this.idApplication;
    }
    
    public void setIdApplication(Long idApplication) {
        this.idApplication = idApplication;
    }

    @Column(name="ID_APPROVAL_PHASE", nullable=false)
    public Long getIdApprovalPhase() {
        return this.idApprovalPhase;
    }
    
    public void setIdApprovalPhase(Long idApprovalPhase) {
        this.idApprovalPhase = idApprovalPhase;
    }


   public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof ApplicationPhaseId) ) return false;
		 ApplicationPhaseId castOther = ( ApplicationPhaseId ) other; 
         
		 return ( (this.getIdApplication()==castOther.getIdApplication()) || ( this.getIdApplication()!=null && castOther.getIdApplication()!=null && this.getIdApplication().equals(castOther.getIdApplication()) ) )
 && ( (this.getIdApprovalPhase()==castOther.getIdApprovalPhase()) || ( this.getIdApprovalPhase()!=null && castOther.getIdApprovalPhase()!=null && this.getIdApprovalPhase().equals(castOther.getIdApprovalPhase()) ) );
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + ( getIdApplication() == null ? 0 : this.getIdApplication().hashCode() );
         result = 37 * result + ( getIdApprovalPhase() == null ? 0 : this.getIdApprovalPhase().hashCode() );
         return result;
   }   


}


