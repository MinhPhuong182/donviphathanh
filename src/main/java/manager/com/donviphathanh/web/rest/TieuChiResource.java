package manager.com.donviphathanh.web.rest;
import manager.com.donviphathanh.domain.TieuChi;
import manager.com.donviphathanh.service.TieuChiService;
import manager.com.donviphathanh.web.rest.errors.BadRequestAlertException;
import manager.com.donviphathanh.web.rest.util.HeaderUtil;
import manager.com.donviphathanh.web.rest.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing TieuChi.
 */
@RestController
@RequestMapping("/api")
public class TieuChiResource {

    private final Logger log = LoggerFactory.getLogger(TieuChiResource.class);

    private static final String ENTITY_NAME = "donviphathanhTieuChi";

    private final TieuChiService tieuChiService;

    public TieuChiResource(TieuChiService tieuChiService) {
        this.tieuChiService = tieuChiService;
    }

    /**
     * POST  /tieu-chis : Create a new tieuChi.
     *
     * @param tieuChi the tieuChi to create
     * @return the ResponseEntity with status 201 (Created) and with body the new tieuChi, or with status 400 (Bad Request) if the tieuChi has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/tieu-chis")
    public ResponseEntity<TieuChi> createTieuChi(@Valid @RequestBody TieuChi tieuChi) throws URISyntaxException {
        log.debug("REST request to save TieuChi : {}", tieuChi);
        if (tieuChi.getId() != null) {
            throw new BadRequestAlertException("A new tieuChi cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TieuChi result = tieuChiService.save(tieuChi);
        return ResponseEntity.created(new URI("/api/tieu-chis/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /tieu-chis : Updates an existing tieuChi.
     *
     * @param tieuChi the tieuChi to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated tieuChi,
     * or with status 400 (Bad Request) if the tieuChi is not valid,
     * or with status 500 (Internal Server Error) if the tieuChi couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/tieu-chis")
    public ResponseEntity<TieuChi> updateTieuChi(@Valid @RequestBody TieuChi tieuChi) throws URISyntaxException {
        log.debug("REST request to update TieuChi : {}", tieuChi);
        if (tieuChi.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TieuChi result = tieuChiService.save(tieuChi);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, tieuChi.getId().toString()))
            .body(result);
    }

    /**
     * GET  /tieu-chis : get all the tieuChis.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of tieuChis in body
     */
    @GetMapping("/tieu-chis")
    public ResponseEntity<List<TieuChi>> getAllTieuChis(Pageable pageable) {
        log.debug("REST request to get a page of TieuChis");
        Page<TieuChi> page = tieuChiService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/tieu-chis");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /tieu-chis/:id : get the "id" tieuChi.
     *
     * @param id the id of the tieuChi to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the tieuChi, or with status 404 (Not Found)
     */
    @GetMapping("/tieu-chis/{id}")
    public ResponseEntity<TieuChi> getTieuChi(@PathVariable Long id) {
        log.debug("REST request to get TieuChi : {}", id);
        Optional<TieuChi> tieuChi = tieuChiService.findOne(id);
        return ResponseUtil.wrapOrNotFound(tieuChi);
    }

    /**
     * DELETE  /tieu-chis/:id : delete the "id" tieuChi.
     *
     * @param id the id of the tieuChi to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/tieu-chis/{id}")
    public ResponseEntity<Void> deleteTieuChi(@PathVariable Long id) {
        log.debug("REST request to delete TieuChi : {}", id);
        tieuChiService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
