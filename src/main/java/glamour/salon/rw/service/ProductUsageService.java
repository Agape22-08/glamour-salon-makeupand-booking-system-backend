package glamour.salon.rw.service;

import glamour.salon.rw.domain.Booking;
import glamour.salon.rw.domain.BookingStatus;
import glamour.salon.rw.domain.ProductUsage;
import glamour.salon.rw.repository.BookingRepository;
import glamour.salon.rw.repository.ProductUsageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ProductUsageService {

    @Autowired
    private ProductUsageRepository productUsageRepo;

    @Autowired
    private BookingRepository bookingRepo;

    // E1
    public String save(UUID bookingId, ProductUsage productUsage) {
        Optional<Booking> opt = bookingRepo.findById(bookingId);
        if (opt.isEmpty()) return "The booking with that id does not exist";
        Booking booking = opt.get();
        if (booking.getStatus() != BookingStatus.SERVED) return "Product usages are only for SERVED bookings";
        productUsage.setUsedDate(LocalDate.now());
        productUsage.setBooking(booking);
        productUsageRepo.save(productUsage);
        return "Product usage saved successfully";
    }

    public List<ProductUsage> getAll() {
        return productUsageRepo.findAll();
    }

    public Optional<ProductUsage> getById(UUID id) {
        return productUsageRepo.findById(id);
    }

    public String update(UUID id, ProductUsage details) {
        Optional<ProductUsage> opt = productUsageRepo.findById(id);
        if (opt.isEmpty()) return "The product usage with that id does not exist";
        ProductUsage p = opt.get();
        p.setProductName(details.getProductName());
        p.setNotes(details.getNotes());
        p.setQuantity(details.getQuantity());
        productUsageRepo.save(p);
        return "Product usage updated successfully";
    }

    // E3
    public String delete(UUID id) {
        if (!productUsageRepo.existsById(id)) return "The product usage with that id does not exist";
        productUsageRepo.deleteById(id);
        return "Product usage deleted successfully";
    }

    // E2
    public List<ProductUsage> getByCustomerPhone(String phone) {
        return productUsageRepo.findByCustomerPhone(phone);
    }

    public boolean existsByBooking(Booking booking) {
        return productUsageRepo.existsByBooking(booking);
    }
}
