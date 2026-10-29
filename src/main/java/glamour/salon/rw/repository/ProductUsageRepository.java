package glamour.salon.rw.repository;

import glamour.salon.rw.domain.Booking;
import glamour.salon.rw.domain.ProductUsage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.UUID;

@Repository
public interface ProductUsageRepository extends JpaRepository<ProductUsage, UUID> {

    // E2 - history by customer phone (JPQL path navigation, ORDER BY)
    @Query("SELECT p FROM ProductUsage p WHERE p.booking.customer.phone = :phone ORDER BY p.usedDate DESC")
    List<ProductUsage> findByCustomerPhone(@Param("phone") String phone);

    // E3 - protect booking deletion
    boolean existsByBooking(Booking booking);
}
