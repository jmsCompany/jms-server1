package com.jms.domain.db;
// Generated 2015-12-31 19:48:00 by Hibernate Tools 3.2.2.GA


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
 * GroupType generated by hbm2java
 */
@Entity
@Table(name="group_type"
    ,catalog="jms3"
)
public class GroupType  implements java.io.Serializable {


     private Long idGroupType;
     private String groupType;
     private Set<Groups> groupses = new HashSet<Groups>(0);

    public GroupType() {
    }

    public GroupType(String groupType, Set<Groups> groupses) {
       this.groupType = groupType;
       this.groupses = groupses;
    }
   
     @Id @GeneratedValue(strategy=IDENTITY)
    
    @Column(name="id_group_type", unique=true, nullable=false)
    public Long getIdGroupType() {
        return this.idGroupType;
    }
    
    public void setIdGroupType(Long idGroupType) {
        this.idGroupType = idGroupType;
    }
    
    @Column(name="group_type", length=20)
    public String getGroupType() {
        return this.groupType;
    }
    
    public void setGroupType(String groupType) {
        this.groupType = groupType;
    }
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="groupType")
    public Set<Groups> getGroupses() {
        return this.groupses;
    }
    
    public void setGroupses(Set<Groups> groupses) {
        this.groupses = groupses;
    }




}


