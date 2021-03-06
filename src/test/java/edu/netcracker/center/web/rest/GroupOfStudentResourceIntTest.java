package edu.netcracker.center.web.rest;

import edu.netcracker.center.Application;
import edu.netcracker.center.domain.GroupOfStudent;
import edu.netcracker.center.domain.LearningType;
import edu.netcracker.center.repository.GroupOfStudentRepository;
import edu.netcracker.center.repository.LearningResultRepository;
import edu.netcracker.center.repository.LearningTypeRepository;
import edu.netcracker.center.service.GroupOfStudentService;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.hamcrest.Matchers.hasItem;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.cloud.cloudfoundry.com.fasterxml.jackson.annotation.JsonAutoDetect;
import org.springframework.data.web.querydsl.QuerydslPredicateArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test class for the GroupOfStudentResource REST controller.
 *
 * @see GroupOfStudentResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class GroupOfStudentResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAA";
    private static final String UPDATED_NAME = "BBBBB";
    private static final String DEFAULT_DESCRIPTION = "AAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBB";

    private static final Boolean DEFAULT_IS_ACTIVE = false;
    private static final Boolean UPDATED_IS_ACTIVE = true;

    @Inject
    private GroupOfStudentRepository groupOfStudentRepository;

    @Inject
    private LearningTypeRepository learningTypeRepository;

    @Inject
    private GroupOfStudentService groupOfStudentService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private QuerydslPredicateArgumentResolver querydslPredicateArgumentResolver;

    private MockMvc restGroupOfStudentMockMvc;

    private GroupOfStudent groupOfStudent;

    private LearningType learningType;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        GroupOfStudentResource groupOfStudentResource = new GroupOfStudentResource();
        ReflectionTestUtils.setField(groupOfStudentResource, "groupOfStudentService", groupOfStudentService);
        this.restGroupOfStudentMockMvc = MockMvcBuilders.standaloneSetup(groupOfStudentResource)
            .setCustomArgumentResolvers(pageableArgumentResolver, querydslPredicateArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        groupOfStudent = new GroupOfStudent();
        learningType = learningTypeRepository.getOne(1L);
        groupOfStudent.setName(DEFAULT_NAME);
        groupOfStudent.setDescription(DEFAULT_DESCRIPTION);
        groupOfStudent.setIsActive(DEFAULT_IS_ACTIVE);
        groupOfStudent.setLearningType(learningType);
    }

    @Test
    @Transactional
    public void createGroupOfStudent() throws Exception {
        int databaseSizeBeforeCreate = groupOfStudentRepository.findAll().size();

        // Create the GroupOfStudent

        restGroupOfStudentMockMvc.perform(post("/api/groupOfStudents")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(groupOfStudent)))
                .andExpect(status().isCreated());

        // Validate the GroupOfStudent in the database
        List<GroupOfStudent> groupOfStudents = groupOfStudentRepository.findAll();
        assertThat(groupOfStudents).hasSize(databaseSizeBeforeCreate + 1);
        GroupOfStudent testGroupOfStudent = groupOfStudents.get(groupOfStudents.size() - 1);
        assertThat(testGroupOfStudent.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testGroupOfStudent.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testGroupOfStudent.getIsActive()).isEqualTo(DEFAULT_IS_ACTIVE);
        assertThat(testGroupOfStudent.getLearningType().getId()).isEqualTo(learningType.getId());
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = groupOfStudentRepository.findAll().size();
        // set the field null
        groupOfStudent.setName(null);

        // Create the GroupOfStudent, which fails.

        restGroupOfStudentMockMvc.perform(post("/api/groupOfStudents")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(groupOfStudent)))
                .andExpect(status().isBadRequest());

        List<GroupOfStudent> groupOfStudents = groupOfStudentRepository.findAll();
        assertThat(groupOfStudents).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLearningTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = groupOfStudentRepository.findAll().size();
        // set the field null
        groupOfStudent.setLearningType(null);

        // Create the GroupOfStudent, which fails.

        restGroupOfStudentMockMvc.perform(post("/api/groupOfStudents")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(groupOfStudent)))
                .andExpect(status().isBadRequest());

        List<GroupOfStudent> groupOfStudents = groupOfStudentRepository.findAll();
        assertThat(groupOfStudents).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkIsActiveIsRequired() throws Exception {
        int databaseSizeBeforeTest = groupOfStudentRepository.findAll().size();
        // set the field null
        groupOfStudent.setIsActive(null);

        // Create the GroupOfStudent, which fails.

        restGroupOfStudentMockMvc.perform(post("/api/groupOfStudents")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(groupOfStudent)))
                .andExpect(status().isBadRequest());

        List<GroupOfStudent> groupOfStudents = groupOfStudentRepository.findAll();
        assertThat(groupOfStudents).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllGroupOfStudents() throws Exception {
        // Initialize the database
        groupOfStudentRepository.saveAndFlush(groupOfStudent);

        // Get all the groupOfStudents
        restGroupOfStudentMockMvc.perform(get("/api/groupOfStudents?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(groupOfStudent.getId().intValue())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
                .andExpect(jsonPath("$.[*].isActive").value(hasItem(DEFAULT_IS_ACTIVE.booleanValue())));
    }

    @Test
    @Transactional
    public void getGroupOfStudent() throws Exception {
        // Initialize the database
        groupOfStudentRepository.saveAndFlush(groupOfStudent);

        // Get the groupOfStudent
        restGroupOfStudentMockMvc.perform(get("/api/groupOfStudents/{id}", groupOfStudent.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(groupOfStudent.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.isActive").value(DEFAULT_IS_ACTIVE.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingGroupOfStudent() throws Exception {
        // Get the groupOfStudent
        restGroupOfStudentMockMvc.perform(get("/api/groupOfStudents/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateGroupOfStudent() throws Exception {
        // Initialize the database
        groupOfStudentRepository.saveAndFlush(groupOfStudent);

		int databaseSizeBeforeUpdate = groupOfStudentRepository.findAll().size();

        // Update the groupOfStudent
        groupOfStudent.setName(UPDATED_NAME);
        groupOfStudent.setDescription(UPDATED_DESCRIPTION);
        groupOfStudent.setIsActive(UPDATED_IS_ACTIVE);

        restGroupOfStudentMockMvc.perform(put("/api/groupOfStudents")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(groupOfStudent)))
                .andExpect(status().isOk());

        // Validate the GroupOfStudent in the database
        List<GroupOfStudent> groupOfStudents = groupOfStudentRepository.findAll();
        assertThat(groupOfStudents).hasSize(databaseSizeBeforeUpdate);
        GroupOfStudent testGroupOfStudent = groupOfStudents.get(groupOfStudents.size() - 1);
        assertThat(testGroupOfStudent.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testGroupOfStudent.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testGroupOfStudent.getIsActive()).isEqualTo(UPDATED_IS_ACTIVE);
    }

    @Test
    @Transactional
    public void deleteGroupOfStudent() throws Exception {
        // Initialize the database
        groupOfStudentRepository.saveAndFlush(groupOfStudent);

		int databaseSizeBeforeDelete = groupOfStudentRepository.findAll().size();

        // Get the groupOfStudent
        restGroupOfStudentMockMvc.perform(delete("/api/groupOfStudents/{id}", groupOfStudent.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<GroupOfStudent> groupOfStudents = groupOfStudentRepository.findAll();
        assertThat(groupOfStudents).hasSize(databaseSizeBeforeDelete - 1);
    }
}
