package glamour.salon.rw.repository;

import glamour.salon.rw.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.UUID;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, UUID> {

    // B1 - by lastName ignoring case, sorted by firstName
    List<Customer> findByLastNameIgnoreCaseOrderByFirstNameAsc(String lastName);

    // C4 - customers of a stylist (JPQL DISTINCT path navigation)
    @Query("SELECT DISTINCT b.customer FROM Booking b WHERE b.stylist.id = :stylistId")
    List<Customer> findCustomersOfStylist(@Param("stylistId") UUID stylistId);
}
