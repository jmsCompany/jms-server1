package com.jms.repositories.q;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jms.domain.db.QCar;
import com.jms.domain.db.QFileManagent;
import com.jms.domain.db.QFileType;





@Repository
public interface QFileTypeRepository extends JpaRepository<QFileType, Long>{

     public List<QFileType> getByIdCompany(Long idCompany);
     
}
