package manager.com.donviphathanh.repository;

import manager.com.donviphathanh.domain.KyCongBo;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the KyCongBo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface KyCongBoRepository extends JpaRepository<KyCongBo, Long> {

}
