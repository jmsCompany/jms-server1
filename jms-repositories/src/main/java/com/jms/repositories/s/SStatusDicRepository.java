package com.jms.repositories.s;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.jms.domain.db.SMaterialCategoryPic;
import com.jms.domain.db.SStatusDic;


@Repository
public interface SStatusDicRepository  extends JpaRepository<SStatusDic, Long>{
		
}