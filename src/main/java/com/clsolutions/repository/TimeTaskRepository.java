package com.clsolutions.repository;

import com.clsolutions.domain.TimeTask;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data MongoDB repository for the TimeTask entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TimeTaskRepository extends MongoRepository<TimeTask, String> {

}
