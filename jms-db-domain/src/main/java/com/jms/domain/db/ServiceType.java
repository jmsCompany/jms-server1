package com.jms.domain.db;
// Generated 2015-8-15 16:28:29 by Hibernate Tools 3.2.2.GA


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
 * ServiceType generated by hbm2java
 */
@Entity
@Table(name="service_type"
    ,catalog="jms"
)
public class ServiceType  implements java.io.Serializable {


     private Long idServiceType;
     private String type;
     private String description;
     private Set<Service> services = new HashSet<Service>(0);

    public ServiceType() {
    }

    public ServiceType(String type, String description, Set<Service> services) {
       this.type = type;
       this.description = description;
       this.services = services;
    }
   
     @Id @GeneratedValue(strategy=IDENTITY)
    
    @Column(name="ID_SERVICE_TYPE", unique=true, nullable=false)
    public Long getIdServiceType() {
        return this.idServiceType;
    }
    
    public void setIdServiceType(Long idServiceType) {
        this.idServiceType = idServiceType;
    }
    
    @Column(name="TYPE", length=64)
    public String getType() {
        return this.type;
    }
    
    public void setType(String type) {
        this.type = type;
    }
    
    @Column(name="Description", length=20)
    public String getDescription() {
        return this.description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="serviceType")
    public Set<Service> getServices() {
        return this.services;
    }
    
    public void setServices(Set<Service> services) {
        this.services = services;
    }




}


