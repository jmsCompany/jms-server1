package com.jms.repositories.system;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.jms.domain.db.Documents;


@Repository
public interface DocumentRepository extends CrudRepository<Documents, Integer>{

}
