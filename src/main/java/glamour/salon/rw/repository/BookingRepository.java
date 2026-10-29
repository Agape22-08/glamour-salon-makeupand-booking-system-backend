package glamour.salon.rw.repository;

import glamour.salon.rw.domain.Booking;
import glamour.salon.rw.domain.BookingStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Repository
public interface BookingRepository extends JpaRepository<Booking, UUID> {

    // B2 - by status ordered by bookingDate
    List<Booking> findByStatusOrderByBookingDateAsc(BookingStatus status);

    // B3 - between two dates inclusive
    List<Booking> findByBookingDateBetweenOrderByBookingDateAsc(LocalDate start, LocalDate end);

    // D1 - check if stylist already has a booking on that date (not NO_SHOW)
    boolean existsByStylistIdAndBookingDateAndStatusNot(UUID stylistId, LocalDate bookingDate, BookingStatus status);

    // F1 - count by status (JPQL GROUP BY)
    @Query("SELECT b.status, COUNT(b) FROM Booking b GROUP BY b.status")
    List<Object[]> countByStatus();

    // F2 - cancel day (JPQL UPDATE @Modifying)
    @Modifying
    @Transactional
    @Query("UPDATE Booking b SET b.status = glamour.salon.rw.domain.BookingStatus.NO_SHOW WHERE b.stylist.id = :stylistId AND b.bookingDate = :date AND b.status <> glamour.salon.rw.domain.BookingStatus.SERVED")
    int cancelDayForStylist(@Param("stylistId") UUID stylistId, @Param("date") LocalDate date);

    // Bonus pagination
    Page<Booking> findAll(Pageable pageable);

    // Bonus dashboard
    long countByBookingDate(LocalDate date);
    long countByStatus(BookingStatus status);
}
