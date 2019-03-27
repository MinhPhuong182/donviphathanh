package manager.com.donviphathanh.web.rest;
import manager.com.donviphathanh.domain.MauPhatHanh;
import manager.com.donviphathanh.service.MauPhatHanhService;
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
 * REST controller for managing MauPhatHanh.
 */
@RestController
@RequestMapping("/api")
public class MauPhatHanhResource {

    private final Logger log = LoggerFactory.getLogger(MauPhatHanhResource.class);

    private static final String ENTITY_NAME = "donviphathanhMauPhatHanh";

    private final MauPhatHanhService mauPhatHanhService;

    public MauPhatHanhResource(MauPhatHanhService mauPhatHanhService) {
        this.mauPhatHanhService = mauPhatHanhService;
    }

    /**
     * POST  /mau-phat-hanhs : Create a new mauPhatHanh.
     *
     * @param mauPhatHanh the mauPhatHanh to create
     * @return the ResponseEntity with status 201 (Created) and with body the new mauPhatHanh, or with status 400 (Bad Request) if the mauPhatHanh has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/mau-phat-hanhs")
    public ResponseEntity<MauPhatHanh> createMauPhatHanh(@Valid @RequestBody MauPhatHanh mauPhatHanh) throws URISyntaxException {
        log.debug("REST request to save MauPhatHanh : {}", mauPhatHanh);
        if (mauPhatHanh.getId() != null) {
            throw new BadRequestAlertException("A new mauPhatHanh cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MauPhatHanh result = mauPhatHanhService.save(mauPhatHanh);
        return ResponseEntity.created(new URI("/api/mau-phat-hanhs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /mau-phat-hanhs : Updates an existing mauPhatHanh.
     *
     * @param mauPhatHanh the mauPhatHanh to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated mauPhatHanh,
     * or with status 400 (Bad Request) if the mauPhatHanh is not valid,
     * or with status 500 (Internal Server Error) if the mauPhatHanh couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/mau-phat-hanhs")
    public ResponseEntity<MauPhatHanh> updateMauPhatHanh(@Valid @RequestBody MauPhatHanh mauPhatHanh) throws URISyntaxException {
        log.debug("REST request to update MauPhatHanh : {}", mauPhatHanh);
        if (mauPhatHanh.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        MauPhatHanh result = mauPhatHanhService.save(mauPhatHanh);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, mauPhatHanh.getId().toString()))
            .body(result);
    }

    /**
     * GET  /mau-phat-hanhs : get all the mauPhatHanhs.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of mauPhatHanhs in body
     */
    @GetMapping("/mau-phat-hanhs")
    public ResponseEntity<List<MauPhatHanh>> getAllMauPhatHanhs(Pageable pageable) {
        log.debug("REST request to get a page of MauPhatHanhs");
        Page<MauPhatHanh> page = mauPhatHanhService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/mau-phat-hanhs");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /mau-phat-hanhs/:id : get the "id" mauPhatHanh.
     *
     * @param id the id of the mauPhatHanh to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the mauPhatHanh, or with status 404 (Not Found)
     */
    @GetMapping("/mau-phat-hanhs/{id}")
    public ResponseEntity<MauPhatHanh> getMauPhatHanh(@PathVariable Long id) {
        log.debug("REST request to get MauPhatHanh : {}", id);
        Optional<MauPhatHanh> mauPhatHanh = mauPhatHanhService.findOne(id);
        return ResponseUtil.wrapOrNotFound(mauPhatHanh);
    }

    /**
     * DELETE  /mau-phat-hanhs/:id : delete the "id" mauPhatHanh.
     *
     * @param id the id of the mauPhatHanh to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/mau-phat-hanhs/{id}")
    public ResponseEntity<Void> deleteMauPhatHanh(@PathVariable Long id) {
        log.debug("REST request to delete MauPhatHanh : {}", id);
        mauPhatHanhService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
