package com.jms.domain.db;
// Generated 2015-8-22 18:08:00 by Hibernate Tools 3.2.2.GA


import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * IssueDocId generated by hbm2java
 */
@Embeddable
public class IssueDocId  implements java.io.Serializable {


     private Long idIssue;
     private Long idDocument;

    public IssueDocId() {
    }

    public IssueDocId(Long idIssue, Long idDocument) {
       this.idIssue = idIssue;
       this.idDocument = idDocument;
    }
   

    @Column(name="ID_ISSUE", nullable=false)
    public Long getIdIssue() {
        return this.idIssue;
    }
    
    public void setIdIssue(Long idIssue) {
        this.idIssue = idIssue;
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
		 if ( !(other instanceof IssueDocId) ) return false;
		 IssueDocId castOther = ( IssueDocId ) other; 
         
		 return ( (this.getIdIssue()==castOther.getIdIssue()) || ( this.getIdIssue()!=null && castOther.getIdIssue()!=null && this.getIdIssue().equals(castOther.getIdIssue()) ) )
 && ( (this.getIdDocument()==castOther.getIdDocument()) || ( this.getIdDocument()!=null && castOther.getIdDocument()!=null && this.getIdDocument().equals(castOther.getIdDocument()) ) );
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + ( getIdIssue() == null ? 0 : this.getIdIssue().hashCode() );
         result = 37 * result + ( getIdDocument() == null ? 0 : this.getIdDocument().hashCode() );
         return result;
   }   


}


