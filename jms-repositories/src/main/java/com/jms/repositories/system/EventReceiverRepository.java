package com.jms.repositories.system;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.jms.domain.db.EventReceiver;


@Repository
public interface EventReceiverRepository extends JpaRepository<EventReceiver, Long>{
	
  public List<EventReceiver> findByIdEventAndIdCompany(Long idEvent,Long idCompany);

}
