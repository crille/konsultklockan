package com.clsolutions.service.impl;

import com.clsolutions.service.TimeTaskService;
import com.clsolutions.domain.TimeTask;
import com.clsolutions.repository.TimeTaskRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Service Implementation for managing TimeTask.
 */
@Service
public class TimeTaskServiceImpl implements TimeTaskService {

    private final Logger log = LoggerFactory.getLogger(TimeTaskServiceImpl.class);

    private TimeTaskRepository timeTaskRepository;

    public TimeTaskServiceImpl(TimeTaskRepository timeTaskRepository) {
        this.timeTaskRepository = timeTaskRepository;
    }

    /**
     * Save a timeTask.
     *
     * @param timeTask the entity to save
     * @return the persisted entity
     */
    @Override
    public TimeTask save(TimeTask timeTask) {
        log.debug("Request to save TimeTask : {}", timeTask);
        return timeTaskRepository.save(timeTask);
    }

    /**
     * Get all the timeTasks.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    public Page<TimeTask> findAll(Pageable pageable) {
        log.debug("Request to get all TimeTasks");
        return timeTaskRepository.findAll(pageable);
    }


    /**
     * Get one timeTask by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    public Optional<TimeTask> findOne(String id) {
        log.debug("Request to get TimeTask : {}", id);
        return timeTaskRepository.findById(id);
    }

    /**
     * Delete the timeTask by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(String id) {
        log.debug("Request to delete TimeTask : {}", id);
        timeTaskRepository.deleteById(id);
    }
}
