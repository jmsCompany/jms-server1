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
 * PWoBom generated by hbm2java
 */
@Entity
@Table(name="p_wo_bom"
    ,catalog="jms4"
)
public class PWoBom  implements java.io.Serializable {


     private Integer idWoBom;
     private PBom PBom;
     private PWo PWo;
     private Set<SMtfMaterial> SMtfMaterials = new HashSet<SMtfMaterial>(0);

    public PWoBom() {
    }

	
    public PWoBom(Integer idWoBom) {
        this.idWoBom = idWoBom;
    }
    public PWoBom(Integer idWoBom, PBom PBom, PWo PWo, Set<SMtfMaterial> SMtfMaterials) {
       this.idWoBom = idWoBom;
       this.PBom = PBom;
       this.PWo = PWo;
       this.SMtfMaterials = SMtfMaterials;
    }
   
    @Id 
    
   @Column(name="id_wo_bom", unique=true, nullable=false)
    public Integer getIdWoBom() {
        return this.idWoBom;
    }
    
    public void setIdWoBom(Integer idWoBom) {
        this.idWoBom = idWoBom;
    }
@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="id_bom")
    public PBom getPBom() {
        return this.PBom;
    }
    
    public void setPBom(PBom PBom) {
        this.PBom = PBom;
    }
@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="id_wo")
    public PWo getPWo() {
        return this.PWo;
    }
    
    public void setPWo(PWo PWo) {
        this.PWo = PWo;
    }
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="PWoBom")
    public Set<SMtfMaterial> getSMtfMaterials() {
        return this.SMtfMaterials;
    }
    
    public void setSMtfMaterials(Set<SMtfMaterial> SMtfMaterials) {
        this.SMtfMaterials = SMtfMaterials;
    }




}


