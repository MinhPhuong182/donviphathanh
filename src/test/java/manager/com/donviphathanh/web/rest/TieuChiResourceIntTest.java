package manager.com.donviphathanh.web.rest;

import manager.com.donviphathanh.DonviphathanhApp;

import manager.com.donviphathanh.domain.TieuChi;
import manager.com.donviphathanh.repository.TieuChiRepository;
import manager.com.donviphathanh.service.TieuChiService;
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
 * Test class for the TieuChiResource REST controller.
 *
 * @see TieuChiResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = DonviphathanhApp.class)
public class TieuChiResourceIntTest {

    private static final String DEFAULT_TIEU_CHI_CODE = "AAAAAAAAAA";
    private static final String UPDATED_TIEU_CHI_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_CHITIEU_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CHITIEU_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_KY_CONG_BO_CODE = "AAAAAAAAAA";
    private static final String UPDATED_KY_CONG_BO_CODE = "BBBBBBBBBB";

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
    private TieuChiRepository tieuChiRepository;

    @Autowired
    private TieuChiService tieuChiService;

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

    private MockMvc restTieuChiMockMvc;

    private TieuChi tieuChi;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TieuChiResource tieuChiResource = new TieuChiResource(tieuChiService);
        this.restTieuChiMockMvc = MockMvcBuilders.standaloneSetup(tieuChiResource)
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
    public static TieuChi createEntity(EntityManager em) {
        TieuChi tieuChi = new TieuChi()
            .tieuChiCode(DEFAULT_TIEU_CHI_CODE)
            .name(DEFAULT_NAME)
            .chitieuCode(DEFAULT_CHITIEU_CODE)
            .kyCongBoCode(DEFAULT_KY_CONG_BO_CODE)
            .userName(DEFAULT_USER_NAME)
            .createTime(DEFAULT_CREATE_TIME)
            .updateTime(DEFAULT_UPDATE_TIME)
            .status(DEFAULT_STATUS)
            .program(DEFAULT_PROGRAM);
        return tieuChi;
    }

    @Before
    public void initTest() {
        tieuChi = createEntity(em);
    }

