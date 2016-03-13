package com.jms.repositories.s;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.jms.domain.db.SMaterialCategoryPic;


@Repository
public interface SMaterialCategoryPicRepository  extends JpaRepository<SMaterialCategoryPic, Long>{
		
}