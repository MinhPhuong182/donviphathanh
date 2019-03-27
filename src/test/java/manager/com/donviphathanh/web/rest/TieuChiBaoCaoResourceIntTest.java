package manager.com.donviphathanh.web.rest;

import manager.com.donviphathanh.DonviphathanhApp;

import manager.com.donviphathanh.domain.TieuChiBaoCao;
import manager.com.donviphathanh.repository.TieuChiBaoCaoRepository;
import manager.com.donviphathanh.service.TieuChiBaoCaoService;
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
 * Test class for the TieuChiBaoCaoResource REST controller.
 *
 * @see TieuChiBaoCaoResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = DonviphathanhApp.class)
public class TieuChiBaoCaoResourceIntTest {

    private static final String DEFAULT_TIEU_CHI_BAO_CAO_CODE = "AAAAAAAAAA";
    private static final String UPDATED_TIEU_CHI_BAO_CAO_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_TIEU_CHI_CODE = "AAAAAAAAAA";
    private static final String UPDATED_TIEU_CHI_CODE = "BBBBBBBBBB";

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
    private TieuChiBaoCaoRepository tieuChiBaoCaoRepository;

    @Autowired
    private TieuChiBaoCaoService tieuChiBaoCaoService;

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

    private MockMvc restTieuChiBaoCaoMockMvc;

    private TieuChiBaoCao tieuChiBaoCao;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TieuChiBaoCaoResource tieuChiBaoCaoResource = new TieuChiBaoCaoResource(tieuChiBaoCaoService);
        this.restTieuChiBaoCaoMockMvc = MockMvcBuilders.standaloneSetup(tieuChiBaoCaoResource)
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
    public static TieuChiBaoCao createEntity(EntityManager em) {
        TieuChiBaoCao tieuChiBaoCao = new TieuChiBaoCao()
            .tieuChiBaoCaoCode(DEFAULT_TIEU_CHI_BAO_CAO_CODE)
            .tieuChiCode(DEFAULT_TIEU_CHI_CODE)
            .nhomDanhMucCode(DEFAULT_NHOM_DANH_MUC_CODE)
            .userName(DEFAULT_USER_NAME)
            .createTime(DEFAULT_CREATE_TIME)
            .updateTime(DEFAULT_UPDATE_TIME)
            .status(DEFAULT_STATUS)
            .program(DEFAULT_PROGRAM);
        return tieuChiBaoCao;
    }

    @Before
    public void initTest() {
        tieuChiBaoCao = createEntity(em);
    }

