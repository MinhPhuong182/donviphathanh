package manager.com.donviphathanh.web.rest;
import manager.com.donviphathanh.domain.MauPhatHanhTieuChi;
import manager.com.donviphathanh.service.MauPhatHanhTieuChiService;
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
 * REST controller for managing MauPhatHanhTieuChi.
 */
@RestController
@RequestMapping("/api")
public class MauPhatHanhTieuChiResource {

    private final Logger log = LoggerFactory.getLogger(MauPhatHanhTieuChiResource.class);

    private static final String ENTITY_NAME = "donviphathanhMauPhatHanhTieuChi";

    private final MauPhatHanhTieuChiService mauPhatHanhTieuChiService;

    public MauPhatHanhTieuChiResource(MauPhatHanhTieuChiService mauPhatHanhTieuChiService) {
        this.mauPhatHanhTieuChiService = mauPhatHanhTieuChiService;
    }

    /**
     * POST  /mau-phat-hanh-tieu-chis : Create a new mauPhatHanhTieuChi.
     *
     * @param mauPhatHanhTieuChi the mauPhatHanhTieuChi to create
     * @return the ResponseEntity with status 201 (Created) and with body the new mauPhatHanhTieuChi, or with status 400 (Bad Request) if the mauPhatHanhTieuChi has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/mau-phat-hanh-tieu-chis")
    public ResponseEntity<MauPhatHanhTieuChi> createMauPhatHanhTieuChi(@Valid @RequestBody MauPhatHanhTieuChi mauPhatHanhTieuChi) throws URISyntaxException {
        log.debug("REST request to save MauPhatHanhTieuChi : {}", mauPhatHanhTieuChi);
        if (mauPhatHanhTieuChi.getId() != null) {
            throw new BadRequestAlertException("A new mauPhatHanhTieuChi cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MauPhatHanhTieuChi result = mauPhatHanhTieuChiService.save(mauPhatHanhTieuChi);
        return ResponseEntity.created(new URI("/api/mau-phat-hanh-tieu-chis/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /mau-phat-hanh-tieu-chis : Updates an existing mauPhatHanhTieuChi.
     *
     * @param mauPhatHanhTieuChi the mauPhatHanhTieuChi to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated mauPhatHanhTieuChi,
     * or with status 400 (Bad Request) if the mauPhatHanhTieuChi is not valid,
     * or with status 500 (Internal Server Error) if the mauPhatHanhTieuChi couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/mau-phat-hanh-tieu-chis")
    public ResponseEntity<MauPhatHanhTieuChi> updateMauPhatHanhTieuChi(@Valid @RequestBody MauPhatHanhTieuChi mauPhatHanhTieuChi) throws URISyntaxException {
        log.debug("REST request to update MauPhatHanhTieuChi : {}", mauPhatHanhTieuChi);
        if (mauPhatHanhTieuChi.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        MauPhatHanhTieuChi result = mauPhatHanhTieuChiService.save(mauPhatHanhTieuChi);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, mauPhatHanhTieuChi.getId().toString()))
            .body(result);
    }

    /**
     * GET  /mau-phat-hanh-tieu-chis : get all the mauPhatHanhTieuChis.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of mauPhatHanhTieuChis in body
     */
    @GetMapping("/mau-phat-hanh-tieu-chis")
    public ResponseEntity<List<MauPhatHanhTieuChi>> getAllMauPhatHanhTieuChis(Pageable pageable) {
        log.debug("REST request to get a page of MauPhatHanhTieuChis");
        Page<MauPhatHanhTieuChi> page = mauPhatHanhTieuChiService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/mau-phat-hanh-tieu-chis");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /mau-phat-hanh-tieu-chis/:id : get the "id" mauPhatHanhTieuChi.
     *
     * @param id the id of the mauPhatHanhTieuChi to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the mauPhatHanhTieuChi, or with status 404 (Not Found)
     */
    @GetMapping("/mau-phat-hanh-tieu-chis/{id}")
    public ResponseEntity<MauPhatHanhTieuChi> getMauPhatHanhTieuChi(@PathVariable Long id) {
        log.debug("REST request to get MauPhatHanhTieuChi : {}", id);
        Optional<MauPhatHanhTieuChi> mauPhatHanhTieuChi = mauPhatHanhTieuChiService.findOne(id);
        return ResponseUtil.wrapOrNotFound(mauPhatHanhTieuChi);
    }

    /**
     * DELETE  /mau-phat-hanh-tieu-chis/:id : delete the "id" mauPhatHanhTieuChi.
     *
     * @param id the id of the mauPhatHanhTieuChi to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/mau-phat-hanh-tieu-chis/{id}")
    public ResponseEntity<Void> deleteMauPhatHanhTieuChi(@PathVariable Long id) {
        log.debug("REST request to delete MauPhatHanhTieuChi : {}", id);
        mauPhatHanhTieuChiService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
