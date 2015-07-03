package com.jms.domain.db;
// Generated 2015-7-3 12:07:40 by Hibernate Tools 3.2.2.GA


import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * ProjectParticipantId generated by hbm2java
 */
@Embeddable
public class ProjectParticipantId  implements java.io.Serializable {


     private Long idProject;
     private Long idUser;

    public ProjectParticipantId() {
    }

    public ProjectParticipantId(Long idProject, Long idUser) {
       this.idProject = idProject;
       this.idUser = idUser;
    }
   

    @Column(name="ID_PROJECT", nullable=false)
    public Long getIdProject() {
        return this.idProject;
    }
    
    public void setIdProject(Long idProject) {
        this.idProject = idProject;
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
		 if ( !(other instanceof ProjectParticipantId) ) return false;
		 ProjectParticipantId castOther = ( ProjectParticipantId ) other; 
         
		 return ( (this.getIdProject()==castOther.getIdProject()) || ( this.getIdProject()!=null && castOther.getIdProject()!=null && this.getIdProject().equals(castOther.getIdProject()) ) )
 && ( (this.getIdUser()==castOther.getIdUser()) || ( this.getIdUser()!=null && castOther.getIdUser()!=null && this.getIdUser().equals(castOther.getIdUser()) ) );
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + ( getIdProject() == null ? 0 : this.getIdProject().hashCode() );
         result = 37 * result + ( getIdUser() == null ? 0 : this.getIdUser().hashCode() );
         return result;
   }   


}


