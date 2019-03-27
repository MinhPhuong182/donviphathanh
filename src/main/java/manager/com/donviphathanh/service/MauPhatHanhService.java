package manager.com.donviphathanh.service;

import manager.com.donviphathanh.domain.MauPhatHanh;
import manager.com.donviphathanh.repository.MauPhatHanhRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing MauPhatHanh.
 */
@Service
@Transactional
public class MauPhatHanhService {

    private final Logger log = LoggerFactory.getLogger(MauPhatHanhService.class);

    private final MauPhatHanhRepository mauPhatHanhRepository;

    public MauPhatHanhService(MauPhatHanhRepository mauPhatHanhRepository) {
        this.mauPhatHanhRepository = mauPhatHanhRepository;
    }

    /**
     * Save a mauPhatHanh.
     *
     * @param mauPhatHanh the entity to save
     * @return the persisted entity
     */
    public MauPhatHanh save(MauPhatHanh mauPhatHanh) {
        log.debug("Request to save MauPhatHanh : {}", mauPhatHanh);
        return mauPhatHanhRepository.save(mauPhatHanh);
    }

    /**
     * Get all the mauPhatHanhs.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<MauPhatHanh> findAll(Pageable pageable) {
        log.debug("Request to get all MauPhatHanhs");
        return mauPhatHanhRepository.findAll(pageable);
    }


    /**
     * Get one mauPhatHanh by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<MauPhatHanh> findOne(Long id) {
        log.debug("Request to get MauPhatHanh : {}", id);
        return mauPhatHanhRepository.findById(id);
    }

    /**
     * Delete the mauPhatHanh by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete MauPhatHanh : {}", id);
        mauPhatHanhRepository.deleteById(id);
    }
}
