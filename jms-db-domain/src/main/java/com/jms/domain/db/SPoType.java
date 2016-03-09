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
 * SPoType generated by hbm2java
 */
@Entity
@Table(name="s_po_type"
    ,catalog="jms4"
)
public class SPoType  implements java.io.Serializable {


     private Long idPoTypeDic;
     private String des;
     private Set<SPo> SPos = new HashSet<SPo>(0);

    public SPoType() {
    }

	
    public SPoType(Long idPoTypeDic) {
        this.idPoTypeDic = idPoTypeDic;
    }
    public SPoType(Long idPoTypeDic, String des, Set<SPo> SPos) {
       this.idPoTypeDic = idPoTypeDic;
       this.des = des;
       this.SPos = SPos;
    }
   
     @Id 
    
    @Column(name="id_po_type_dic", unique=true, nullable=false)
    public Long getIdPoTypeDic() {
        return this.idPoTypeDic;
    }
    
    public void setIdPoTypeDic(Long idPoTypeDic) {
        this.idPoTypeDic = idPoTypeDic;
    }
    
    @Column(name="des", length=20)
    public String getDes() {
        return this.des;
    }
    
    public void setDes(String des) {
        this.des = des;
    }
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="SPoType")
    public Set<SPo> getSPos() {
        return this.SPos;
    }
    
    public void setSPos(Set<SPo> SPos) {
        this.SPos = SPos;
    }




}


