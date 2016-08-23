package com.jms.repositories.m;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.jms.domain.db.MRemark;




@Repository
public interface MRemarkRepository extends JpaRepository<MRemark, Long>{
	


}
