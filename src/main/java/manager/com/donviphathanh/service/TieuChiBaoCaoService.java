package manager.com.donviphathanh.service;

import manager.com.donviphathanh.domain.TieuChiBaoCao;
import manager.com.donviphathanh.repository.TieuChiBaoCaoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing TieuChiBaoCao.
 */
@Service
@Transactional
public class TieuChiBaoCaoService {

    private final Logger log = LoggerFactory.getLogger(TieuChiBaoCaoService.class);

    private final TieuChiBaoCaoRepository tieuChiBaoCaoRepository;

    public TieuChiBaoCaoService(TieuChiBaoCaoRepository tieuChiBaoCaoRepository) {
        this.tieuChiBaoCaoRepository = tieuChiBaoCaoRepository;
    }

    /**
     * Save a tieuChiBaoCao.
     *
     * @param tieuChiBaoCao the entity to save
     * @return the persisted entity
     */
    public TieuChiBaoCao save(TieuChiBaoCao tieuChiBaoCao) {
        log.debug("Request to save TieuChiBaoCao : {}", tieuChiBaoCao);
        return tieuChiBaoCaoRepository.save(tieuChiBaoCao);
    }

    /**
     * Get all the tieuChiBaoCaos.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<TieuChiBaoCao> findAll(Pageable pageable) {
        log.debug("Request to get all TieuChiBaoCaos");
        return tieuChiBaoCaoRepository.findAll(pageable);
    }


    /**
     * Get one tieuChiBaoCao by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<TieuChiBaoCao> findOne(Long id) {
        log.debug("Request to get TieuChiBaoCao : {}", id);
        return tieuChiBaoCaoRepository.findById(id);
    }

    /**
     * Delete the tieuChiBaoCao by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete TieuChiBaoCao : {}", id);
        tieuChiBaoCaoRepository.deleteById(id);
    }
}
