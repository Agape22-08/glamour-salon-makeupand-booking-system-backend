package glamour.salon.rw.service;

import glamour.salon.rw.domain.Booking;
import glamour.salon.rw.domain.BookingStatus;
import glamour.salon.rw.repository.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class BookingService {

    @Autowired
    private BookingRepository bookingRepo;

    // D1 - safe save
    public String save(Booking booking) {
        if (booking.getBookingDate().isBefore(LocalDate.now())) return "Booking date cannot be in the past";
        boolean conflict = bookingRepo.existsByStylistIdAndBookingDateAndStatusNot(
            booking.getStylist().getId(), booking.getBookingDate(), BookingStatus.NO_SHOW);
        if (conflict) return "Stylist is already booked on that date";
        booking.setStatus(BookingStatus.BOOKED);
        bookingRepo.save(booking);
        return "Booking saved successfully";
    }

    public List<Booking> getAll() {
        return bookingRepo.findAll();
    }

    public Optional<Booking> getById(UUID id) {
        return bookingRepo.findById(id);
    }

    public String update(UUID id, Booking details) {
        Optional<Booking> opt = bookingRepo.findById(id);
        if (opt.isEmpty()) return "The booking with that id does not exist";
        Booking b = opt.get();
        b.setBookingDate(details.getBookingDate());
        b.setServiceName(details.getServiceName());
        b.setDurationMinutes(details.getDurationMinutes());
        b.setCustomer(details.getCustomer());
        b.setStylist(details.getStylist());
        // status is NOT changed here - only via PATCH /{id}/status
        bookingRepo.save(b);
        return "Booking updated successfully";
    }

    public String delete(UUID id) {
        if (!bookingRepo.existsById(id)) return "The booking with that id does not exist";
        bookingRepo.deleteById(id);
        return "Booking deleted successfully";
    }

    // B2
    public List<Booking> getByStatus(BookingStatus status) {
        return bookingRepo.findByStatusOrderByBookingDateAsc(status);
    }

    // B3
    public List<Booking> getBetween(LocalDate start, LocalDate end) {
        return bookingRepo.findByBookingDateBetweenOrderByBookingDateAsc(start, end);
    }

    // D2 - status flow
    public String updateStatus(UUID id, BookingStatus newStatus) {
        Optional<Booking> opt = bookingRepo.findById(id);
        if (opt.isEmpty()) return "The booking with that id does not exist";
        Booking b = opt.get();
        BookingStatus current = b.getStatus();
        boolean allowed = false;
        if (current == BookingStatus.BOOKED && (newStatus == BookingStatus.IN_PROGRESS || newStatus == BookingStatus.NO_SHOW)) allowed = true;
        if (current == BookingStatus.IN_PROGRESS && (newStatus == BookingStatus.SERVED || newStatus == BookingStatus.NO_SHOW)) allowed = true;
        if (!allowed) return "Cannot change status from " + current + " to " + newStatus;
        b.setStatus(newStatus);
        bookingRepo.save(b);
        return "Booking status updated successfully";
    }

    // F1
    public List<Object[]> getStatsByStatus() {
        return bookingRepo.countByStatus();
    }

    // F2
    @Transactional
    public int cancelDay(UUID stylistId, LocalDate date) {
        return bookingRepo.cancelDayForStylist(stylistId, date);
    }

    // Bonus pagination
    public Page<Booking> getPage(Pageable pageable) {
        return bookingRepo.findAll(pageable);
    }
}
