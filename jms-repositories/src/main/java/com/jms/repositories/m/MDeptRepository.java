package com.jms.repositories.m;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.jms.domain.db.MDept;




@Repository
public interface MDeptRepository extends JpaRepository<MDept, Long>{

}
