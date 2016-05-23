package com.jms.repositories.q;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.jms.domain.db.QCheckList;


@Repository
public interface QCheckListRepository extends JpaRepository<QCheckList, Long>{
   @Query("select q from QCheckList q where q.PRoutineD.idRoutineD=?1")
   public List<QCheckList> getByRoutineDId(Long routineDId);
}
