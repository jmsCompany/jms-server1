package com.jms.domain.db;
// Generated 2015-6-6 20:38:20 by Hibernate Tools 3.2.2.GA


import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * ProjectDocId generated by hbm2java
 */
@Embeddable
public class ProjectDocId  implements java.io.Serializable {


     private int idProject;
     private int idDocument;

    public ProjectDocId() {
    }

    public ProjectDocId(int idProject, int idDocument) {
       this.idProject = idProject;
       this.idDocument = idDocument;
    }
   

    @Column(name="ID_PROJECT", nullable=false)
    public int getIdProject() {
        return this.idProject;
    }
    
    public void setIdProject(int idProject) {
        this.idProject = idProject;
    }

    @Column(name="ID_DOCUMENT", nullable=false)
    public int getIdDocument() {
        return this.idDocument;
    }
    
    public void setIdDocument(int idDocument) {
        this.idDocument = idDocument;
    }


   public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof ProjectDocId) ) return false;
		 ProjectDocId castOther = ( ProjectDocId ) other; 
         
		 return (this.getIdProject()==castOther.getIdProject())
 && (this.getIdDocument()==castOther.getIdDocument());
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + this.getIdProject();
         result = 37 * result + this.getIdDocument();
         return result;
   }   


}


