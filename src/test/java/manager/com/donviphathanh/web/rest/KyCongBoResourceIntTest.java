package manager.com.donviphathanh.web.rest;

import manager.com.donviphathanh.DonviphathanhApp;

import manager.com.donviphathanh.domain.KyCongBo;
import manager.com.donviphathanh.repository.KyCongBoRepository;
import manager.com.donviphathanh.service.KyCongBoService;
import manager.com.donviphathanh.web.rest.errors.ExceptionTranslator;

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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.util.List;


import static manager.com.donviphathanh.web.rest.TestUtil.sameInstant;
import static manager.com.donviphathanh.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the KyCongBoResource REST controller.
 *
 * @see KyCongBoResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = DonviphathanhApp.class)
public class KyCongBoResourceIntTest {

    private static final String DEFAULT_KY_CONG_BO_CODE = "AAAAAAAAAA";
    private static final String UPDATED_KY_CONG_BO_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_NHOM_DANH_MUC_CODE = "AAAAAAAAAA";
    private static final String UPDATED_NHOM_DANH_MUC_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_USER_NAME = "AAAAAAAAAA";
    private static final String UPDATED_USER_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_CREATE_TIME = "AAAAAAAAAA";
    private static final String UPDATED_CREATE_TIME = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_UPDATE_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_UPDATE_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_STATUS = "BBBBBBBBBB";

    private static final String DEFAULT_PROGRAM = "AAAAAAAAAA";
    private static final String UPDATED_PROGRAM = "BBBBBBBBBB";

    @Autowired
    private KyCongBoRepository kyCongBoRepository;

    @Autowired
    private KyCongBoService kyCongBoService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restKyCongBoMockMvc;

