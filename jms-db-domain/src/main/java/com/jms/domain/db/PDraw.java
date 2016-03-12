package com.jms.domain.db;
// Generated 2016-3-9 10:11:30 by Hibernate Tools 3.2.2.GA


import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * PDraw generated by hbm2java
 */
@Entity
@Table(name="p_draw"
    ,catalog="jms4"
)
public class PDraw  implements java.io.Serializable {


     private Long idDraw;
     private Long drawNo;
     private String drawVer;
     private String drawAtt;
     private Set<PRoutine> PRoutines = new HashSet<PRoutine>(0);

    public PDraw() {
    }

	
    public PDraw(Long idDraw) {
        this.idDraw = idDraw;
    }
    public PDraw(Long idDraw, Long drawNo, String drawVer, String drawAtt, Set<PRoutine> PRoutines) {
       this.idDraw = idDraw;
       this.drawNo = drawNo;
       this.drawVer = drawVer;
       this.drawAtt = drawAtt;
       this.PRoutines = PRoutines;
    }
   
     @Id 
    
    @Column(name="id_draw", unique=true, nullable=false)
    public Long getIdDraw() {
        return this.idDraw;
    }
    
    public void setIdDraw(Long idDraw) {
        this.idDraw = idDraw;
    }
    
    @Column(name="draw_no")
    public Long getDrawNo() {
        return this.drawNo;
    }
    
    public void setDrawNo(Long drawNo) {
        this.drawNo = drawNo;
    }
    
    @Column(name="draw_ver", length=20)
    public String getDrawVer() {
        return this.drawVer;
    }
    
    public void setDrawVer(String drawVer) {
        this.drawVer = drawVer;
    }
    
    @Column(name="draw_att", length=20)
    public String getDrawAtt() {
        return this.drawAtt;
    }
    
    public void setDrawAtt(String drawAtt) {
        this.drawAtt = drawAtt;
    }
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="PDraw")
    public Set<PRoutine> getPRoutines() {
        return this.PRoutines;
    }
    
    public void setPRoutines(Set<PRoutine> PRoutines) {
        this.PRoutines = PRoutines;
    }




}

