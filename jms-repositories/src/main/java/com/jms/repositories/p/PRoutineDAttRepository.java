package com.jms.repositories.p;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.jms.domain.db.PRoutineDAtt;



@Repository
public interface PRoutineDAttRepository extends JpaRepository<PRoutineDAtt, Long>{

	@Query("select p from PRoutineDAtt p where p.PRoutineD.idRoutineD=?1")
	public List<PRoutineDAtt> getByRoutineDId(Long routineDId);
}
