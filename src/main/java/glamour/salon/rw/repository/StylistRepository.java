package glamour.salon.rw.repository;

import glamour.salon.rw.domain.Station;
import glamour.salon.rw.domain.Stylist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface StylistRepository extends JpaRepository<Stylist, UUID> {

    // D4 - check if station is taken
    boolean existsByStation(Station station);

    Optional<Stylist> findByStation(Station station);

    // C1 - stylists by beauty service name (JPQL, JOIN on collection, LOWER)
    @Query("SELECT s FROM Stylist s JOIN s.beautyServices b WHERE LOWER(b.name) LIKE CONCAT('%', LOWER(:name), '%')")
    List<Stylist> findByBeautyServiceName(@Param("name") String name);
}
