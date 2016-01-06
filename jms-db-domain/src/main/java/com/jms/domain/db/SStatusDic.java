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
 * SStatusDic generated by hbm2java
 */
@Entity
@Table(name="s_status_dic"
    ,catalog="jms3"
)
public class SStatusDic  implements java.io.Serializable {


     private Long id;
     private String name;
     private String des;
     private String source;
     private Set<SMaterialCategory> SMaterialCategories = new HashSet<SMaterialCategory>(0);
     private Set<SCompanyCo> SCompanyCos = new HashSet<SCompanyCo>(0);
     private Set<SPo> SPos = new HashSet<SPo>(0);
     private Set<SRmaMaterial> SRmaMaterials = new HashSet<SRmaMaterial>(0);
     private Set<SSo> SSos = new HashSet<SSo>(0);
     private Set<SStk> SStks = new HashSet<SStk>(0);
     private Set<SMrb> SMrbs = new HashSet<SMrb>(0);
     private Set<SMrbMaterial> SMrbMaterials = new HashSet<SMrbMaterial>(0);
     private Set<SSoMaterial> SSoMaterials = new HashSet<SSoMaterial>(0);
     private Set<SLinkman> SLinkmans = new HashSet<SLinkman>(0);
     private Set<SDoMaterial> SDoMaterials = new HashSet<SDoMaterial>(0);
     private Set<SMaterial> SMaterials = new HashSet<SMaterial>(0);
     private Set<SIncomeMaterial> SIncomeMaterials = new HashSet<SIncomeMaterial>(0);
     private Set<SBin> SBins = new HashSet<SBin>(0);
     private Set<SPoMaterial> SPoMaterials = new HashSet<SPoMaterial>(0);
     private Set<SRma> SRmas = new HashSet<SRma>(0);

    public SStatusDic() {
    }

    public SStatusDic(String name, String des, String source, Set<SMaterialCategory> SMaterialCategories, Set<SCompanyCo> SCompanyCos, Set<SPo> SPos, Set<SRmaMaterial> SRmaMaterials, Set<SSo> SSos, Set<SStk> SStks, Set<SMrb> SMrbs, Set<SMrbMaterial> SMrbMaterials, Set<SSoMaterial> SSoMaterials, Set<SLinkman> SLinkmans, Set<SDoMaterial> SDoMaterials, Set<SMaterial> SMaterials, Set<SIncomeMaterial> SIncomeMaterials, Set<SBin> SBins, Set<SPoMaterial> SPoMaterials, Set<SRma> SRmas) {
       this.name = name;
       this.des = des;
       this.source = source;
       this.SMaterialCategories = SMaterialCategories;
       this.SCompanyCos = SCompanyCos;
       this.SPos = SPos;
       this.SRmaMaterials = SRmaMaterials;
       this.SSos = SSos;
       this.SStks = SStks;
       this.SMrbs = SMrbs;
       this.SMrbMaterials = SMrbMaterials;
       this.SSoMaterials = SSoMaterials;
       this.SLinkmans = SLinkmans;
       this.SDoMaterials = SDoMaterials;
       this.SMaterials = SMaterials;
       this.SIncomeMaterials = SIncomeMaterials;
       this.SBins = SBins;
       this.SPoMaterials = SPoMaterials;
       this.SRmas = SRmas;
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
    
    @Column(name="des", length=1024)
    public String getDes() {
        return this.des;
    }
    
    public void setDes(String des) {
        this.des = des;
    }
    
    @Column(name="source", length=20)
    public String getSource() {
        return this.source;
    }
    
    public void setSource(String source) {
        this.source = source;
    }
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="SStatusDic")
    public Set<SMaterialCategory> getSMaterialCategories() {
        return this.SMaterialCategories;
    }
    
    public void setSMaterialCategories(Set<SMaterialCategory> SMaterialCategories) {
        this.SMaterialCategories = SMaterialCategories;
    }
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="SStatusDic")
    public Set<SCompanyCo> getSCompanyCos() {
        return this.SCompanyCos;
    }
    
    public void setSCompanyCos(Set<SCompanyCo> SCompanyCos) {
        this.SCompanyCos = SCompanyCos;
    }
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="SStatusDic")
    public Set<SPo> getSPos() {
        return this.SPos;
    }
    
    public void setSPos(Set<SPo> SPos) {
        this.SPos = SPos;
    }
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="SStatusDic")
    public Set<SRmaMaterial> getSRmaMaterials() {
        return this.SRmaMaterials;
    }
    
    public void setSRmaMaterials(Set<SRmaMaterial> SRmaMaterials) {
        this.SRmaMaterials = SRmaMaterials;
    }
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="SStatusDic")
    public Set<SSo> getSSos() {
        return this.SSos;
    }
    
    public void setSSos(Set<SSo> SSos) {
        this.SSos = SSos;
    }
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="SStatusDic")
    public Set<SStk> getSStks() {
        return this.SStks;
    }
    
    public void setSStks(Set<SStk> SStks) {
        this.SStks = SStks;
    }
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="SStatusDic")
    public Set<SMrb> getSMrbs() {
        return this.SMrbs;
    }
    
    public void setSMrbs(Set<SMrb> SMrbs) {
        this.SMrbs = SMrbs;
    }
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="SStatusDic")
    public Set<SMrbMaterial> getSMrbMaterials() {
        return this.SMrbMaterials;
    }
    
    public void setSMrbMaterials(Set<SMrbMaterial> SMrbMaterials) {
        this.SMrbMaterials = SMrbMaterials;
    }
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="SStatusDic")
    public Set<SSoMaterial> getSSoMaterials() {
        return this.SSoMaterials;
    }
    
    public void setSSoMaterials(Set<SSoMaterial> SSoMaterials) {
        this.SSoMaterials = SSoMaterials;
    }
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="SStatusDic")
    public Set<SLinkman> getSLinkmans() {
        return this.SLinkmans;
    }
    
    public void setSLinkmans(Set<SLinkman> SLinkmans) {
        this.SLinkmans = SLinkmans;
    }
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="SStatusDic")
    public Set<SDoMaterial> getSDoMaterials() {
        return this.SDoMaterials;
    }
    
    public void setSDoMaterials(Set<SDoMaterial> SDoMaterials) {
        this.SDoMaterials = SDoMaterials;
    }
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="SStatusDic")
    public Set<SMaterial> getSMaterials() {
        return this.SMaterials;
    }
    
    public void setSMaterials(Set<SMaterial> SMaterials) {
        this.SMaterials = SMaterials;
    }
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="SStatusDic")
    public Set<SIncomeMaterial> getSIncomeMaterials() {
        return this.SIncomeMaterials;
    }
    
    public void setSIncomeMaterials(Set<SIncomeMaterial> SIncomeMaterials) {
        this.SIncomeMaterials = SIncomeMaterials;
    }
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="SStatusDic")
    public Set<SBin> getSBins() {
        return this.SBins;
    }
    
    public void setSBins(Set<SBin> SBins) {
        this.SBins = SBins;
    }
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="SStatusDic")
    public Set<SPoMaterial> getSPoMaterials() {
        return this.SPoMaterials;
    }
    
    public void setSPoMaterials(Set<SPoMaterial> SPoMaterials) {
        this.SPoMaterials = SPoMaterials;
    }
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="SStatusDic")
    public Set<SRma> getSRmas() {
        return this.SRmas;
    }
    
    public void setSRmas(Set<SRma> SRmas) {
        this.SRmas = SRmas;
    }




}


