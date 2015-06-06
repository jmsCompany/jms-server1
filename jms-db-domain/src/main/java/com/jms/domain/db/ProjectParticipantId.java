package com.jms.domain.db;
// Generated 2015-6-6 20:38:20 by Hibernate Tools 3.2.2.GA


import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * ProjectParticipantId generated by hbm2java
 */
@Embeddable
public class ProjectParticipantId  implements java.io.Serializable {


     private int idProject;
     private int idUser;

    public ProjectParticipantId() {
    }

    public ProjectParticipantId(int idProject, int idUser) {
       this.idProject = idProject;
       this.idUser = idUser;
    }
   

    @Column(name="ID_PROJECT", nullable=false)
    public int getIdProject() {
        return this.idProject;
    }
    
    public void setIdProject(int idProject) {
        this.idProject = idProject;
    }

    @Column(name="ID_USER", nullable=false)
    public int getIdUser() {
        return this.idUser;
    }
    
    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }


   public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof ProjectParticipantId) ) return false;
		 ProjectParticipantId castOther = ( ProjectParticipantId ) other; 
         
		 return (this.getIdProject()==castOther.getIdProject())
 && (this.getIdUser()==castOther.getIdUser());
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + this.getIdProject();
         result = 37 * result + this.getIdUser();
         return result;
   }   


}


