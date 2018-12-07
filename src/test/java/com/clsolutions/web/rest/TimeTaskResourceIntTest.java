package com.clsolutions.web.rest;

import com.clsolutions.KonsultklockanApp;

import com.clsolutions.domain.TimeTask;
import com.clsolutions.repository.TimeTaskRepository;
import com.clsolutions.service.TimeTaskService;
import com.clsolutions.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;


import static com.clsolutions.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the TimeTaskResource REST controller.
 *
 * @see TimeTaskResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = KonsultklockanApp.class)
public class TimeTaskResourceIntTest {

    private static final LocalDate DEFAULT_START = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_START = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_STOP = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_STOP = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_NOTE = "AAAAAAAAAA";
    private static final String UPDATED_NOTE = "BBBBBBBBBB";

    @Autowired
    private TimeTaskRepository timeTaskRepository;
    
    @Autowired
    private TimeTaskService timeTaskService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    private MockMvc restTimeTaskMockMvc;

    private TimeTask timeTask;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TimeTaskResource timeTaskResource = new TimeTaskResource(timeTaskService);
        this.restTimeTaskMockMvc = MockMvcBuilders.standaloneSetup(timeTaskResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TimeTask createEntity() {
        TimeTask timeTask = new TimeTask()
            .start(DEFAULT_START)
            .stop(DEFAULT_STOP)
            .note(DEFAULT_NOTE);
        return timeTask;
    }

    @Before
    public void initTest() {
        timeTaskRepository.deleteAll();
        timeTask = createEntity();
    }

    @Test
    public void createTimeTask() throws Exception {
        int databaseSizeBeforeCreate = timeTaskRepository.findAll().size();

        // Create the TimeTask
        restTimeTaskMockMvc.perform(post("/api/time-tasks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(timeTask)))
            .andExpect(status().isCreated());

        // Validate the TimeTask in the database
        List<TimeTask> timeTaskList = timeTaskRepository.findAll();
        assertThat(timeTaskList).hasSize(databaseSizeBeforeCreate + 1);
        TimeTask testTimeTask = timeTaskList.get(timeTaskList.size() - 1);
        assertThat(testTimeTask.getStart()).isEqualTo(DEFAULT_START);
        assertThat(testTimeTask.getStop()).isEqualTo(DEFAULT_STOP);
        assertThat(testTimeTask.getNote()).isEqualTo(DEFAULT_NOTE);
    }

    @Test
    public void createTimeTaskWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = timeTaskRepository.findAll().size();

        // Create the TimeTask with an existing ID
        timeTask.setId("existing_id");

        // An entity with an existing ID cannot be created, so this API call must fail
        restTimeTaskMockMvc.perform(post("/api/time-tasks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(timeTask)))
            .andExpect(status().isBadRequest());

        // Validate the TimeTask in the database
        List<TimeTask> timeTaskList = timeTaskRepository.findAll();
        assertThat(timeTaskList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    public void checkStartIsRequired() throws Exception {
        int databaseSizeBeforeTest = timeTaskRepository.findAll().size();
        // set the field null
        timeTask.setStart(null);

        // Create the TimeTask, which fails.

        restTimeTaskMockMvc.perform(post("/api/time-tasks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(timeTask)))
            .andExpect(status().isBadRequest());

        List<TimeTask> timeTaskList = timeTaskRepository.findAll();
        assertThat(timeTaskList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void getAllTimeTasks() throws Exception {
        // Initialize the database
        timeTaskRepository.save(timeTask);

        // Get all the timeTaskList
        restTimeTaskMockMvc.perform(get("/api/time-tasks?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(timeTask.getId())))
            .andExpect(jsonPath("$.[*].start").value(hasItem(DEFAULT_START.toString())))
            .andExpect(jsonPath("$.[*].stop").value(hasItem(DEFAULT_STOP.toString())))
            .andExpect(jsonPath("$.[*].note").value(hasItem(DEFAULT_NOTE.toString())));
    }
    
    @Test
    public void getTimeTask() throws Exception {
        // Initialize the database
        timeTaskRepository.save(timeTask);

        // Get the timeTask
        restTimeTaskMockMvc.perform(get("/api/time-tasks/{id}", timeTask.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(timeTask.getId()))
            .andExpect(jsonPath("$.start").value(DEFAULT_START.toString()))
            .andExpect(jsonPath("$.stop").value(DEFAULT_STOP.toString()))
            .andExpect(jsonPath("$.note").value(DEFAULT_NOTE.toString()));
    }

    @Test
    public void getNonExistingTimeTask() throws Exception {
        // Get the timeTask
        restTimeTaskMockMvc.perform(get("/api/time-tasks/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateTimeTask() throws Exception {
        // Initialize the database
        timeTaskService.save(timeTask);

        int databaseSizeBeforeUpdate = timeTaskRepository.findAll().size();

        // Update the timeTask
        TimeTask updatedTimeTask = timeTaskRepository.findById(timeTask.getId()).get();
        updatedTimeTask
            .start(UPDATED_START)
            .stop(UPDATED_STOP)
            .note(UPDATED_NOTE);

        restTimeTaskMockMvc.perform(put("/api/time-tasks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedTimeTask)))
            .andExpect(status().isOk());

        // Validate the TimeTask in the database
        List<TimeTask> timeTaskList = timeTaskRepository.findAll();
        assertThat(timeTaskList).hasSize(databaseSizeBeforeUpdate);
        TimeTask testTimeTask = timeTaskList.get(timeTaskList.size() - 1);
        assertThat(testTimeTask.getStart()).isEqualTo(UPDATED_START);
        assertThat(testTimeTask.getStop()).isEqualTo(UPDATED_STOP);
        assertThat(testTimeTask.getNote()).isEqualTo(UPDATED_NOTE);
    }

    @Test
    public void updateNonExistingTimeTask() throws Exception {
        int databaseSizeBeforeUpdate = timeTaskRepository.findAll().size();

        // Create the TimeTask

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTimeTaskMockMvc.perform(put("/api/time-tasks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(timeTask)))
            .andExpect(status().isBadRequest());

        // Validate the TimeTask in the database
        List<TimeTask> timeTaskList = timeTaskRepository.findAll();
        assertThat(timeTaskList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    public void deleteTimeTask() throws Exception {
        // Initialize the database
        timeTaskService.save(timeTask);

        int databaseSizeBeforeDelete = timeTaskRepository.findAll().size();

        // Get the timeTask
        restTimeTaskMockMvc.perform(delete("/api/time-tasks/{id}", timeTask.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<TimeTask> timeTaskList = timeTaskRepository.findAll();
        assertThat(timeTaskList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TimeTask.class);
        TimeTask timeTask1 = new TimeTask();
        timeTask1.setId("id1");
        TimeTask timeTask2 = new TimeTask();
        timeTask2.setId(timeTask1.getId());
        assertThat(timeTask1).isEqualTo(timeTask2);
        timeTask2.setId("id2");
        assertThat(timeTask1).isNotEqualTo(timeTask2);
        timeTask1.setId(null);
        assertThat(timeTask1).isNotEqualTo(timeTask2);
    }
}
