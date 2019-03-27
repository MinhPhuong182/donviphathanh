package manager.com.donviphathanh.web.rest;

import manager.com.donviphathanh.DonviphathanhApp;

import manager.com.donviphathanh.domain.MauPhatHanh;
import manager.com.donviphathanh.repository.MauPhatHanhRepository;
import manager.com.donviphathanh.service.MauPhatHanhService;
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
 * Test class for the MauPhatHanhResource REST controller.
 *
 * @see MauPhatHanhResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = DonviphathanhApp.class)
public class MauPhatHanhResourceIntTest {

    private static final String DEFAULT_MAU_PHAT_HANH_CODE = "AAAAAAAAAA";
    private static final String UPDATED_MAU_PHAT_HANH_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_TIEU_CHI_CODE = "AAAAAAAAAA";
    private static final String UPDATED_TIEU_CHI_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_NHOM_PHAN_LOAI_CODE = "AAAAAAAAAA";
    private static final String UPDATED_NHOM_PHAN_LOAI_CODE = "BBBBBBBBBB";

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
    private MauPhatHanhRepository mauPhatHanhRepository;

    @Autowired
    private MauPhatHanhService mauPhatHanhService;

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

    private MockMvc restMauPhatHanhMockMvc;

    private MauPhatHanh mauPhatHanh;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final MauPhatHanhResource mauPhatHanhResource = new MauPhatHanhResource(mauPhatHanhService);
        this.restMauPhatHanhMockMvc = MockMvcBuilders.standaloneSetup(mauPhatHanhResource)
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
    public static MauPhatHanh createEntity(EntityManager em) {
        MauPhatHanh mauPhatHanh = new MauPhatHanh()
            .mauPhatHanhCode(DEFAULT_MAU_PHAT_HANH_CODE)
            .name(DEFAULT_NAME)
            .tieuChiCode(DEFAULT_TIEU_CHI_CODE)
            .nhomPhanLoaiCode(DEFAULT_NHOM_PHAN_LOAI_CODE)
            .userName(DEFAULT_USER_NAME)
            .createTime(DEFAULT_CREATE_TIME)
            .updateTime(DEFAULT_UPDATE_TIME)
            .status(DEFAULT_STATUS)
            .program(DEFAULT_PROGRAM);
        return mauPhatHanh;
    }

    @Before
    public void initTest() {
        mauPhatHanh = createEntity(em);
    }

