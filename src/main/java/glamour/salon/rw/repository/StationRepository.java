package glamour.salon.rw.repository;

import glamour.salon.rw.domain.Station;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface StationRepository extends JpaRepository<Station, UUID> {

    Optional<Station> findByStationNumber(int stationNumber);

    // C2 - free stations (no stylist assigned)
    @Query("SELECT s FROM Station s WHERE NOT EXISTS (SELECT p FROM Stylist p WHERE p.station = s) ORDER BY s.stationNumber ASC")
    List<Station> findFreeStations();
}
