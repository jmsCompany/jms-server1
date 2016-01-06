package com.jms.domain.db;
// Generated 2016-1-6 12:39:14 by Hibernate Tools 3.2.2.GA


import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * WIssueDocId generated by hbm2java
 */
@Embeddable
public class WIssueDocId  implements java.io.Serializable {


     private Long idIssue;
     private Long idDocument;

    public WIssueDocId() {
    }

    public WIssueDocId(Long idIssue, Long idDocument) {
       this.idIssue = idIssue;
       this.idDocument = idDocument;
    }
   

    @Column(name="id_issue", nullable=false)
    public Long getIdIssue() {
        return this.idIssue;
    }
    
    public void setIdIssue(Long idIssue) {
        this.idIssue = idIssue;
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
		 if ( !(other instanceof WIssueDocId) ) return false;
		 WIssueDocId castOther = ( WIssueDocId ) other; 
         
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