    @Test
    @Transactional
    public void createTieuChiBaoCao() throws Exception {
        int databaseSizeBeforeCreate = tieuChiBaoCaoRepository.findAll().size();

        // Create the TieuChiBaoCao
        restTieuChiBaoCaoMockMvc.perform(post("/api/tieu-chi-bao-caos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tieuChiBaoCao)))
            .andExpect(status().isCreated());

        // Validate the TieuChiBaoCao in the database
        List<TieuChiBaoCao> tieuChiBaoCaoList = tieuChiBaoCaoRepository.findAll();
        assertThat(tieuChiBaoCaoList).hasSize(databaseSizeBeforeCreate + 1);
        TieuChiBaoCao testTieuChiBaoCao = tieuChiBaoCaoList.get(tieuChiBaoCaoList.size() - 1);
        assertThat(testTieuChiBaoCao.getTieuChiBaoCaoCode()).isEqualTo(DEFAULT_TIEU_CHI_BAO_CAO_CODE);
        assertThat(testTieuChiBaoCao.getTieuChiCode()).isEqualTo(DEFAULT_TIEU_CHI_CODE);
        assertThat(testTieuChiBaoCao.getNhomDanhMucCode()).isEqualTo(DEFAULT_NHOM_DANH_MUC_CODE);
        assertThat(testTieuChiBaoCao.getUserName()).isEqualTo(DEFAULT_USER_NAME);
        assertThat(testTieuChiBaoCao.getCreateTime()).isEqualTo(DEFAULT_CREATE_TIME);
        assertThat(testTieuChiBaoCao.getUpdateTime()).isEqualTo(DEFAULT_UPDATE_TIME);
        assertThat(testTieuChiBaoCao.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testTieuChiBaoCao.getProgram()).isEqualTo(DEFAULT_PROGRAM);
    }

    @Test
    @Transactional
    public void createTieuChiBaoCaoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = tieuChiBaoCaoRepository.findAll().size();

        // Create the TieuChiBaoCao with an existing ID
        tieuChiBaoCao.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTieuChiBaoCaoMockMvc.perform(post("/api/tieu-chi-bao-caos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tieuChiBaoCao)))
            .andExpect(status().isBadRequest());

        // Validate the TieuChiBaoCao in the database
        List<TieuChiBaoCao> tieuChiBaoCaoList = tieuChiBaoCaoRepository.findAll();
        assertThat(tieuChiBaoCaoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkTieuChiBaoCaoCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = tieuChiBaoCaoRepository.findAll().size();
        // set the field null
        tieuChiBaoCao.setTieuChiBaoCaoCode(null);

        // Create the TieuChiBaoCao, which fails.

        restTieuChiBaoCaoMockMvc.perform(post("/api/tieu-chi-bao-caos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tieuChiBaoCao)))
            .andExpect(status().isBadRequest());

        List<TieuChiBaoCao> tieuChiBaoCaoList = tieuChiBaoCaoRepository.findAll();
        assertThat(tieuChiBaoCaoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTieuChiBaoCaos() throws Exception {
        // Initialize the database
        tieuChiBaoCaoRepository.saveAndFlush(tieuChiBaoCao);

        // Get all the tieuChiBaoCaoList
        restTieuChiBaoCaoMockMvc.perform(get("/api/tieu-chi-bao-caos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tieuChiBaoCao.getId().intValue())))
            .andExpect(jsonPath("$.[*].tieuChiBaoCaoCode").value(hasItem(DEFAULT_TIEU_CHI_BAO_CAO_CODE.toString())))
            .andExpect(jsonPath("$.[*].tieuChiCode").value(hasItem(DEFAULT_TIEU_CHI_CODE.toString())))
            .andExpect(jsonPath("$.[*].nhomDanhMucCode").value(hasItem(DEFAULT_NHOM_DANH_MUC_CODE.toString())))
            .andExpect(jsonPath("$.[*].userName").value(hasItem(DEFAULT_USER_NAME.toString())))
            .andExpect(jsonPath("$.[*].createTime").value(hasItem(DEFAULT_CREATE_TIME.toString())))
            .andExpect(jsonPath("$.[*].updateTime").value(hasItem(sameInstant(DEFAULT_UPDATE_TIME))))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].program").value(hasItem(DEFAULT_PROGRAM.toString())));
    }
    
    @Test
    @Transactional
    public void getTieuChiBaoCao() throws Exception {
        // Initialize the database
        tieuChiBaoCaoRepository.saveAndFlush(tieuChiBaoCao);

        // Get the tieuChiBaoCao
        restTieuChiBaoCaoMockMvc.perform(get("/api/tieu-chi-bao-caos/{id}", tieuChiBaoCao.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(tieuChiBaoCao.getId().intValue()))
            .andExpect(jsonPath("$.tieuChiBaoCaoCode").value(DEFAULT_TIEU_CHI_BAO_CAO_CODE.toString()))
            .andExpect(jsonPath("$.tieuChiCode").value(DEFAULT_TIEU_CHI_CODE.toString()))
            .andExpect(jsonPath("$.nhomDanhMucCode").value(DEFAULT_NHOM_DANH_MUC_CODE.toString()))
            .andExpect(jsonPath("$.userName").value(DEFAULT_USER_NAME.toString()))
            .andExpect(jsonPath("$.createTime").value(DEFAULT_CREATE_TIME.toString()))
            .andExpect(jsonPath("$.updateTime").value(sameInstant(DEFAULT_UPDATE_TIME)))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.program").value(DEFAULT_PROGRAM.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingTieuChiBaoCao() throws Exception {
        // Get the tieuChiBaoCao
        restTieuChiBaoCaoMockMvc.perform(get("/api/tieu-chi-bao-caos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTieuChiBaoCao() throws Exception {
        // Initialize the database
        tieuChiBaoCaoService.save(tieuChiBaoCao);

        int databaseSizeBeforeUpdate = tieuChiBaoCaoRepository.findAll().size();

        // Update the tieuChiBaoCao
        TieuChiBaoCao updatedTieuChiBaoCao = tieuChiBaoCaoRepository.findById(tieuChiBaoCao.getId()).get();
        // Disconnect from session so that the updates on updatedTieuChiBaoCao are not directly saved in db
        em.detach(updatedTieuChiBaoCao);
        updatedTieuChiBaoCao
            .tieuChiBaoCaoCode(UPDATED_TIEU_CHI_BAO_CAO_CODE)
            .tieuChiCode(UPDATED_TIEU_CHI_CODE)
            .nhomDanhMucCode(UPDATED_NHOM_DANH_MUC_CODE)
            .userName(UPDATED_USER_NAME)
            .createTime(UPDATED_CREATE_TIME)
            .updateTime(UPDATED_UPDATE_TIME)
            .status(UPDATED_STATUS)
            .program(UPDATED_PROGRAM);

        restTieuChiBaoCaoMockMvc.perform(put("/api/tieu-chi-bao-caos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedTieuChiBaoCao)))
            .andExpect(status().isOk());

        // Validate the TieuChiBaoCao in the database
        List<TieuChiBaoCao> tieuChiBaoCaoList = tieuChiBaoCaoRepository.findAll();
        assertThat(tieuChiBaoCaoList).hasSize(databaseSizeBeforeUpdate);
        TieuChiBaoCao testTieuChiBaoCao = tieuChiBaoCaoList.get(tieuChiBaoCaoList.size() - 1);
        assertThat(testTieuChiBaoCao.getTieuChiBaoCaoCode()).isEqualTo(UPDATED_TIEU_CHI_BAO_CAO_CODE);
        assertThat(testTieuChiBaoCao.getTieuChiCode()).isEqualTo(UPDATED_TIEU_CHI_CODE);
        assertThat(testTieuChiBaoCao.getNhomDanhMucCode()).isEqualTo(UPDATED_NHOM_DANH_MUC_CODE);
        assertThat(testTieuChiBaoCao.getUserName()).isEqualTo(UPDATED_USER_NAME);
        assertThat(testTieuChiBaoCao.getCreateTime()).isEqualTo(UPDATED_CREATE_TIME);
        assertThat(testTieuChiBaoCao.getUpdateTime()).isEqualTo(UPDATED_UPDATE_TIME);
        assertThat(testTieuChiBaoCao.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testTieuChiBaoCao.getProgram()).isEqualTo(UPDATED_PROGRAM);
    }

    @Test
    @Transactional
    public void updateNonExistingTieuChiBaoCao() throws Exception {
        int databaseSizeBeforeUpdate = tieuChiBaoCaoRepository.findAll().size();

        // Create the TieuChiBaoCao

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTieuChiBaoCaoMockMvc.perform(put("/api/tieu-chi-bao-caos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tieuChiBaoCao)))
            .andExpect(status().isBadRequest());

        // Validate the TieuChiBaoCao in the database
        List<TieuChiBaoCao> tieuChiBaoCaoList = tieuChiBaoCaoRepository.findAll();
        assertThat(tieuChiBaoCaoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTieuChiBaoCao() throws Exception {
        // Initialize the database
        tieuChiBaoCaoService.save(tieuChiBaoCao);

        int databaseSizeBeforeDelete = tieuChiBaoCaoRepository.findAll().size();

        // Delete the tieuChiBaoCao
        restTieuChiBaoCaoMockMvc.perform(delete("/api/tieu-chi-bao-caos/{id}", tieuChiBaoCao.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<TieuChiBaoCao> tieuChiBaoCaoList = tieuChiBaoCaoRepository.findAll();
        assertThat(tieuChiBaoCaoList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TieuChiBaoCao.class);
        TieuChiBaoCao tieuChiBaoCao1 = new TieuChiBaoCao();
        tieuChiBaoCao1.setId(1L);
        TieuChiBaoCao tieuChiBaoCao2 = new TieuChiBaoCao();
        tieuChiBaoCao2.setId(tieuChiBaoCao1.getId());
        assertThat(tieuChiBaoCao1).isEqualTo(tieuChiBaoCao2);
        tieuChiBaoCao2.setId(2L);
        assertThat(tieuChiBaoCao1).isNotEqualTo(tieuChiBaoCao2);
        tieuChiBaoCao1.setId(null);
        assertThat(tieuChiBaoCao1).isNotEqualTo(tieuChiBaoCao2);
    }
}
