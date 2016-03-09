package com.jms.domain.db;
// Generated 2016-3-9 10:11:30 by Hibernate Tools 3.2.2.GA


import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * MMainItem generated by hbm2java
 */
@Entity
@Table(name="m_main_item"
    ,catalog="jms4"
)
public class MMainItem  implements java.io.Serializable {


     private Long idMainItem;
     private MMachine MMachine;
     private MDept MDept;
     private MMainCycle MMainCycle;
     private String item;
     private Set<MMainRecord> MMainRecords = new HashSet<MMainRecord>(0);

    public MMainItem() {
    }

	
    public MMainItem(Long idMainItem) {
        this.idMainItem = idMainItem;
    }
    public MMainItem(Long idMainItem, MMachine MMachine, MDept MDept, MMainCycle MMainCycle, String item, Set<MMainRecord> MMainRecords) {
       this.idMainItem = idMainItem;
       this.MMachine = MMachine;
       this.MDept = MDept;
       this.MMainCycle = MMainCycle;
       this.item = item;
       this.MMainRecords = MMainRecords;
    }
   
     @Id 
    
    @Column(name="id_main_item", unique=true, nullable=false)
    public Long getIdMainItem() {
        return this.idMainItem;
    }
    
    public void setIdMainItem(Long idMainItem) {
        this.idMainItem = idMainItem;
    }
@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="id_machine")
    public MMachine getMMachine() {
        return this.MMachine;
    }
    
    public void setMMachine(MMachine MMachine) {
        this.MMachine = MMachine;
    }
@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="id_dept")
    public MDept getMDept() {
        return this.MDept;
    }
    
    public void setMDept(MDept MDept) {
        this.MDept = MDept;
    }
@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="id_main_cycle")
    public MMainCycle getMMainCycle() {
        return this.MMainCycle;
    }
    
    public void setMMainCycle(MMainCycle MMainCycle) {
        this.MMainCycle = MMainCycle;
    }
    
    @Column(name="item", length=256)
    public String getItem() {
        return this.item;
    }
    
    public void setItem(String item) {
        this.item = item;
    }
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="MMainItem")
    public Set<MMainRecord> getMMainRecords() {
        return this.MMainRecords;
    }
    
    public void setMMainRecords(Set<MMainRecord> MMainRecords) {
        this.MMainRecords = MMainRecords;
    }




}


