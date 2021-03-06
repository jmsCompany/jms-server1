package com.jms.domain.db;
// Generated 2016-3-31 16:15:17 by Hibernate Tools 3.2.2.GA


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
 * QItemType generated by hbm2java
 */
@Entity
@Table(name="q_item_type"
)
public class QItemType  implements java.io.Serializable {


     private Long idItemType;
     private String des;
     private Set<QCheckList> QCheckLists = new HashSet<QCheckList>(0);

    public QItemType() {
    }

    public QItemType(String des, Set<QCheckList> QCheckLists) {
       this.des = des;
       this.QCheckLists = QCheckLists;
    }
   
     @Id @GeneratedValue(strategy=IDENTITY)
    
    @Column(name="id_item_type", unique=true, nullable=false)
    public Long getIdItemType() {
        return this.idItemType;
    }
    
    public void setIdItemType(Long idItemType) {
        this.idItemType = idItemType;
    }
    
    @Column(name="des", length=64)
    public String getDes() {
        return this.des;
    }
    
    public void setDes(String des) {
        this.des = des;
    }
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="QItemType")
    public Set<QCheckList> getQCheckLists() {
        return this.QCheckLists;
    }
    
    public void setQCheckLists(Set<QCheckList> QCheckLists) {
        this.QCheckLists = QCheckLists;
    }




}


