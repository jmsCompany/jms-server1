package com.jms.domain.db;
// Generated 2015-6-16 15:01:20 by Hibernate Tools 3.2.2.GA


import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * RolePrivId generated by hbm2java
 */
@Embeddable
public class RolePrivId  implements java.io.Serializable {


     private int idModule;
     private int idRole;

    public RolePrivId() {
    }

    public RolePrivId(int idModule, int idRole) {
       this.idModule = idModule;
       this.idRole = idRole;
    }
   

    @Column(name="ID_MODULE", nullable=false)
    public int getIdModule() {
        return this.idModule;
    }
    
    public void setIdModule(int idModule) {
        this.idModule = idModule;
    }

    @Column(name="ID_ROLE", nullable=false)
    public int getIdRole() {
        return this.idRole;
    }
    
    public void setIdRole(int idRole) {
        this.idRole = idRole;
    }


   public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof RolePrivId) ) return false;
		 RolePrivId castOther = ( RolePrivId ) other; 
         
		 return (this.getIdModule()==castOther.getIdModule())
 && (this.getIdRole()==castOther.getIdRole());
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + this.getIdModule();
         result = 37 * result + this.getIdRole();
         return result;
   }   


}


