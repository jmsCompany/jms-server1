package com.jms.domain.db;
// Generated 2015-8-15 16:28:29 by Hibernate Tools 3.2.2.GA


import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * GroupAuthoritiesId generated by hbm2java
 */
@Embeddable
public class GroupAuthoritiesId  implements java.io.Serializable {


     private Long idGroup;
     private Long idRole;

    public GroupAuthoritiesId() {
    }

    public GroupAuthoritiesId(Long idGroup, Long idRole) {
       this.idGroup = idGroup;
       this.idRole = idRole;
    }
   

    @Column(name="ID_GROUP", nullable=false)
    public Long getIdGroup() {
        return this.idGroup;
    }
    
    public void setIdGroup(Long idGroup) {
        this.idGroup = idGroup;
    }

    @Column(name="ID_ROLE", nullable=false)
    public Long getIdRole() {
        return this.idRole;
    }
    
    public void setIdRole(Long idRole) {
        this.idRole = idRole;
    }


   public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof GroupAuthoritiesId) ) return false;
		 GroupAuthoritiesId castOther = ( GroupAuthoritiesId ) other; 
         
		 return ( (this.getIdGroup()==castOther.getIdGroup()) || ( this.getIdGroup()!=null && castOther.getIdGroup()!=null && this.getIdGroup().equals(castOther.getIdGroup()) ) )
 && ( (this.getIdRole()==castOther.getIdRole()) || ( this.getIdRole()!=null && castOther.getIdRole()!=null && this.getIdRole().equals(castOther.getIdRole()) ) );
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + ( getIdGroup() == null ? 0 : this.getIdGroup().hashCode() );
         result = 37 * result + ( getIdRole() == null ? 0 : this.getIdRole().hashCode() );
         return result;
   }   


}


