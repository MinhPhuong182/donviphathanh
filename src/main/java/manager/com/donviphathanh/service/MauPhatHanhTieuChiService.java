package manager.com.donviphathanh.service;

import manager.com.donviphathanh.domain.MauPhatHanhTieuChi;
import manager.com.donviphathanh.repository.MauPhatHanhTieuChiRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing MauPhatHanhTieuChi.
 */
@Service
@Transactional
public class MauPhatHanhTieuChiService {

    private final Logger log = LoggerFactory.getLogger(MauPhatHanhTieuChiService.class);

    private final MauPhatHanhTieuChiRepository mauPhatHanhTieuChiRepository;

    public MauPhatHanhTieuChiService(MauPhatHanhTieuChiRepository mauPhatHanhTieuChiRepository) {
        this.mauPhatHanhTieuChiRepository = mauPhatHanhTieuChiRepository;
    }

    /**
     * Save a mauPhatHanhTieuChi.
     *
     * @param mauPhatHanhTieuChi the entity to save
     * @return the persisted entity
     */
    public MauPhatHanhTieuChi save(MauPhatHanhTieuChi mauPhatHanhTieuChi) {
        log.debug("Request to save MauPhatHanhTieuChi : {}", mauPhatHanhTieuChi);
        return mauPhatHanhTieuChiRepository.save(mauPhatHanhTieuChi);
    }

    /**
     * Get all the mauPhatHanhTieuChis.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<MauPhatHanhTieuChi> findAll(Pageable pageable) {
        log.debug("Request to get all MauPhatHanhTieuChis");
        return mauPhatHanhTieuChiRepository.findAll(pageable);
    }


    /**
     * Get one mauPhatHanhTieuChi by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<MauPhatHanhTieuChi> findOne(Long id) {
        log.debug("Request to get MauPhatHanhTieuChi : {}", id);
        return mauPhatHanhTieuChiRepository.findById(id);
    }

    /**
     * Delete the mauPhatHanhTieuChi by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete MauPhatHanhTieuChi : {}", id);
        mauPhatHanhTieuChiRepository.deleteById(id);
    }
}
