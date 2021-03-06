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
 * SMaterialAttachment generated by hbm2java
 */
@Entity
@Table(name="s_material_attachment"
)
public class SMaterialAttachment  implements java.io.Serializable {


     private Long id;
     private SAttachment SAttachment;
     private SMaterial SMaterial;
     private Long orderBy;

    public SMaterialAttachment() {
    }

    public SMaterialAttachment(SAttachment SAttachment, SMaterial SMaterial, Long orderBy) {
       this.SAttachment = SAttachment;
       this.SMaterial = SMaterial;
       this.orderBy = orderBy;
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
    @JoinColumn(name="id_attachment")
    public SAttachment getSAttachment() {
        return this.SAttachment;
    }
    
    public void setSAttachment(SAttachment SAttachment) {
        this.SAttachment = SAttachment;
    }
@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="id_material")
    public SMaterial getSMaterial() {
        return this.SMaterial;
    }
    
    public void setSMaterial(SMaterial SMaterial) {
        this.SMaterial = SMaterial;
    }
    
    @Column(name="order_by")
    public Long getOrderBy() {
        return this.orderBy;
    }
    
    public void setOrderBy(Long orderBy) {
        this.orderBy = orderBy;
    }




}


