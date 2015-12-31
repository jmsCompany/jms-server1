package com.jms.domain.db;
// Generated 2015-12-31 19:48:00 by Hibernate Tools 3.2.2.GA


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
 * SMaterialCategoryPic generated by hbm2java
 */
@Entity
@Table(name="s_material_category_pic"
    ,catalog="jms3"
)
public class SMaterialCategoryPic  implements java.io.Serializable {


     private Long id;
     private SMaterialCategory SMaterialCategory;
     private SPic SPic;
     private Long orderBy;

    public SMaterialCategoryPic() {
    }

	
    public SMaterialCategoryPic(SMaterialCategory SMaterialCategory, SPic SPic) {
        this.SMaterialCategory = SMaterialCategory;
        this.SPic = SPic;
    }
    public SMaterialCategoryPic(SMaterialCategory SMaterialCategory, SPic SPic, Long orderBy) {
       this.SMaterialCategory = SMaterialCategory;
       this.SPic = SPic;
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
    @JoinColumn(name="id_material_category", nullable=false)
    public SMaterialCategory getSMaterialCategory() {
        return this.SMaterialCategory;
    }
    
    public void setSMaterialCategory(SMaterialCategory SMaterialCategory) {
        this.SMaterialCategory = SMaterialCategory;
    }
@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="id_pic", nullable=false)
    public SPic getSPic() {
        return this.SPic;
    }
    
    public void setSPic(SPic SPic) {
        this.SPic = SPic;
    }
    
    @Column(name="order_by")
    public Long getOrderBy() {
        return this.orderBy;
    }
    
    public void setOrderBy(Long orderBy) {
        this.orderBy = orderBy;
    }




}


