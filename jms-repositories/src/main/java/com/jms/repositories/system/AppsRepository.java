package com.jms.repositories.system;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.jms.domain.db.Apps;


@Repository
public interface AppsRepository extends JpaRepository<Apps, Long>{
	@Query("select a from Apps a where a.groupsEn='SCM' or a.groupsEn='PD' order by a.seq")
	public List<Apps> findInvs();
	
	@Query("select a from Apps a order by a.seq")
	public List<Apps> findAllBySeq();

}
