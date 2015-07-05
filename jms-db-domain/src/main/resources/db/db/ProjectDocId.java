package com.jms.domain.db;
// Generated 2015-7-5 7:21:02 by Hibernate Tools 3.2.2.GA


import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * ProjectDocId generated by hbm2java
 */
@Embeddable
public class ProjectDocId  implements java.io.Serializable {


     private Long idProject;
     private Long idDocument;

    public ProjectDocId() {
    }

    public ProjectDocId(Long idProject, Long idDocument) {
       this.idProject = idProject;
       this.idDocument = idDocument;
    }
   

    @Column(name="ID_PROJECT", nullable=false)
    public Long getIdProject() {
        return this.idProject;
    }
    
    public void setIdProject(Long idProject) {
        this.idProject = idProject;
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
		 if ( !(other instanceof ProjectDocId) ) return false;
		 ProjectDocId castOther = ( ProjectDocId ) other; 
         
		 return ( (this.getIdProject()==castOther.getIdProject()) || ( this.getIdProject()!=null && castOther.getIdProject()!=null && this.getIdProject().equals(castOther.getIdProject()) ) )
 && ( (this.getIdDocument()==castOther.getIdDocument()) || ( this.getIdDocument()!=null && castOther.getIdDocument()!=null && this.getIdDocument().equals(castOther.getIdDocument()) ) );
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + ( getIdProject() == null ? 0 : this.getIdProject().hashCode() );
         result = 37 * result + ( getIdDocument() == null ? 0 : this.getIdDocument().hashCode() );
         return result;
   }   


}


