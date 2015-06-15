package com.jms.domain.db;
// Generated 2015-6-14 15:39:31 by Hibernate Tools 3.2.2.GA


import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * GroupMembersId generated by hbm2java
 */
@Embeddable
public class GroupMembersId  implements java.io.Serializable {


     private int idGroup;
     private int idUser;

    public GroupMembersId() {
    }

    public GroupMembersId(int idGroup, int idUser) {
       this.idGroup = idGroup;
       this.idUser = idUser;
    }
   

    @Column(name="ID_GROUP", nullable=false)
    public int getIdGroup() {
        return this.idGroup;
    }
    
    public void setIdGroup(int idGroup) {
        this.idGroup = idGroup;
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
		 if ( !(other instanceof GroupMembersId) ) return false;
		 GroupMembersId castOther = ( GroupMembersId ) other; 
         
		 return (this.getIdGroup()==castOther.getIdGroup())
 && (this.getIdUser()==castOther.getIdUser());
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + this.getIdGroup();
         result = 37 * result + this.getIdUser();
         return result;
   }   


}


