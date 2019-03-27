package manager.com.donviphathanh.web.rest;

import manager.com.donviphathanh.DonviphathanhApp;

import manager.com.donviphathanh.domain.MauPhatHanhTieuChi;
import manager.com.donviphathanh.repository.MauPhatHanhTieuChiRepository;
import manager.com.donviphathanh.service.MauPhatHanhTieuChiService;
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
import java.util.List;


import static manager.com.donviphathanh.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the MauPhatHanhTieuChiResource REST controller.
 *
 * @see MauPhatHanhTieuChiResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = DonviphathanhApp.class)
public class MauPhatHanhTieuChiResourceIntTest {

    private static final String DEFAULT_MAU_PHAT_HANH_CODE = "AAAAAAAAAA";
    private static final String UPDATED_MAU_PHAT_HANH_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_TIEU_CHI_CODE = "AAAAAAAAAA";
    private static final String UPDATED_TIEU_CHI_CODE = "BBBBBBBBBB";

    @Autowired
    private MauPhatHanhTieuChiRepository mauPhatHanhTieuChiRepository;

    @Autowired
    private MauPhatHanhTieuChiService mauPhatHanhTieuChiService;

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

    private MockMvc restMauPhatHanhTieuChiMockMvc;

    private MauPhatHanhTieuChi mauPhatHanhTieuChi;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final MauPhatHanhTieuChiResource mauPhatHanhTieuChiResource = new MauPhatHanhTieuChiResource(mauPhatHanhTieuChiService);
        this.restMauPhatHanhTieuChiMockMvc = MockMvcBuilders.standaloneSetup(mauPhatHanhTieuChiResource)
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
    public static MauPhatHanhTieuChi createEntity(EntityManager em) {
        MauPhatHanhTieuChi mauPhatHanhTieuChi = new MauPhatHanhTieuChi()
            .mauPhatHanhCode(DEFAULT_MAU_PHAT_HANH_CODE)
            .tieuChiCode(DEFAULT_TIEU_CHI_CODE);
        return mauPhatHanhTieuChi;
    }

    @Before
    public void initTest() {
        mauPhatHanhTieuChi = createEntity(em);
    }

