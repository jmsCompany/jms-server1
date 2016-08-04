package com.jms.repositories.m;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.jms.domain.db.MMainCycle;
import com.jms.domain.db.MMainItem;




@Repository
public interface MMainItemRepository extends JpaRepository<MMainItem, Long>{

}
