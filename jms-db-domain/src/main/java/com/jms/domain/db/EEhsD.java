package com.jms.domain.db;
// Generated 2016-3-31 16:15:17 by Hibernate Tools 3.2.2.GA


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * EEhsD generated by hbm2java
 */
@Entity
@Table(name="e_ehs_d"
    ,catalog="jms5"
)
public class EEhsD  implements java.io.Serializable {


     private Long idEhsD;
     private EEhs EEhs;
     private ECategoryItem ECategoryItem;
     private EResult EResult;
     private ELevelDic ELevelDic;
     private String control;

    public EEhsD() {
    }

    public EEhsD(EEhs EEhs, ECategoryItem ECategoryItem, EResult EResult, ELevelDic ELevelDic, String control) {
       this.EEhs = EEhs;
       this.ECategoryItem = ECategoryItem;
       this.EResult = EResult;
       this.ELevelDic = ELevelDic;
       this.control = control;
    }
   
     @Id @GeneratedValue(strategy=IDENTITY)
    
    @Column(name="id_ehs_d", unique=true, nullable=false)
    public Long getIdEhsD() {
        return this.idEhsD;
    }
    
    public void setIdEhsD(Long idEhsD) {
        this.idEhsD = idEhsD;
    }
@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="id_ehs")
    public EEhs getEEhs() {
        return this.EEhs;
    }
    
    public void setEEhs(EEhs EEhs) {
        this.EEhs = EEhs;
    }
@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="id_category_item")
    public ECategoryItem getECategoryItem() {
        return this.ECategoryItem;
    }
    
    public void setECategoryItem(ECategoryItem ECategoryItem) {
        this.ECategoryItem = ECategoryItem;
    }
@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="id_result")
    public EResult getEResult() {
        return this.EResult;
    }
    
    public void setEResult(EResult EResult) {
        this.EResult = EResult;
    }
@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="residual")
    public ELevelDic getELevelDic() {
        return this.ELevelDic;
    }
    
    public void setELevelDic(ELevelDic ELevelDic) {
        this.ELevelDic = ELevelDic;
    }
    
    @Column(name="control", length=1024)
    public String getControl() {
        return this.control;
    }
    
    public void setControl(String control) {
        this.control = control;
    }




}


