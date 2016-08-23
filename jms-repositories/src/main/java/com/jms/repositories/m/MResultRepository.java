package com.jms.repositories.m;



import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.jms.domain.db.MMainCycle;
import com.jms.domain.db.MMainItem;
import com.jms.domain.db.MResult;




@Repository
public interface MResultRepository extends JpaRepository<MResult, Long>{


}
