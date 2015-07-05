package com.jms.domain.db;
// Generated 2015-7-5 10:54:07 by Hibernate Tools 3.2.2.GA


import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * UserAppId generated by hbm2java
 */
@Embeddable
public class UserAppId  implements java.io.Serializable {


     private Long idUser;
     private Long idApp;

    public UserAppId() {
    }

    public UserAppId(Long idUser, Long idApp) {
       this.idUser = idUser;
       this.idApp = idApp;
    }
   

    @Column(name="ID_USER", nullable=false)
    public Long getIdUser() {
        return this.idUser;
    }
    
    public void setIdUser(Long idUser) {
        this.idUser = idUser;
    }

    @Column(name="ID_APP", nullable=false)
    public Long getIdApp() {
        return this.idApp;
    }
    
    public void setIdApp(Long idApp) {
        this.idApp = idApp;
    }


   public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof UserAppId) ) return false;
		 UserAppId castOther = ( UserAppId ) other; 
         
		 return ( (this.getIdUser()==castOther.getIdUser()) || ( this.getIdUser()!=null && castOther.getIdUser()!=null && this.getIdUser().equals(castOther.getIdUser()) ) )
 && ( (this.getIdApp()==castOther.getIdApp()) || ( this.getIdApp()!=null && castOther.getIdApp()!=null && this.getIdApp().equals(castOther.getIdApp()) ) );
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + ( getIdUser() == null ? 0 : this.getIdUser().hashCode() );
         result = 37 * result + ( getIdApp() == null ? 0 : this.getIdApp().hashCode() );
         return result;
   }   


}


