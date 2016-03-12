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
 * ECheckItemDic generated by hbm2java
 */
@Entity
@Table(name="e_check_item_dic"
    ,catalog="jms4"
)
public class ECheckItemDic  implements java.io.Serializable {


     private Long idCheckItem;
     private Company company;
     private String checkItem;
     private Set<ECategoryItem> ECategoryItems = new HashSet<ECategoryItem>(0);

    public ECheckItemDic() {
    }

	
    public ECheckItemDic(Long idCheckItem) {
        this.idCheckItem = idCheckItem;
    }
    public ECheckItemDic(Long idCheckItem, Company company, String checkItem, Set<ECategoryItem> ECategoryItems) {
       this.idCheckItem = idCheckItem;
       this.company = company;
       this.checkItem = checkItem;
       this.ECategoryItems = ECategoryItems;
    }
   
     @Id 
    
    @Column(name="id_check_item", unique=true, nullable=false)
    public Long getIdCheckItem() {
        return this.idCheckItem;
    }
    
    public void setIdCheckItem(Long idCheckItem) {
        this.idCheckItem = idCheckItem;
    }
@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="id_company")
    public Company getCompany() {
        return this.company;
    }
    
    public void setCompany(Company company) {
        this.company = company;
    }
    
    @Column(name="check_item", length=256)
    public String getCheckItem() {
        return this.checkItem;
    }
    
    public void setCheckItem(String checkItem) {
        this.checkItem = checkItem;
    }
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="ECheckItemDic")
    public Set<ECategoryItem> getECategoryItems() {
        return this.ECategoryItems;
    }
    
    public void setECategoryItems(Set<ECategoryItem> ECategoryItems) {
        this.ECategoryItems = ECategoryItems;
    }




}

