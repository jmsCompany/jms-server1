package com.jms.domain.db;
// Generated 2016-3-9 10:11:30 by Hibernate Tools 3.2.2.GA


import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * SMaterialTypeDic generated by hbm2java
 */
@Entity
@Table(name="s_material_type_dic"
    ,catalog="jms4"
)
public class SMaterialTypeDic  implements java.io.Serializable {


     private Long id;
     private String name;
     private String des;
     private Set<SMaterial> SMaterials = new HashSet<SMaterial>(0);

    public SMaterialTypeDic() {
    }

    public SMaterialTypeDic(String name, String des, Set<SMaterial> SMaterials) {
       this.name = name;
       this.des = des;
       this.SMaterials = SMaterials;
    }
   
     @Id @GeneratedValue(strategy=IDENTITY)
    
    @Column(name="id", unique=true, nullable=false)
    public Long getId() {
        return this.id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    @Column(name="name", length=20)
    public String getName() {
        return this.name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    @Column(name="des", length=128)
    public String getDes() {
        return this.des;
    }
    
    public void setDes(String des) {
        this.des = des;
    }
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="SMaterialTypeDic")
    public Set<SMaterial> getSMaterials() {
        return this.SMaterials;
    }
    
    public void setSMaterials(Set<SMaterial> SMaterials) {
        this.SMaterials = SMaterials;
    }




}


