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
 * District generated by hbm2java
 */
@Entity
@Table(name="district"
    ,catalog="jms4"
)
public class District  implements java.io.Serializable {


     private Long idDistrict;
     private City city;
     private String district;
     private Set<Users> userses = new HashSet<Users>(0);
     private Set<Company> companies = new HashSet<Company>(0);

    public District() {
    }

	
    public District(Long idDistrict) {
        this.idDistrict = idDistrict;
    }
    public District(Long idDistrict, City city, String district, Set<Users> userses, Set<Company> companies) {
       this.idDistrict = idDistrict;
       this.city = city;
       this.district = district;
       this.userses = userses;
       this.companies = companies;
    }
   
     @Id 
    
    @Column(name="id_district", unique=true, nullable=false)
    public Long getIdDistrict() {
        return this.idDistrict;
    }
    
    public void setIdDistrict(Long idDistrict) {
        this.idDistrict = idDistrict;
    }
@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="id_city")
    public City getCity() {
        return this.city;
    }
    
    public void setCity(City city) {
        this.city = city;
    }
    
    @Column(name="district", length=32)
    public String getDistrict() {
        return this.district;
    }
    
    public void setDistrict(String district) {
        this.district = district;
    }
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="district")
    public Set<Users> getUserses() {
        return this.userses;
    }
    
    public void setUserses(Set<Users> userses) {
        this.userses = userses;
    }
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="district")
    public Set<Company> getCompanies() {
        return this.companies;
    }
    
    public void setCompanies(Set<Company> companies) {
        this.companies = companies;
    }




}