    private KyCongBo kyCongBo;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final KyCongBoResource kyCongBoResource = new KyCongBoResource(kyCongBoService);
        this.restKyCongBoMockMvc = MockMvcBuilders.standaloneSetup(kyCongBoResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static KyCongBo createEntity(EntityManager em) {
        KyCongBo kyCongBo = new KyCongBo()
            .kyCongBoCode(DEFAULT_KY_CONG_BO_CODE)
            .name(DEFAULT_NAME)
            .nhomDanhMucCode(DEFAULT_NHOM_DANH_MUC_CODE)
            .userName(DEFAULT_USER_NAME)
            .createTime(DEFAULT_CREATE_TIME)
            .updateTime(DEFAULT_UPDATE_TIME)
            .status(DEFAULT_STATUS)
            .program(DEFAULT_PROGRAM);
        return kyCongBo;
    }

    @Before
    public void initTest() {
        kyCongBo = createEntity(em);
    }

    @Test
    @Transactional
    public void createKyCongBo() throws Exception {
        int databaseSizeBeforeCreate = kyCongBoRepository.findAll().size();

        // Create the KyCongBo
        restKyCongBoMockMvc.perform(post("/api/ky-cong-bos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(kyCongBo)))
            .andExpect(status().isCreated());

        // Validate the KyCongBo in the database
        List<KyCongBo> kyCongBoList = kyCongBoRepository.findAll();
        assertThat(kyCongBoList).hasSize(databaseSizeBeforeCreate + 1);
        KyCongBo testKyCongBo = kyCongBoList.get(kyCongBoList.size() - 1);
        assertThat(testKyCongBo.getKyCongBoCode()).isEqualTo(DEFAULT_KY_CONG_BO_CODE);
        assertThat(testKyCongBo.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testKyCongBo.getNhomDanhMucCode()).isEqualTo(DEFAULT_NHOM_DANH_MUC_CODE);
        assertThat(testKyCongBo.getUserName()).isEqualTo(DEFAULT_USER_NAME);
        assertThat(testKyCongBo.getCreateTime()).isEqualTo(DEFAULT_CREATE_TIME);
        assertThat(testKyCongBo.getUpdateTime()).isEqualTo(DEFAULT_UPDATE_TIME);
        assertThat(testKyCongBo.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testKyCongBo.getProgram()).isEqualTo(DEFAULT_PROGRAM);
    }

    @Test
    @Transactional
    public void createKyCongBoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = kyCongBoRepository.findAll().size();

        // Create the KyCongBo with an existing ID
        kyCongBo.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restKyCongBoMockMvc.perform(post("/api/ky-cong-bos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(kyCongBo)))
            .andExpect(status().isBadRequest());

        // Validate the KyCongBo in the database
        List<KyCongBo> kyCongBoList = kyCongBoRepository.findAll();
        assertThat(kyCongBoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkKyCongBoCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = kyCongBoRepository.findAll().size();
        // set the field null
        kyCongBo.setKyCongBoCode(null);

        // Create the KyCongBo, which fails.

        restKyCongBoMockMvc.perform(post("/api/ky-cong-bos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(kyCongBo)))
            .andExpect(status().isBadRequest());

        List<KyCongBo> kyCongBoList = kyCongBoRepository.findAll();
        assertThat(kyCongBoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllKyCongBos() throws Exception {
        // Initialize the database
        kyCongBoRepository.saveAndFlush(kyCongBo);

        // Get all the kyCongBoList
        restKyCongBoMockMvc.perform(get("/api/ky-cong-bos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(kyCongBo.getId().intValue())))
            .andExpect(jsonPath("$.[*].kyCongBoCode").value(hasItem(DEFAULT_KY_CONG_BO_CODE.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].nhomDanhMucCode").value(hasItem(DEFAULT_NHOM_DANH_MUC_CODE.toString())))
            .andExpect(jsonPath("$.[*].userName").value(hasItem(DEFAULT_USER_NAME.toString())))
            .andExpect(jsonPath("$.[*].createTime").value(hasItem(DEFAULT_CREATE_TIME.toString())))
            .andExpect(jsonPath("$.[*].updateTime").value(hasItem(sameInstant(DEFAULT_UPDATE_TIME))))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].program").value(hasItem(DEFAULT_PROGRAM.toString())));
    }
    
    @Test
    @Transactional
    public void getKyCongBo() throws Exception {
        // Initialize the database
        kyCongBoRepository.saveAndFlush(kyCongBo);

        // Get the kyCongBo
        restKyCongBoMockMvc.perform(get("/api/ky-cong-bos/{id}", kyCongBo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(kyCongBo.getId().intValue()))
            .andExpect(jsonPath("$.kyCongBoCode").value(DEFAULT_KY_CONG_BO_CODE.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.nhomDanhMucCode").value(DEFAULT_NHOM_DANH_MUC_CODE.toString()))
            .andExpect(jsonPath("$.userName").value(DEFAULT_USER_NAME.toString()))
            .andExpect(jsonPath("$.createTime").value(DEFAULT_CREATE_TIME.toString()))
            .andExpect(jsonPath("$.updateTime").value(sameInstant(DEFAULT_UPDATE_TIME)))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.program").value(DEFAULT_PROGRAM.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingKyCongBo() throws Exception {
        // Get the kyCongBo
        restKyCongBoMockMvc.perform(get("/api/ky-cong-bos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateKyCongBo() throws Exception {
        // Initialize the database
        kyCongBoService.save(kyCongBo);

        int databaseSizeBeforeUpdate = kyCongBoRepository.findAll().size();

        // Update the kyCongBo
        KyCongBo updatedKyCongBo = kyCongBoRepository.findById(kyCongBo.getId()).get();
        // Disconnect from session so that the updates on updatedKyCongBo are not directly saved in db
        em.detach(updatedKyCongBo);
        updatedKyCongBo
            .kyCongBoCode(UPDATED_KY_CONG_BO_CODE)
            .name(UPDATED_NAME)
            .nhomDanhMucCode(UPDATED_NHOM_DANH_MUC_CODE)
            .userName(UPDATED_USER_NAME)
            .createTime(UPDATED_CREATE_TIME)
            .updateTime(UPDATED_UPDATE_TIME)
            .status(UPDATED_STATUS)
            .program(UPDATED_PROGRAM);

        restKyCongBoMockMvc.perform(put("/api/ky-cong-bos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedKyCongBo)))
            .andExpect(status().isOk());

        // Validate the KyCongBo in the database
        List<KyCongBo> kyCongBoList = kyCongBoRepository.findAll();
        assertThat(kyCongBoList).hasSize(databaseSizeBeforeUpdate);
        KyCongBo testKyCongBo = kyCongBoList.get(kyCongBoList.size() - 1);
        assertThat(testKyCongBo.getKyCongBoCode()).isEqualTo(UPDATED_KY_CONG_BO_CODE);
        assertThat(testKyCongBo.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testKyCongBo.getNhomDanhMucCode()).isEqualTo(UPDATED_NHOM_DANH_MUC_CODE);
        assertThat(testKyCongBo.getUserName()).isEqualTo(UPDATED_USER_NAME);
        assertThat(testKyCongBo.getCreateTime()).isEqualTo(UPDATED_CREATE_TIME);
        assertThat(testKyCongBo.getUpdateTime()).isEqualTo(UPDATED_UPDATE_TIME);
        assertThat(testKyCongBo.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testKyCongBo.getProgram()).isEqualTo(UPDATED_PROGRAM);
    }

    @Test
    @Transactional
    public void updateNonExistingKyCongBo() throws Exception {
        int databaseSizeBeforeUpdate = kyCongBoRepository.findAll().size();

        // Create the KyCongBo

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restKyCongBoMockMvc.perform(put("/api/ky-cong-bos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(kyCongBo)))
            .andExpect(status().isBadRequest());

        // Validate the KyCongBo in the database
        List<KyCongBo> kyCongBoList = kyCongBoRepository.findAll();
        assertThat(kyCongBoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteKyCongBo() throws Exception {
        // Initialize the database
        kyCongBoService.save(kyCongBo);

        int databaseSizeBeforeDelete = kyCongBoRepository.findAll().size();

        // Delete the kyCongBo
        restKyCongBoMockMvc.perform(delete("/api/ky-cong-bos/{id}", kyCongBo.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<KyCongBo> kyCongBoList = kyCongBoRepository.findAll();
        assertThat(kyCongBoList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(KyCongBo.class);
        KyCongBo kyCongBo1 = new KyCongBo();
        kyCongBo1.setId(1L);
        KyCongBo kyCongBo2 = new KyCongBo();
        kyCongBo2.setId(kyCongBo1.getId());
        assertThat(kyCongBo1).isEqualTo(kyCongBo2);
        kyCongBo2.setId(2L);
        assertThat(kyCongBo1).isNotEqualTo(kyCongBo2);
        kyCongBo1.setId(null);
        assertThat(kyCongBo1).isNotEqualTo(kyCongBo2);
    }
}