    @Test
    @Transactional
    public void createMauPhatHanhTieuChi() throws Exception {
        int databaseSizeBeforeCreate = mauPhatHanhTieuChiRepository.findAll().size();

        // Create the MauPhatHanhTieuChi
        restMauPhatHanhTieuChiMockMvc.perform(post("/api/mau-phat-hanh-tieu-chis")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mauPhatHanhTieuChi)))
            .andExpect(status().isCreated());

        // Validate the MauPhatHanhTieuChi in the database
        List<MauPhatHanhTieuChi> mauPhatHanhTieuChiList = mauPhatHanhTieuChiRepository.findAll();
        assertThat(mauPhatHanhTieuChiList).hasSize(databaseSizeBeforeCreate + 1);
        MauPhatHanhTieuChi testMauPhatHanhTieuChi = mauPhatHanhTieuChiList.get(mauPhatHanhTieuChiList.size() - 1);
        assertThat(testMauPhatHanhTieuChi.getMauPhatHanhCode()).isEqualTo(DEFAULT_MAU_PHAT_HANH_CODE);
        assertThat(testMauPhatHanhTieuChi.getTieuChiCode()).isEqualTo(DEFAULT_TIEU_CHI_CODE);
    }

    @Test
    @Transactional
    public void createMauPhatHanhTieuChiWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = mauPhatHanhTieuChiRepository.findAll().size();

        // Create the MauPhatHanhTieuChi with an existing ID
        mauPhatHanhTieuChi.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMauPhatHanhTieuChiMockMvc.perform(post("/api/mau-phat-hanh-tieu-chis")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mauPhatHanhTieuChi)))
            .andExpect(status().isBadRequest());

        // Validate the MauPhatHanhTieuChi in the database
        List<MauPhatHanhTieuChi> mauPhatHanhTieuChiList = mauPhatHanhTieuChiRepository.findAll();
        assertThat(mauPhatHanhTieuChiList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkMauPhatHanhCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = mauPhatHanhTieuChiRepository.findAll().size();
        // set the field null
        mauPhatHanhTieuChi.setMauPhatHanhCode(null);

        // Create the MauPhatHanhTieuChi, which fails.

        restMauPhatHanhTieuChiMockMvc.perform(post("/api/mau-phat-hanh-tieu-chis")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mauPhatHanhTieuChi)))
            .andExpect(status().isBadRequest());

        List<MauPhatHanhTieuChi> mauPhatHanhTieuChiList = mauPhatHanhTieuChiRepository.findAll();
        assertThat(mauPhatHanhTieuChiList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTieuChiCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = mauPhatHanhTieuChiRepository.findAll().size();
        // set the field null
        mauPhatHanhTieuChi.setTieuChiCode(null);

        // Create the MauPhatHanhTieuChi, which fails.

        restMauPhatHanhTieuChiMockMvc.perform(post("/api/mau-phat-hanh-tieu-chis")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mauPhatHanhTieuChi)))
            .andExpect(status().isBadRequest());

        List<MauPhatHanhTieuChi> mauPhatHanhTieuChiList = mauPhatHanhTieuChiRepository.findAll();
        assertThat(mauPhatHanhTieuChiList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllMauPhatHanhTieuChis() throws Exception {
        // Initialize the database
        mauPhatHanhTieuChiRepository.saveAndFlush(mauPhatHanhTieuChi);

        // Get all the mauPhatHanhTieuChiList
        restMauPhatHanhTieuChiMockMvc.perform(get("/api/mau-phat-hanh-tieu-chis?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mauPhatHanhTieuChi.getId().intValue())))
            .andExpect(jsonPath("$.[*].mauPhatHanhCode").value(hasItem(DEFAULT_MAU_PHAT_HANH_CODE.toString())))
            .andExpect(jsonPath("$.[*].tieuChiCode").value(hasItem(DEFAULT_TIEU_CHI_CODE.toString())));
    }
    
    @Test
    @Transactional
    public void getMauPhatHanhTieuChi() throws Exception {
        // Initialize the database
        mauPhatHanhTieuChiRepository.saveAndFlush(mauPhatHanhTieuChi);

        // Get the mauPhatHanhTieuChi
        restMauPhatHanhTieuChiMockMvc.perform(get("/api/mau-phat-hanh-tieu-chis/{id}", mauPhatHanhTieuChi.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(mauPhatHanhTieuChi.getId().intValue()))
            .andExpect(jsonPath("$.mauPhatHanhCode").value(DEFAULT_MAU_PHAT_HANH_CODE.toString()))
            .andExpect(jsonPath("$.tieuChiCode").value(DEFAULT_TIEU_CHI_CODE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingMauPhatHanhTieuChi() throws Exception {
        // Get the mauPhatHanhTieuChi
        restMauPhatHanhTieuChiMockMvc.perform(get("/api/mau-phat-hanh-tieu-chis/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMauPhatHanhTieuChi() throws Exception {
        // Initialize the database
        mauPhatHanhTieuChiService.save(mauPhatHanhTieuChi);

        int databaseSizeBeforeUpdate = mauPhatHanhTieuChiRepository.findAll().size();

        // Update the mauPhatHanhTieuChi
        MauPhatHanhTieuChi updatedMauPhatHanhTieuChi = mauPhatHanhTieuChiRepository.findById(mauPhatHanhTieuChi.getId()).get();
        // Disconnect from session so that the updates on updatedMauPhatHanhTieuChi are not directly saved in db
        em.detach(updatedMauPhatHanhTieuChi);
        updatedMauPhatHanhTieuChi
            .mauPhatHanhCode(UPDATED_MAU_PHAT_HANH_CODE)
            .tieuChiCode(UPDATED_TIEU_CHI_CODE);

        restMauPhatHanhTieuChiMockMvc.perform(put("/api/mau-phat-hanh-tieu-chis")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedMauPhatHanhTieuChi)))
            .andExpect(status().isOk());

        // Validate the MauPhatHanhTieuChi in the database
        List<MauPhatHanhTieuChi> mauPhatHanhTieuChiList = mauPhatHanhTieuChiRepository.findAll();
        assertThat(mauPhatHanhTieuChiList).hasSize(databaseSizeBeforeUpdate);
        MauPhatHanhTieuChi testMauPhatHanhTieuChi = mauPhatHanhTieuChiList.get(mauPhatHanhTieuChiList.size() - 1);
        assertThat(testMauPhatHanhTieuChi.getMauPhatHanhCode()).isEqualTo(UPDATED_MAU_PHAT_HANH_CODE);
        assertThat(testMauPhatHanhTieuChi.getTieuChiCode()).isEqualTo(UPDATED_TIEU_CHI_CODE);
    }

    @Test
    @Transactional
    public void updateNonExistingMauPhatHanhTieuChi() throws Exception {
        int databaseSizeBeforeUpdate = mauPhatHanhTieuChiRepository.findAll().size();

        // Create the MauPhatHanhTieuChi

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMauPhatHanhTieuChiMockMvc.perform(put("/api/mau-phat-hanh-tieu-chis")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mauPhatHanhTieuChi)))
            .andExpect(status().isBadRequest());

        // Validate the MauPhatHanhTieuChi in the database
        List<MauPhatHanhTieuChi> mauPhatHanhTieuChiList = mauPhatHanhTieuChiRepository.findAll();
        assertThat(mauPhatHanhTieuChiList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteMauPhatHanhTieuChi() throws Exception {
        // Initialize the database
        mauPhatHanhTieuChiService.save(mauPhatHanhTieuChi);

        int databaseSizeBeforeDelete = mauPhatHanhTieuChiRepository.findAll().size();

        // Delete the mauPhatHanhTieuChi
        restMauPhatHanhTieuChiMockMvc.perform(delete("/api/mau-phat-hanh-tieu-chis/{id}", mauPhatHanhTieuChi.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<MauPhatHanhTieuChi> mauPhatHanhTieuChiList = mauPhatHanhTieuChiRepository.findAll();
        assertThat(mauPhatHanhTieuChiList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MauPhatHanhTieuChi.class);
        MauPhatHanhTieuChi mauPhatHanhTieuChi1 = new MauPhatHanhTieuChi();
        mauPhatHanhTieuChi1.setId(1L);
        MauPhatHanhTieuChi mauPhatHanhTieuChi2 = new MauPhatHanhTieuChi();
        mauPhatHanhTieuChi2.setId(mauPhatHanhTieuChi1.getId());
        assertThat(mauPhatHanhTieuChi1).isEqualTo(mauPhatHanhTieuChi2);
        mauPhatHanhTieuChi2.setId(2L);
        assertThat(mauPhatHanhTieuChi1).isNotEqualTo(mauPhatHanhTieuChi2);
        mauPhatHanhTieuChi1.setId(null);
        assertThat(mauPhatHanhTieuChi1).isNotEqualTo(mauPhatHanhTieuChi2);
    }
}