    @Test
    @Transactional
    public void createTieuChi() throws Exception {
        int databaseSizeBeforeCreate = tieuChiRepository.findAll().size();

        // Create the TieuChi
        restTieuChiMockMvc.perform(post("/api/tieu-chis")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tieuChi)))
            .andExpect(status().isCreated());

        // Validate the TieuChi in the database
        List<TieuChi> tieuChiList = tieuChiRepository.findAll();
        assertThat(tieuChiList).hasSize(databaseSizeBeforeCreate + 1);
        TieuChi testTieuChi = tieuChiList.get(tieuChiList.size() - 1);
        assertThat(testTieuChi.getTieuChiCode()).isEqualTo(DEFAULT_TIEU_CHI_CODE);
        assertThat(testTieuChi.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testTieuChi.getChitieuCode()).isEqualTo(DEFAULT_CHITIEU_CODE);
        assertThat(testTieuChi.getKyCongBoCode()).isEqualTo(DEFAULT_KY_CONG_BO_CODE);
        assertThat(testTieuChi.getUserName()).isEqualTo(DEFAULT_USER_NAME);
        assertThat(testTieuChi.getCreateTime()).isEqualTo(DEFAULT_CREATE_TIME);
        assertThat(testTieuChi.getUpdateTime()).isEqualTo(DEFAULT_UPDATE_TIME);
        assertThat(testTieuChi.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testTieuChi.getProgram()).isEqualTo(DEFAULT_PROGRAM);
    }

    @Test
    @Transactional
    public void createTieuChiWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = tieuChiRepository.findAll().size();

        // Create the TieuChi with an existing ID
        tieuChi.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTieuChiMockMvc.perform(post("/api/tieu-chis")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tieuChi)))
            .andExpect(status().isBadRequest());

        // Validate the TieuChi in the database
        List<TieuChi> tieuChiList = tieuChiRepository.findAll();
        assertThat(tieuChiList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkTieuChiCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = tieuChiRepository.findAll().size();
        // set the field null
        tieuChi.setTieuChiCode(null);

        // Create the TieuChi, which fails.

        restTieuChiMockMvc.perform(post("/api/tieu-chis")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tieuChi)))
            .andExpect(status().isBadRequest());

        List<TieuChi> tieuChiList = tieuChiRepository.findAll();
        assertThat(tieuChiList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTieuChis() throws Exception {
        // Initialize the database
        tieuChiRepository.saveAndFlush(tieuChi);

        // Get all the tieuChiList
        restTieuChiMockMvc.perform(get("/api/tieu-chis?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tieuChi.getId().intValue())))
            .andExpect(jsonPath("$.[*].tieuChiCode").value(hasItem(DEFAULT_TIEU_CHI_CODE.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].chitieuCode").value(hasItem(DEFAULT_CHITIEU_CODE.toString())))
            .andExpect(jsonPath("$.[*].kyCongBoCode").value(hasItem(DEFAULT_KY_CONG_BO_CODE.toString())))
            .andExpect(jsonPath("$.[*].userName").value(hasItem(DEFAULT_USER_NAME.toString())))
            .andExpect(jsonPath("$.[*].createTime").value(hasItem(DEFAULT_CREATE_TIME.toString())))
            .andExpect(jsonPath("$.[*].updateTime").value(hasItem(sameInstant(DEFAULT_UPDATE_TIME))))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].program").value(hasItem(DEFAULT_PROGRAM.toString())));
    }
    
    @Test
    @Transactional
    public void getTieuChi() throws Exception {
        // Initialize the database
        tieuChiRepository.saveAndFlush(tieuChi);

        // Get the tieuChi
        restTieuChiMockMvc.perform(get("/api/tieu-chis/{id}", tieuChi.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(tieuChi.getId().intValue()))
            .andExpect(jsonPath("$.tieuChiCode").value(DEFAULT_TIEU_CHI_CODE.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.chitieuCode").value(DEFAULT_CHITIEU_CODE.toString()))
            .andExpect(jsonPath("$.kyCongBoCode").value(DEFAULT_KY_CONG_BO_CODE.toString()))
            .andExpect(jsonPath("$.userName").value(DEFAULT_USER_NAME.toString()))
            .andExpect(jsonPath("$.createTime").value(DEFAULT_CREATE_TIME.toString()))
            .andExpect(jsonPath("$.updateTime").value(sameInstant(DEFAULT_UPDATE_TIME)))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.program").value(DEFAULT_PROGRAM.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingTieuChi() throws Exception {
        // Get the tieuChi
        restTieuChiMockMvc.perform(get("/api/tieu-chis/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTieuChi() throws Exception {
        // Initialize the database
        tieuChiService.save(tieuChi);

        int databaseSizeBeforeUpdate = tieuChiRepository.findAll().size();

        // Update the tieuChi
        TieuChi updatedTieuChi = tieuChiRepository.findById(tieuChi.getId()).get();
        // Disconnect from session so that the updates on updatedTieuChi are not directly saved in db
        em.detach(updatedTieuChi);
        updatedTieuChi
            .tieuChiCode(UPDATED_TIEU_CHI_CODE)
            .name(UPDATED_NAME)
            .chitieuCode(UPDATED_CHITIEU_CODE)
            .kyCongBoCode(UPDATED_KY_CONG_BO_CODE)
            .userName(UPDATED_USER_NAME)
            .createTime(UPDATED_CREATE_TIME)
            .updateTime(UPDATED_UPDATE_TIME)
            .status(UPDATED_STATUS)
            .program(UPDATED_PROGRAM);

        restTieuChiMockMvc.perform(put("/api/tieu-chis")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedTieuChi)))
            .andExpect(status().isOk());

        // Validate the TieuChi in the database
        List<TieuChi> tieuChiList = tieuChiRepository.findAll();
        assertThat(tieuChiList).hasSize(databaseSizeBeforeUpdate);
        TieuChi testTieuChi = tieuChiList.get(tieuChiList.size() - 1);
        assertThat(testTieuChi.getTieuChiCode()).isEqualTo(UPDATED_TIEU_CHI_CODE);
        assertThat(testTieuChi.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testTieuChi.getChitieuCode()).isEqualTo(UPDATED_CHITIEU_CODE);
        assertThat(testTieuChi.getKyCongBoCode()).isEqualTo(UPDATED_KY_CONG_BO_CODE);
        assertThat(testTieuChi.getUserName()).isEqualTo(UPDATED_USER_NAME);
        assertThat(testTieuChi.getCreateTime()).isEqualTo(UPDATED_CREATE_TIME);
        assertThat(testTieuChi.getUpdateTime()).isEqualTo(UPDATED_UPDATE_TIME);
        assertThat(testTieuChi.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testTieuChi.getProgram()).isEqualTo(UPDATED_PROGRAM);
    }

    @Test
    @Transactional
    public void updateNonExistingTieuChi() throws Exception {
        int databaseSizeBeforeUpdate = tieuChiRepository.findAll().size();

        // Create the TieuChi

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTieuChiMockMvc.perform(put("/api/tieu-chis")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tieuChi)))
            .andExpect(status().isBadRequest());

        // Validate the TieuChi in the database
        List<TieuChi> tieuChiList = tieuChiRepository.findAll();
        assertThat(tieuChiList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTieuChi() throws Exception {
        // Initialize the database
        tieuChiService.save(tieuChi);

        int databaseSizeBeforeDelete = tieuChiRepository.findAll().size();

        // Delete the tieuChi
        restTieuChiMockMvc.perform(delete("/api/tieu-chis/{id}", tieuChi.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<TieuChi> tieuChiList = tieuChiRepository.findAll();
        assertThat(tieuChiList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TieuChi.class);
        TieuChi tieuChi1 = new TieuChi();
        tieuChi1.setId(1L);
        TieuChi tieuChi2 = new TieuChi();
        tieuChi2.setId(tieuChi1.getId());
        assertThat(tieuChi1).isEqualTo(tieuChi2);
        tieuChi2.setId(2L);
        assertThat(tieuChi1).isNotEqualTo(tieuChi2);
        tieuChi1.setId(null);
        assertThat(tieuChi1).isNotEqualTo(tieuChi2);
    }
}
