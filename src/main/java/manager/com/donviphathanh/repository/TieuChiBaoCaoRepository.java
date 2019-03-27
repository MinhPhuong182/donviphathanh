package manager.com.donviphathanh.repository;

import manager.com.donviphathanh.domain.TieuChiBaoCao;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the TieuChiBaoCao entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TieuChiBaoCaoRepository extends JpaRepository<TieuChiBaoCao, Long> {

}
