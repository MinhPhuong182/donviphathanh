package manager.com.donviphathanh.web.rest;
import manager.com.donviphathanh.domain.KyCongBo;
import manager.com.donviphathanh.service.KyCongBoService;
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
 * REST controller for managing KyCongBo.
 */
@RestController
@RequestMapping("/api")
public class KyCongBoResource {

    private final Logger log = LoggerFactory.getLogger(KyCongBoResource.class);

    private static final String ENTITY_NAME = "donviphathanhKyCongBo";

    private final KyCongBoService kyCongBoService;

    public KyCongBoResource(KyCongBoService kyCongBoService) {
        this.kyCongBoService = kyCongBoService;
    }

    /**
     * POST  /ky-cong-bos : Create a new kyCongBo.
     *
     * @param kyCongBo the kyCongBo to create
     * @return the ResponseEntity with status 201 (Created) and with body the new kyCongBo, or with status 400 (Bad Request) if the kyCongBo has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/ky-cong-bos")
    public ResponseEntity<KyCongBo> createKyCongBo(@Valid @RequestBody KyCongBo kyCongBo) throws URISyntaxException {
        log.debug("REST request to save KyCongBo : {}", kyCongBo);
        if (kyCongBo.getId() != null) {
            throw new BadRequestAlertException("A new kyCongBo cannot already have an ID", ENTITY_NAME, "idexists");
        }
        KyCongBo result = kyCongBoService.save(kyCongBo);
        return ResponseEntity.created(new URI("/api/ky-cong-bos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /ky-cong-bos : Updates an existing kyCongBo.
     *
     * @param kyCongBo the kyCongBo to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated kyCongBo,
     * or with status 400 (Bad Request) if the kyCongBo is not valid,
     * or with status 500 (Internal Server Error) if the kyCongBo couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/ky-cong-bos")
    public ResponseEntity<KyCongBo> updateKyCongBo(@Valid @RequestBody KyCongBo kyCongBo) throws URISyntaxException {
        log.debug("REST request to update KyCongBo : {}", kyCongBo);
        if (kyCongBo.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        KyCongBo result = kyCongBoService.save(kyCongBo);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, kyCongBo.getId().toString()))
            .body(result);
    }

    /**
     * GET  /ky-cong-bos : get all the kyCongBos.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of kyCongBos in body
     */
    @GetMapping("/ky-cong-bos")
    public ResponseEntity<List<KyCongBo>> getAllKyCongBos(Pageable pageable) {
        log.debug("REST request to get a page of KyCongBos");
        Page<KyCongBo> page = kyCongBoService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/ky-cong-bos");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /ky-cong-bos/:id : get the "id" kyCongBo.
     *
     * @param id the id of the kyCongBo to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the kyCongBo, or with status 404 (Not Found)
     */
    @GetMapping("/ky-cong-bos/{id}")
    public ResponseEntity<KyCongBo> getKyCongBo(@PathVariable Long id) {
        log.debug("REST request to get KyCongBo : {}", id);
        Optional<KyCongBo> kyCongBo = kyCongBoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(kyCongBo);
    }

    /**
     * DELETE  /ky-cong-bos/:id : delete the "id" kyCongBo.
     *
     * @param id the id of the kyCongBo to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/ky-cong-bos/{id}")
    public ResponseEntity<Void> deleteKyCongBo(@PathVariable Long id) {
        log.debug("REST request to delete KyCongBo : {}", id);
        kyCongBoService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
