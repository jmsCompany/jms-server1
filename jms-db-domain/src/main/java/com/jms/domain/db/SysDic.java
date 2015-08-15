package com.jms.domain.db;
// Generated 2015-8-15 16:28:29 by Hibernate Tools 3.2.2.GA


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
 * SysDic generated by hbm2java
 */
@Entity
@Table(name="sys_dic"
    ,catalog="jms"
)
public class SysDic  implements java.io.Serializable {


     private String type;
     private String description;
     private Set<SysDicD> sysDicDs = new HashSet<SysDicD>(0);

    public SysDic() {
    }

	
    public SysDic(String type, String description) {
        this.type = type;
        this.description = description;
    }
    public SysDic(String type, String description, Set<SysDicD> sysDicDs) {
       this.type = type;
       this.description = description;
       this.sysDicDs = sysDicDs;
    }
   
     @Id 
    
    @Column(name="TYPE", unique=true, nullable=false, length=64)
    public String getType() {
        return this.type;
    }
    
    public void setType(String type) {
        this.type = type;
    }
    
    @Column(name="DESCRIPTION", nullable=false, length=128)
    public String getDescription() {
        return this.description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="sysDic")
    public Set<SysDicD> getSysDicDs() {
        return this.sysDicDs;
    }
    
    public void setSysDicDs(Set<SysDicD> sysDicDs) {
        this.sysDicDs = sysDicDs;
    }




}


