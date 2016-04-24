package com.jms.repositories.s;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.jms.domain.db.SAttachment;

@Repository
public interface SAttachmentRepository  extends JpaRepository<SAttachment, Long>{
	
		
}