package com.jms.domain.db;
// Generated 2016-1-6 12:39:14 by Hibernate Tools 3.2.2.GA


import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * SMrbMaterial generated by hbm2java
 */
@Entity
@Table(name="s_mrb_material"
    ,catalog="jms3"
)
public class SMrbMaterial  implements java.io.Serializable {


     private Long id;
     private SMrb SMrb;
     private SStatusDic SStatusDic;
     private SMaterial SMaterial;
     private Long line;
     private Long qty;
     private Long UPrice;
     private Long totalPrice;
     private String remark;
     private Date deliveryDate;
     private Long qtyReceived;
     private Long qtyRejected;

    public SMrbMaterial() {
    }

    public SMrbMaterial(SMrb SMrb, SStatusDic SStatusDic, SMaterial SMaterial, Long line, Long qty, Long UPrice, Long totalPrice, String remark, Date deliveryDate, Long qtyReceived, Long qtyRejected) {
       this.SMrb = SMrb;
       this.SStatusDic = SStatusDic;
       this.SMaterial = SMaterial;
       this.line = line;
       this.qty = qty;
       this.UPrice = UPrice;
       this.totalPrice = totalPrice;
       this.remark = remark;
       this.deliveryDate = deliveryDate;
       this.qtyReceived = qtyReceived;
       this.qtyRejected = qtyRejected;
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
    @JoinColumn(name="id_mrb")
    public SMrb getSMrb() {
        return this.SMrb;
    }
    
    public void setSMrb(SMrb SMrb) {
        this.SMrb = SMrb;
    }
@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="status")
    public SStatusDic getSStatusDic() {
        return this.SStatusDic;
    }
    
    public void setSStatusDic(SStatusDic SStatusDic) {
        this.SStatusDic = SStatusDic;
    }
@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="pno")
    public SMaterial getSMaterial() {
        return this.SMaterial;
    }
    
    public void setSMaterial(SMaterial SMaterial) {
        this.SMaterial = SMaterial;
    }
    
    @Column(name="line")
    public Long getLine() {
        return this.line;
    }
    
    public void setLine(Long line) {
        this.line = line;
    }
    
    @Column(name="qty", precision=10, scale=0)
    public Long getQty() {
        return this.qty;
    }
    
    public void setQty(Long qty) {
        this.qty = qty;
    }
    
    @Column(name="u_price", precision=10, scale=0)
    public Long getUPrice() {
        return this.UPrice;
    }
    
    public void setUPrice(Long UPrice) {
        this.UPrice = UPrice;
    }
    
    @Column(name="total_price", precision=10, scale=0)
    public Long getTotalPrice() {
        return this.totalPrice;
    }
    
    public void setTotalPrice(Long totalPrice) {
        this.totalPrice = totalPrice;
    }
    
    @Column(name="remark", length=1024)
    public String getRemark() {
        return this.remark;
    }
    
    public void setRemark(String remark) {
        this.remark = remark;
    }
    @Temporal(TemporalType.DATE)
    @Column(name="delivery_date", length=10)
    public Date getDeliveryDate() {
        return this.deliveryDate;
    }
    
    public void setDeliveryDate(Date deliveryDate) {
        this.deliveryDate = deliveryDate;
    }
    
    @Column(name="qty_received", precision=10, scale=0)
    public Long getQtyReceived() {
        return this.qtyReceived;
    }
    
    public void setQtyReceived(Long qtyReceived) {
        this.qtyReceived = qtyReceived;
    }
    
    @Column(name="qty_rejected", precision=10, scale=0)
    public Long getQtyRejected() {
        return this.qtyRejected;
    }
    
    public void setQtyRejected(Long qtyRejected) {
        this.qtyRejected = qtyRejected;
    }




}

