package com.jms.domain.db;
// Generated 2016-3-31 16:15:17 by Hibernate Tools 3.2.2.GA


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Feedback generated by hbm2java
 */
@Entity
@Table(name="feedback"
)
public class Feedback  implements java.io.Serializable {


     private Long id;
     private Users users;
     private Long feedbackMethod;
     private String tel;
     private String email;
     private String name;
     private String description;
     private Long status;

    public Feedback() {
    }

    public Feedback(Users users, Long feedbackMethod, String tel, String email, String name, String description, Long status) {
       this.users = users;
       this.feedbackMethod = feedbackMethod;
       this.tel = tel;
       this.email = email;
       this.name = name;
       this.description = description;
       this.status = status;
    }
   
     @Id @GeneratedValue(strategy=IDENTITY)
    
    @Column(name="id", unique=true, nullable=false)
    public Long getId() {
        return this.id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="id_user")
    public Users getUsers() {
        return this.users;
    }
    
    public void setUsers(Users users) {
        this.users = users;
    }
    
    @Column(name="feedback_method")
    public Long getFeedbackMethod() {
        return this.feedbackMethod;
    }
    
    public void setFeedbackMethod(Long feedbackMethod) {
        this.feedbackMethod = feedbackMethod;
    }
    
    @Column(name="tel", length=20)
    public String getTel() {
        return this.tel;
    }
    
    public void setTel(String tel) {
        this.tel = tel;
    }
    
    @Column(name="email", length=128)
    public String getEmail() {
        return this.email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    @Column(name="name", length=64)
    public String getName() {
        return this.name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    @Column(name="description", length=1024)
    public String getDescription() {
        return this.description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    @Column(name="status")
    public Long getStatus() {
        return this.status;
    }
    
    public void setStatus(Long status) {
        this.status = status;
    }




}


