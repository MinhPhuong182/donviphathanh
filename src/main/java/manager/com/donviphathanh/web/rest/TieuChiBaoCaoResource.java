package manager.com.donviphathanh.web.rest;
import manager.com.donviphathanh.domain.TieuChiBaoCao;
import manager.com.donviphathanh.service.TieuChiBaoCaoService;
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
 * REST controller for managing TieuChiBaoCao.
 */
@RestController
@RequestMapping("/api")
public class TieuChiBaoCaoResource {

    private final Logger log = LoggerFactory.getLogger(TieuChiBaoCaoResource.class);

    private static final String ENTITY_NAME = "donviphathanhTieuChiBaoCao";

    private final TieuChiBaoCaoService tieuChiBaoCaoService;

    public TieuChiBaoCaoResource(TieuChiBaoCaoService tieuChiBaoCaoService) {
        this.tieuChiBaoCaoService = tieuChiBaoCaoService;
    }

    /**
     * POST  /tieu-chi-bao-caos : Create a new tieuChiBaoCao.
     *
     * @param tieuChiBaoCao the tieuChiBaoCao to create
     * @return the ResponseEntity with status 201 (Created) and with body the new tieuChiBaoCao, or with status 400 (Bad Request) if the tieuChiBaoCao has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/tieu-chi-bao-caos")
    public ResponseEntity<TieuChiBaoCao> createTieuChiBaoCao(@Valid @RequestBody TieuChiBaoCao tieuChiBaoCao) throws URISyntaxException {
        log.debug("REST request to save TieuChiBaoCao : {}", tieuChiBaoCao);
        if (tieuChiBaoCao.getId() != null) {
            throw new BadRequestAlertException("A new tieuChiBaoCao cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TieuChiBaoCao result = tieuChiBaoCaoService.save(tieuChiBaoCao);
        return ResponseEntity.created(new URI("/api/tieu-chi-bao-caos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /tieu-chi-bao-caos : Updates an existing tieuChiBaoCao.
     *
     * @param tieuChiBaoCao the tieuChiBaoCao to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated tieuChiBaoCao,
     * or with status 400 (Bad Request) if the tieuChiBaoCao is not valid,
     * or with status 500 (Internal Server Error) if the tieuChiBaoCao couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/tieu-chi-bao-caos")
    public ResponseEntity<TieuChiBaoCao> updateTieuChiBaoCao(@Valid @RequestBody TieuChiBaoCao tieuChiBaoCao) throws URISyntaxException {
        log.debug("REST request to update TieuChiBaoCao : {}", tieuChiBaoCao);
        if (tieuChiBaoCao.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TieuChiBaoCao result = tieuChiBaoCaoService.save(tieuChiBaoCao);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, tieuChiBaoCao.getId().toString()))
            .body(result);
    }

    /**
     * GET  /tieu-chi-bao-caos : get all the tieuChiBaoCaos.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of tieuChiBaoCaos in body
     */
    @GetMapping("/tieu-chi-bao-caos")
    public ResponseEntity<List<TieuChiBaoCao>> getAllTieuChiBaoCaos(Pageable pageable) {
        log.debug("REST request to get a page of TieuChiBaoCaos");
        Page<TieuChiBaoCao> page = tieuChiBaoCaoService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/tieu-chi-bao-caos");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /tieu-chi-bao-caos/:id : get the "id" tieuChiBaoCao.
     *
     * @param id the id of the tieuChiBaoCao to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the tieuChiBaoCao, or with status 404 (Not Found)
     */
    @GetMapping("/tieu-chi-bao-caos/{id}")
    public ResponseEntity<TieuChiBaoCao> getTieuChiBaoCao(@PathVariable Long id) {
        log.debug("REST request to get TieuChiBaoCao : {}", id);
        Optional<TieuChiBaoCao> tieuChiBaoCao = tieuChiBaoCaoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(tieuChiBaoCao);
    }

    /**
     * DELETE  /tieu-chi-bao-caos/:id : delete the "id" tieuChiBaoCao.
     *
     * @param id the id of the tieuChiBaoCao to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/tieu-chi-bao-caos/{id}")
    public ResponseEntity<Void> deleteTieuChiBaoCao(@PathVariable Long id) {
        log.debug("REST request to delete TieuChiBaoCao : {}", id);
        tieuChiBaoCaoService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
