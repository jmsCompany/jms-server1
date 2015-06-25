package com.jms.repositories.system;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.jms.domain.db.City;
import com.jms.domain.db.District;
import com.jms.domain.db.Province;
import com.jms.domain.db.Sector;
import com.jms.domain.db.SysDic;
import com.jms.domain.db.SysDicD;

@Repository
public interface SysDicDRepository extends CrudRepository<SysDicD, Long>{

	@Query("select s from SysDicD s where s.sysDic.type=?1 ")	
	public List<SysDicD> findDicsByType(String type);
}
