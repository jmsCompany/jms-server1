package com.jms.domain.db;
// Generated 2016-3-31 16:15:17 by Hibernate Tools 3.2.2.GA


import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * GroupMembersId generated by hbm2java
 */
@Embeddable
public class GroupMembersId  implements java.io.Serializable {


     private Long idGroup;
     private Long idUser;

    public GroupMembersId() {
    }

    public GroupMembersId(Long idGroup, Long idUser) {
       this.idGroup = idGroup;
       this.idUser = idUser;
    }
   

    @Column(name="id_group", nullable=false)
    public Long getIdGroup() {
        return this.idGroup;
    }
    
    public void setIdGroup(Long idGroup) {
        this.idGroup = idGroup;
    }

    @Column(name="id_user", nullable=false)
    public Long getIdUser() {
        return this.idUser;
    }
    
    public void setIdUser(Long idUser) {
        this.idUser = idUser;
    }


   public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof GroupMembersId) ) return false;
		 GroupMembersId castOther = ( GroupMembersId ) other; 
         
		 return ( (this.getIdGroup()==castOther.getIdGroup()) || ( this.getIdGroup()!=null && castOther.getIdGroup()!=null && this.getIdGroup().equals(castOther.getIdGroup()) ) )
 && ( (this.getIdUser()==castOther.getIdUser()) || ( this.getIdUser()!=null && castOther.getIdUser()!=null && this.getIdUser().equals(castOther.getIdUser()) ) );
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + ( getIdGroup() == null ? 0 : this.getIdGroup().hashCode() );
         result = 37 * result + ( getIdUser() == null ? 0 : this.getIdUser().hashCode() );
         return result;
   }   


}


