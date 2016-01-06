package com.jms.domain.db;
// Generated 2016-1-6 12:39:14 by Hibernate Tools 3.2.2.GA


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
 * SIncomeMaterial generated by hbm2java
 */
@Entity
@Table(name="s_income_material"
    ,catalog="jms3"
)
public class SIncomeMaterial  implements java.io.Serializable {


     private Long id;
     private SStk SStk;
     private SMaterial SMaterial;
     private SIncome SIncome;
     private SStatusDic SStatusDic;
     private Long qtyPo;
     private Long qtyReceived;
     private Long qty;

    public SIncomeMaterial() {
    }

    public SIncomeMaterial(SStk SStk, SMaterial SMaterial, SIncome SIncome, SStatusDic SStatusDic, Long qtyPo, Long qtyReceived, Long qty) {
       this.SStk = SStk;
       this.SMaterial = SMaterial;
       this.SIncome = SIncome;
       this.SStatusDic = SStatusDic;
       this.qtyPo = qtyPo;
       this.qtyReceived = qtyReceived;
       this.qty = qty;
    }
   
     @Id @GeneratedValue(strategy=IDENTITY)
    
    @Column(name="id", unique=true, nullable=false)
    public Long getId() {
        return this.id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="stk")
    public SStk getSStk() {
        return this.SStk;
    }
    
    public void setSStk(SStk SStk) {
        this.SStk = SStk;
    }
@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="material")
    public SMaterial getSMaterial() {
        return this.SMaterial;
    }
    
    public void setSMaterial(SMaterial SMaterial) {
        this.SMaterial = SMaterial;
    }
@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="id_r")
    public SIncome getSIncome() {
        return this.SIncome;
    }
    
    public void setSIncome(SIncome SIncome) {
        this.SIncome = SIncome;
    }
@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="status_inspection")
    public SStatusDic getSStatusDic() {
        return this.SStatusDic;
    }
    
    public void setSStatusDic(SStatusDic SStatusDic) {
        this.SStatusDic = SStatusDic;
    }
    
    @Column(name="qty_po", precision=10, scale=0)
    public Long getQtyPo() {
        return this.qtyPo;
    }
    
    public void setQtyPo(Long qtyPo) {
        this.qtyPo = qtyPo;
    }
    
    @Column(name="qty_received", precision=10, scale=0)
    public Long getQtyReceived() {
        return this.qtyReceived;
    }
    
    public void setQtyReceived(Long qtyReceived) {
        this.qtyReceived = qtyReceived;
    }
    
    @Column(name="qty", precision=10, scale=0)
    public Long getQty() {
        return this.qty;
    }
    
    public void setQty(Long qty) {
        this.qty = qty;
    }




}


