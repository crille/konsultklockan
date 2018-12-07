package com.clsolutions.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.clsolutions.domain.TimeTask;
import com.clsolutions.service.TimeTaskService;
import com.clsolutions.web.rest.errors.BadRequestAlertException;
import com.clsolutions.web.rest.util.HeaderUtil;
import com.clsolutions.web.rest.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing TimeTask.
 */
@RestController
@RequestMapping("/api")
public class TimeTaskResource {

    private final Logger log = LoggerFactory.getLogger(TimeTaskResource.class);

    private static final String ENTITY_NAME = "timeTask";

    private TimeTaskService timeTaskService;

    public TimeTaskResource(TimeTaskService timeTaskService) {
        this.timeTaskService = timeTaskService;
    }

    /**
     * POST  /time-tasks : Create a new timeTask.
     *
     * @param timeTask the timeTask to create
     * @return the ResponseEntity with status 201 (Created) and with body the new timeTask, or with status 400 (Bad Request) if the timeTask has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/time-tasks")
    @Timed
    public ResponseEntity<TimeTask> createTimeTask(@Valid @RequestBody TimeTask timeTask) throws URISyntaxException {
        log.debug("REST request to save TimeTask : {}", timeTask);
        if (timeTask.getId() != null) {
            throw new BadRequestAlertException("A new timeTask cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TimeTask result = timeTaskService.save(timeTask);
        return ResponseEntity.created(new URI("/api/time-tasks/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /time-tasks : Updates an existing timeTask.
     *
     * @param timeTask the timeTask to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated timeTask,
     * or with status 400 (Bad Request) if the timeTask is not valid,
     * or with status 500 (Internal Server Error) if the timeTask couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/time-tasks")
    @Timed
    public ResponseEntity<TimeTask> updateTimeTask(@Valid @RequestBody TimeTask timeTask) throws URISyntaxException {
        log.debug("REST request to update TimeTask : {}", timeTask);
        if (timeTask.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TimeTask result = timeTaskService.save(timeTask);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, timeTask.getId().toString()))
            .body(result);
    }

    /**
     * GET  /time-tasks : get all the timeTasks.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of timeTasks in body
     */
    @GetMapping("/time-tasks")
    @Timed
    public ResponseEntity<List<TimeTask>> getAllTimeTasks(Pageable pageable) {
        log.debug("REST request to get a page of TimeTasks");
        Page<TimeTask> page = timeTaskService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/time-tasks");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /time-tasks/:id : get the "id" timeTask.
     *
     * @param id the id of the timeTask to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the timeTask, or with status 404 (Not Found)
     */
    @GetMapping("/time-tasks/{id}")
    @Timed
    public ResponseEntity<TimeTask> getTimeTask(@PathVariable String id) {
        log.debug("REST request to get TimeTask : {}", id);
        Optional<TimeTask> timeTask = timeTaskService.findOne(id);
        return ResponseUtil.wrapOrNotFound(timeTask);
    }

    /**
     * DELETE  /time-tasks/:id : delete the "id" timeTask.
     *
     * @param id the id of the timeTask to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/time-tasks/{id}")
    @Timed
    public ResponseEntity<Void> deleteTimeTask(@PathVariable String id) {
        log.debug("REST request to delete TimeTask : {}", id);
        timeTaskService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id)).build();
    }
}
