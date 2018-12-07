package com.clsolutions.service;

import com.clsolutions.domain.TimeTask;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing TimeTask.
 */
public interface TimeTaskService {

    /**
     * Save a timeTask.
     *
     * @param timeTask the entity to save
     * @return the persisted entity
     */
    TimeTask save(TimeTask timeTask);

    /**
     * Get all the timeTasks.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<TimeTask> findAll(Pageable pageable);


    /**
     * Get the "id" timeTask.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<TimeTask> findOne(String id);

    /**
     * Delete the "id" timeTask.
     *
     * @param id the id of the entity
     */
    void delete(String id);
}
