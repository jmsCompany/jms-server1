package com.jms.domain.db;
// Generated 2016-1-6 12:39:14 by Hibernate Tools 3.2.2.GA


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
 * SCountryDic generated by hbm2java
 */
@Entity
@Table(name="s_country_dic"
    ,catalog="jms3"
)
public class SCountryDic  implements java.io.Serializable {


     private Long id;
     private String name;
     private Set<SCompanyCo> SCompanyCos = new HashSet<SCompanyCo>(0);

    public SCountryDic() {
    }

    public SCountryDic(String name, Set<SCompanyCo> SCompanyCos) {
       this.name = name;
       this.SCompanyCos = SCompanyCos;
    }
   
     @Id @GeneratedValue(strategy=IDENTITY)
    
    @Column(name="id", unique=true, nullable=false)
    public Long getId() {
        return this.id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    @Column(name="name", length=64)
    public String getName() {
        return this.name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="SCountryDic")
    public Set<SCompanyCo> getSCompanyCos() {
        return this.SCompanyCos;
    }
    
    public void setSCompanyCos(Set<SCompanyCo> SCompanyCos) {
        this.SCompanyCos = SCompanyCos;
    }




}


