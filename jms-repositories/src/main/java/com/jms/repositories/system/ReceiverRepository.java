package com.jms.repositories.system;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.jms.domain.db.Receiver;

@Repository
public interface ReceiverRepository extends JpaRepository<Receiver, Long>{

	@Query("select r from Receiver r where r.groups.idGroup in(?1) order by r.notification.creationTime desc")
	public Page<Receiver> findReceivers(List<Long> idGroups,Pageable pageable);
}
