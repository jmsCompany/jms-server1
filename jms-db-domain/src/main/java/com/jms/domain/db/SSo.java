package com.jms.domain.db;
// Generated 2016-3-31 16:15:17 by Hibernate Tools 3.2.2.GA


import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * SSo generated by hbm2java
 */
@Entity
@Table(name="s_so"
    ,catalog="jms5"
)
public class SSo  implements java.io.Serializable {


     private Long idSo;
     private Company company;
     private SCompanyCo SCompanyCo;
     private SStatusDic SStatusDic;
     private Users users;
     private SMaterial SMaterial;
     private SAttachment SAttachment;
     private STermDic STermDicByPaymentTerm;
     private STermDic STermDicByFreightTerm;
     private String codeSo;
     private String coOrderNo;
     private Date dateOrder;
     private Long taxRate;
     private Long exchange;
     private Long qtySo;
     private Long UPrice;
     private Long totalAmount;
     private Date deliveryDate;
     private Long qtyDelivered;
     private String remark;
     private String autoRemark;
     private Set<PWo> PWos = new HashSet<PWo>(0);
     private Set<SMtfMaterial> SMtfMaterials = new HashSet<SMtfMaterial>(0);

    public SSo() {
    }

    public SSo(Company company, SCompanyCo SCompanyCo, SStatusDic SStatusDic, Users users, SMaterial SMaterial, SAttachment SAttachment, STermDic STermDicByPaymentTerm, STermDic STermDicByFreightTerm, String codeSo, String coOrderNo, Date dateOrder, Long taxRate, Long exchange, Long qtySo, Long UPrice, Long totalAmount, Date deliveryDate, Long qtyDelivered, String remark, String autoRemark, Set<PWo> PWos, Set<SMtfMaterial> SMtfMaterials) {
       this.company = company;
       this.SCompanyCo = SCompanyCo;
       this.SStatusDic = SStatusDic;
       this.users = users;
       this.SMaterial = SMaterial;
       this.SAttachment = SAttachment;
       this.STermDicByPaymentTerm = STermDicByPaymentTerm;
       this.STermDicByFreightTerm = STermDicByFreightTerm;
       this.codeSo = codeSo;
       this.coOrderNo = coOrderNo;
       this.dateOrder = dateOrder;
       this.taxRate = taxRate;
       this.exchange = exchange;
       this.qtySo = qtySo;
       this.UPrice = UPrice;
       this.totalAmount = totalAmount;
       this.deliveryDate = deliveryDate;
       this.qtyDelivered = qtyDelivered;
       this.remark = remark;
       this.autoRemark = autoRemark;
       this.PWos = PWos;
       this.SMtfMaterials = SMtfMaterials;
    }
   
     @Id @GeneratedValue(strategy=IDENTITY)
    
    @Column(name="id_so", unique=true, nullable=false)
    public Long getIdSo() {
        return this.idSo;
    }
    
    public void setIdSo(Long idSo) {
        this.idSo = idSo;
    }
@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="id_company")
    public Company getCompany() {
        return this.company;
    }
    
    public void setCompany(Company company) {
        this.company = company;
    }
@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="code_co")
    public SCompanyCo getSCompanyCo() {
        return this.SCompanyCo;
    }
    
    public void setSCompanyCo(SCompanyCo SCompanyCo) {
        this.SCompanyCo = SCompanyCo;
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
    @JoinColumn(name="emp_order")
    public Users getUsers() {
        return this.users;
    }
    
    public void setUsers(Users users) {
        this.users = users;
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
    @JoinColumn(name="attachment")
    public SAttachment getSAttachment() {
        return this.SAttachment;
    }
    
    public void setSAttachment(SAttachment SAttachment) {
        this.SAttachment = SAttachment;
    }
@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="payment_term")
    public STermDic getSTermDicByPaymentTerm() {
        return this.STermDicByPaymentTerm;
    }
    
    public void setSTermDicByPaymentTerm(STermDic STermDicByPaymentTerm) {
        this.STermDicByPaymentTerm = STermDicByPaymentTerm;
    }
@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="freight_term")
    public STermDic getSTermDicByFreightTerm() {
        return this.STermDicByFreightTerm;
    }
    
    public void setSTermDicByFreightTerm(STermDic STermDicByFreightTerm) {
        this.STermDicByFreightTerm = STermDicByFreightTerm;
    }
    
    @Column(name="code_so", length=64)
    public String getCodeSo() {
        return this.codeSo;
    }
    
    public void setCodeSo(String codeSo) {
        this.codeSo = codeSo;
    }
    
    @Column(name="co_order_no", length=32)
    public String getCoOrderNo() {
        return this.coOrderNo;
    }
    
    public void setCoOrderNo(String coOrderNo) {
        this.coOrderNo = coOrderNo;
    }
    @Temporal(TemporalType.DATE)
    @Column(name="date_order", length=10)
    public Date getDateOrder() {
        return this.dateOrder;
    }
    
    public void setDateOrder(Date dateOrder) {
        this.dateOrder = dateOrder;
    }
    
    @Column(name="tax_rate", precision=10, scale=0)
    public Long getTaxRate() {
        return this.taxRate;
    }
    
    public void setTaxRate(Long taxRate) {
        this.taxRate = taxRate;
    }
    
    @Column(name="exchange", precision=10, scale=0)
    public Long getExchange() {
        return this.exchange;
    }
    
    public void setExchange(Long exchange) {
        this.exchange = exchange;
    }
    
    @Column(name="qty_so")
    public Long getQtySo() {
        return this.qtySo;
    }
    
    public void setQtySo(Long qtySo) {
        this.qtySo = qtySo;
    }
    
    @Column(name="u_price", precision=10, scale=0)
    public Long getUPrice() {
        return this.UPrice;
    }
    
    public void setUPrice(Long UPrice) {
        this.UPrice = UPrice;
    }
    
    @Column(name="total_amount", precision=10, scale=0)
    public Long getTotalAmount() {
        return this.totalAmount;
    }
    
    public void setTotalAmount(Long totalAmount) {
        this.totalAmount = totalAmount;
    }
    @Temporal(TemporalType.DATE)
    @Column(name="delivery_date", length=10)
    public Date getDeliveryDate() {
        return this.deliveryDate;
    }
    
    public void setDeliveryDate(Date deliveryDate) {
        this.deliveryDate = deliveryDate;
    }
    
    @Column(name="qty_delivered")
    public Long getQtyDelivered() {
        return this.qtyDelivered;
    }
    
    public void setQtyDelivered(Long qtyDelivered) {
        this.qtyDelivered = qtyDelivered;
    }
    
    @Column(name="remark", length=1024)
    public String getRemark() {
        return this.remark;
    }
    
    public void setRemark(String remark) {
        this.remark = remark;
    }
    
    @Column(name="auto_remark", length=1024)
    public String getAutoRemark() {
        return this.autoRemark;
    }
    
    public void setAutoRemark(String autoRemark) {
        this.autoRemark = autoRemark;
    }
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="SSo")
    public Set<PWo> getPWos() {
        return this.PWos;
    }
    
    public void setPWos(Set<PWo> PWos) {
        this.PWos = PWos;
    }
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="SSo")
    public Set<SMtfMaterial> getSMtfMaterials() {
        return this.SMtfMaterials;
    }
    
    public void setSMtfMaterials(Set<SMtfMaterial> SMtfMaterials) {
        this.SMtfMaterials = SMtfMaterials;
    }




}


