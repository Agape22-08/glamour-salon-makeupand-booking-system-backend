package glamour.salon.rw.repository;

import glamour.salon.rw.domain.BeautyService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.UUID;

@Repository
public interface BeautyServiceRepository extends JpaRepository<BeautyService, UUID> {

    // C3 - beauty services nobody holds (JPQL IS EMPTY)
    @Query("SELECT b FROM BeautyService b WHERE b.stylists IS EMPTY")
    List<BeautyService> findUnusedBeautyServices();
}
