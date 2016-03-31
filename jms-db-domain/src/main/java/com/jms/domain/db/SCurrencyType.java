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
 * SCurrencyType generated by hbm2java
 */
@Entity
@Table(name="s_currency_type"
    ,catalog="jms5"
)
public class SCurrencyType  implements java.io.Serializable {


     private Long idCurrencyType;
     private String currency;
     private Set<SPo> SPos = new HashSet<SPo>(0);

    public SCurrencyType() {
    }

    public SCurrencyType(String currency, Set<SPo> SPos) {
       this.currency = currency;
       this.SPos = SPos;
    }
   
     @Id @GeneratedValue(strategy=IDENTITY)
    
    @Column(name="id_currency_type", unique=true, nullable=false)
    public Long getIdCurrencyType() {
        return this.idCurrencyType;
    }
    
    public void setIdCurrencyType(Long idCurrencyType) {
        this.idCurrencyType = idCurrencyType;
    }
    
    @Column(name="currency", length=20)
    public String getCurrency() {
        return this.currency;
    }
    
    public void setCurrency(String currency) {
        this.currency = currency;
    }
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="SCurrencyType")
    public Set<SPo> getSPos() {
        return this.SPos;
    }
    
    public void setSPos(Set<SPo> SPos) {
        this.SPos = SPos;
    }




}


