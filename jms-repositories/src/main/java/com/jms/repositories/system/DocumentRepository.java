package com.jms.repositories.system;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.jms.domain.db.Document;


@Repository
public interface DocumentRepository extends CrudRepository<Document, Integer>{

}
