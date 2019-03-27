package manager.com.donviphathanh.service;

import manager.com.donviphathanh.domain.KyCongBo;
import manager.com.donviphathanh.repository.KyCongBoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing KyCongBo.
 */
@Service
@Transactional
public class KyCongBoService {

    private final Logger log = LoggerFactory.getLogger(KyCongBoService.class);

    private final KyCongBoRepository kyCongBoRepository;

    public KyCongBoService(KyCongBoRepository kyCongBoRepository) {
        this.kyCongBoRepository = kyCongBoRepository;
    }

    /**
     * Save a kyCongBo.
     *
     * @param kyCongBo the entity to save
     * @return the persisted entity
     */
    public KyCongBo save(KyCongBo kyCongBo) {
        log.debug("Request to save KyCongBo : {}", kyCongBo);
        return kyCongBoRepository.save(kyCongBo);
    }

    /**
     * Get all the kyCongBos.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<KyCongBo> findAll(Pageable pageable) {
        log.debug("Request to get all KyCongBos");
        return kyCongBoRepository.findAll(pageable);
    }


    /**
     * Get one kyCongBo by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<KyCongBo> findOne(Long id) {
        log.debug("Request to get KyCongBo : {}", id);
        return kyCongBoRepository.findById(id);
    }

    /**
     * Delete the kyCongBo by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete KyCongBo : {}", id);
        kyCongBoRepository.deleteById(id);
    }
}
