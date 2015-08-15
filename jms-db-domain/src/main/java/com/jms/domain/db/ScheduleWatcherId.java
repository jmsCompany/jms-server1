package com.jms.domain.db;
// Generated 2015-8-15 16:28:29 by Hibernate Tools 3.2.2.GA


import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * ScheduleWatcherId generated by hbm2java
 */
@Embeddable
public class ScheduleWatcherId  implements java.io.Serializable {


     private Long idUser;
     private Long idSchedule;

    public ScheduleWatcherId() {
    }

    public ScheduleWatcherId(Long idUser, Long idSchedule) {
       this.idUser = idUser;
       this.idSchedule = idSchedule;
    }
   

    @Column(name="ID_USER", nullable=false)
    public Long getIdUser() {
        return this.idUser;
    }
    
    public void setIdUser(Long idUser) {
        this.idUser = idUser;
    }

    @Column(name="ID_SCHEDULE", nullable=false)
    public Long getIdSchedule() {
        return this.idSchedule;
    }
    
    public void setIdSchedule(Long idSchedule) {
        this.idSchedule = idSchedule;
    }


   public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof ScheduleWatcherId) ) return false;
		 ScheduleWatcherId castOther = ( ScheduleWatcherId ) other; 
         
		 return ( (this.getIdUser()==castOther.getIdUser()) || ( this.getIdUser()!=null && castOther.getIdUser()!=null && this.getIdUser().equals(castOther.getIdUser()) ) )
 && ( (this.getIdSchedule()==castOther.getIdSchedule()) || ( this.getIdSchedule()!=null && castOther.getIdSchedule()!=null && this.getIdSchedule().equals(castOther.getIdSchedule()) ) );
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + ( getIdUser() == null ? 0 : this.getIdUser().hashCode() );
         result = 37 * result + ( getIdSchedule() == null ? 0 : this.getIdSchedule().hashCode() );
         return result;
   }   


}


