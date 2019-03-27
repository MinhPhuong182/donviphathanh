package manager.com.donviphathanh.repository;

import manager.com.donviphathanh.domain.TieuChi;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the TieuChi entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TieuChiRepository extends JpaRepository<TieuChi, Long> {

}
