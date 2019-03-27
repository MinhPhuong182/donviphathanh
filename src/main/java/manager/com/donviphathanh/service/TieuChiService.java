package manager.com.donviphathanh.service;

import manager.com.donviphathanh.domain.TieuChi;
import manager.com.donviphathanh.repository.TieuChiRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing TieuChi.
 */
@Service
@Transactional
public class TieuChiService {

    private final Logger log = LoggerFactory.getLogger(TieuChiService.class);

    private final TieuChiRepository tieuChiRepository;

    public TieuChiService(TieuChiRepository tieuChiRepository) {
        this.tieuChiRepository = tieuChiRepository;
    }

    /**
     * Save a tieuChi.
     *
     * @param tieuChi the entity to save
     * @return the persisted entity
     */
    public TieuChi save(TieuChi tieuChi) {
        log.debug("Request to save TieuChi : {}", tieuChi);
        return tieuChiRepository.save(tieuChi);
    }

    /**
     * Get all the tieuChis.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<TieuChi> findAll(Pageable pageable) {
        log.debug("Request to get all TieuChis");
        return tieuChiRepository.findAll(pageable);
    }


    /**
     * Get one tieuChi by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<TieuChi> findOne(Long id) {
        log.debug("Request to get TieuChi : {}", id);
        return tieuChiRepository.findById(id);
    }

    /**
     * Delete the tieuChi by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete TieuChi : {}", id);
        tieuChiRepository.deleteById(id);
    }
}
