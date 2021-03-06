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
 * SStkTypeDic generated by hbm2java
 */
@Entity
@Table(name="s_stk_type_dic")

public class SStkTypeDic  implements java.io.Serializable {


     private Long idStkType;
     private String name;
     private String code;
     private Set<SStk> SStks = new HashSet<SStk>(0);
     private String nameEn;
    public SStkTypeDic() {
    }

    public SStkTypeDic(String name, Set<SStk> SStks) {
       this.name = name;
       this.SStks = SStks;
    }
   
    @Id @GeneratedValue(strategy=IDENTITY)
    @Column(name="id_stk_type", unique=true, nullable=false)
    public Long getIdStkType() {
        return this.idStkType;
    }
    
    public void setIdStkType(Long idStkType) {
        this.idStkType = idStkType;
    }
    
    @Column(name="name", length=20)
    public String getName() {
        return this.name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    @Column(name="code", length=20)
    public String getCode() {
        return this.code;
    }
    
    public void setCode(String code) {
        this.name = code;
    }
    
    @OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="SStkTypeDic")
    public Set<SStk> getSStks() {
        return this.SStks;
    }
    
    public void setSStks(Set<SStk> SStks) {
        this.SStks = SStks;
    }
    @Column(name="name_en", length=20)
	public String getNameEn() {
		return nameEn;
	}

	public void setNameEn(String nameEn) {
		this.nameEn = nameEn;
	}

}


