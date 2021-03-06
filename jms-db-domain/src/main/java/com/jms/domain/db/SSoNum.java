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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * SBin generated by hbm2java
 */
@Entity
@Table(name="s_so_num"
)
public class SSoNum  implements java.io.Serializable {


     private Long id;
     
     private String nam;
 


    public SSoNum() {
    }
    public SSoNum(String nam) {
     this.nam = nam;
    }
   
     @Id @GeneratedValue(strategy=IDENTITY)
    
    @Column(name="id", unique=true, nullable=false)
    public Long getId() {
        return this.id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }

    
    @Column(name="nam", length=45)
    public String getNam() {
        return this.nam;
    }
    
    public void setNam(String nam) {
        this.nam = nam;
    }



}