    @Test
    @Transactional
    public void createMauPhatHanh() throws Exception {
        int databaseSizeBeforeCreate = mauPhatHanhRepository.findAll().size();

        // Create the MauPhatHanh
        restMauPhatHanhMockMvc.perform(post("/api/mau-phat-hanhs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mauPhatHanh)))
            .andExpect(status().isCreated());

        // Validate the MauPhatHanh in the database
        List<MauPhatHanh> mauPhatHanhList = mauPhatHanhRepository.findAll();
        assertThat(mauPhatHanhList).hasSize(databaseSizeBeforeCreate + 1);
        MauPhatHanh testMauPhatHanh = mauPhatHanhList.get(mauPhatHanhList.size() - 1);
        assertThat(testMauPhatHanh.getMauPhatHanhCode()).isEqualTo(DEFAULT_MAU_PHAT_HANH_CODE);
        assertThat(testMauPhatHanh.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testMauPhatHanh.getTieuChiCode()).isEqualTo(DEFAULT_TIEU_CHI_CODE);
        assertThat(testMauPhatHanh.getNhomPhanLoaiCode()).isEqualTo(DEFAULT_NHOM_PHAN_LOAI_CODE);
        assertThat(testMauPhatHanh.getUserName()).isEqualTo(DEFAULT_USER_NAME);
        assertThat(testMauPhatHanh.getCreateTime()).isEqualTo(DEFAULT_CREATE_TIME);
        assertThat(testMauPhatHanh.getUpdateTime()).isEqualTo(DEFAULT_UPDATE_TIME);
        assertThat(testMauPhatHanh.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testMauPhatHanh.getProgram()).isEqualTo(DEFAULT_PROGRAM);
    }

    @Test
    @Transactional
    public void createMauPhatHanhWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = mauPhatHanhRepository.findAll().size();

        // Create the MauPhatHanh with an existing ID
        mauPhatHanh.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMauPhatHanhMockMvc.perform(post("/api/mau-phat-hanhs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mauPhatHanh)))
            .andExpect(status().isBadRequest());

        // Validate the MauPhatHanh in the database
        List<MauPhatHanh> mauPhatHanhList = mauPhatHanhRepository.findAll();
        assertThat(mauPhatHanhList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkMauPhatHanhCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = mauPhatHanhRepository.findAll().size();
        // set the field null
        mauPhatHanh.setMauPhatHanhCode(null);

        // Create the MauPhatHanh, which fails.

        restMauPhatHanhMockMvc.perform(post("/api/mau-phat-hanhs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mauPhatHanh)))
            .andExpect(status().isBadRequest());

        List<MauPhatHanh> mauPhatHanhList = mauPhatHanhRepository.findAll();
        assertThat(mauPhatHanhList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllMauPhatHanhs() throws Exception {
        // Initialize the database
        mauPhatHanhRepository.saveAndFlush(mauPhatHanh);

        // Get all the mauPhatHanhList
        restMauPhatHanhMockMvc.perform(get("/api/mau-phat-hanhs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mauPhatHanh.getId().intValue())))
            .andExpect(jsonPath("$.[*].mauPhatHanhCode").value(hasItem(DEFAULT_MAU_PHAT_HANH_CODE.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].tieuChiCode").value(hasItem(DEFAULT_TIEU_CHI_CODE.toString())))
            .andExpect(jsonPath("$.[*].nhomPhanLoaiCode").value(hasItem(DEFAULT_NHOM_PHAN_LOAI_CODE.toString())))
            .andExpect(jsonPath("$.[*].userName").value(hasItem(DEFAULT_USER_NAME.toString())))
            .andExpect(jsonPath("$.[*].createTime").value(hasItem(DEFAULT_CREATE_TIME.toString())))
            .andExpect(jsonPath("$.[*].updateTime").value(hasItem(sameInstant(DEFAULT_UPDATE_TIME))))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].program").value(hasItem(DEFAULT_PROGRAM.toString())));
    }
    
    @Test
    @Transactional
    public void getMauPhatHanh() throws Exception {
        // Initialize the database
        mauPhatHanhRepository.saveAndFlush(mauPhatHanh);

        // Get the mauPhatHanh
        restMauPhatHanhMockMvc.perform(get("/api/mau-phat-hanhs/{id}", mauPhatHanh.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(mauPhatHanh.getId().intValue()))
            .andExpect(jsonPath("$.mauPhatHanhCode").value(DEFAULT_MAU_PHAT_HANH_CODE.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.tieuChiCode").value(DEFAULT_TIEU_CHI_CODE.toString()))
            .andExpect(jsonPath("$.nhomPhanLoaiCode").value(DEFAULT_NHOM_PHAN_LOAI_CODE.toString()))
            .andExpect(jsonPath("$.userName").value(DEFAULT_USER_NAME.toString()))
            .andExpect(jsonPath("$.createTime").value(DEFAULT_CREATE_TIME.toString()))
            .andExpect(jsonPath("$.updateTime").value(sameInstant(DEFAULT_UPDATE_TIME)))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.program").value(DEFAULT_PROGRAM.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingMauPhatHanh() throws Exception {
        // Get the mauPhatHanh
        restMauPhatHanhMockMvc.perform(get("/api/mau-phat-hanhs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMauPhatHanh() throws Exception {
        // Initialize the database
        mauPhatHanhService.save(mauPhatHanh);

        int databaseSizeBeforeUpdate = mauPhatHanhRepository.findAll().size();

        // Update the mauPhatHanh
        MauPhatHanh updatedMauPhatHanh = mauPhatHanhRepository.findById(mauPhatHanh.getId()).get();
        // Disconnect from session so that the updates on updatedMauPhatHanh are not directly saved in db
        em.detach(updatedMauPhatHanh);
        updatedMauPhatHanh
            .mauPhatHanhCode(UPDATED_MAU_PHAT_HANH_CODE)
            .name(UPDATED_NAME)
            .tieuChiCode(UPDATED_TIEU_CHI_CODE)
            .nhomPhanLoaiCode(UPDATED_NHOM_PHAN_LOAI_CODE)
            .userName(UPDATED_USER_NAME)
            .createTime(UPDATED_CREATE_TIME)
            .updateTime(UPDATED_UPDATE_TIME)
            .status(UPDATED_STATUS)
            .program(UPDATED_PROGRAM);

        restMauPhatHanhMockMvc.perform(put("/api/mau-phat-hanhs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedMauPhatHanh)))
            .andExpect(status().isOk());

        // Validate the MauPhatHanh in the database
        List<MauPhatHanh> mauPhatHanhList = mauPhatHanhRepository.findAll();
        assertThat(mauPhatHanhList).hasSize(databaseSizeBeforeUpdate);
        MauPhatHanh testMauPhatHanh = mauPhatHanhList.get(mauPhatHanhList.size() - 1);
        assertThat(testMauPhatHanh.getMauPhatHanhCode()).isEqualTo(UPDATED_MAU_PHAT_HANH_CODE);
        assertThat(testMauPhatHanh.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testMauPhatHanh.getTieuChiCode()).isEqualTo(UPDATED_TIEU_CHI_CODE);
        assertThat(testMauPhatHanh.getNhomPhanLoaiCode()).isEqualTo(UPDATED_NHOM_PHAN_LOAI_CODE);
        assertThat(testMauPhatHanh.getUserName()).isEqualTo(UPDATED_USER_NAME);
        assertThat(testMauPhatHanh.getCreateTime()).isEqualTo(UPDATED_CREATE_TIME);
        assertThat(testMauPhatHanh.getUpdateTime()).isEqualTo(UPDATED_UPDATE_TIME);
        assertThat(testMauPhatHanh.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testMauPhatHanh.getProgram()).isEqualTo(UPDATED_PROGRAM);
    }

    @Test
    @Transactional
    public void updateNonExistingMauPhatHanh() throws Exception {
        int databaseSizeBeforeUpdate = mauPhatHanhRepository.findAll().size();

        // Create the MauPhatHanh

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMauPhatHanhMockMvc.perform(put("/api/mau-phat-hanhs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mauPhatHanh)))
            .andExpect(status().isBadRequest());

        // Validate the MauPhatHanh in the database
        List<MauPhatHanh> mauPhatHanhList = mauPhatHanhRepository.findAll();
        assertThat(mauPhatHanhList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteMauPhatHanh() throws Exception {
        // Initialize the database
        mauPhatHanhService.save(mauPhatHanh);

        int databaseSizeBeforeDelete = mauPhatHanhRepository.findAll().size();

        // Delete the mauPhatHanh
        restMauPhatHanhMockMvc.perform(delete("/api/mau-phat-hanhs/{id}", mauPhatHanh.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<MauPhatHanh> mauPhatHanhList = mauPhatHanhRepository.findAll();
        assertThat(mauPhatHanhList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MauPhatHanh.class);
        MauPhatHanh mauPhatHanh1 = new MauPhatHanh();
        mauPhatHanh1.setId(1L);
        MauPhatHanh mauPhatHanh2 = new MauPhatHanh();
        mauPhatHanh2.setId(mauPhatHanh1.getId());
        assertThat(mauPhatHanh1).isEqualTo(mauPhatHanh2);
        mauPhatHanh2.setId(2L);
        assertThat(mauPhatHanh1).isNotEqualTo(mauPhatHanh2);
        mauPhatHanh1.setId(null);
        assertThat(mauPhatHanh1).isNotEqualTo(mauPhatHanh2);
    }
}
