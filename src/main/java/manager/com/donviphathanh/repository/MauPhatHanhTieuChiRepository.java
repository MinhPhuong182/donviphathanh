package manager.com.donviphathanh.repository;

import manager.com.donviphathanh.domain.MauPhatHanhTieuChi;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the MauPhatHanhTieuChi entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MauPhatHanhTieuChiRepository extends JpaRepository<MauPhatHanhTieuChi, Long> {

}
