package manager.com.donviphathanh.repository;

import manager.com.donviphathanh.domain.MauPhatHanh;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the MauPhatHanh entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MauPhatHanhRepository extends JpaRepository<MauPhatHanh, Long> {

}
