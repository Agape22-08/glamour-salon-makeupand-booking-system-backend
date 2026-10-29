package glamour.salon.rw.service;

import glamour.salon.rw.domain.BookingStatus;
import glamour.salon.rw.repository.BookingRepository;
import glamour.salon.rw.repository.CustomerRepository;
import glamour.salon.rw.repository.ProductUsageRepository;
import glamour.salon.rw.repository.StationRepository;
import glamour.salon.rw.repository.StylistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.Map;

@Service
public class DashboardService {

    @Autowired private CustomerRepository customerRepo;
    @Autowired private StylistRepository stylistRepo;
    @Autowired private StationRepository stationRepo;
    @Autowired private BookingRepository bookingRepo;
    @Autowired private ProductUsageRepository productUsageRepo;

    public Map<String, Long> summary() {
        Map<String, Long> map = new LinkedHashMap<>();
        map.put("customers", customerRepo.count());
        map.put("stylists", stylistRepo.count());
        map.put("freeStations", (long) stationRepo.findFreeStations().size());
        map.put("bookingsToday", bookingRepo.countByBookingDate(LocalDate.now()));
        map.put("booked", bookingRepo.countByStatus(BookingStatus.BOOKED));
        map.put("productUsages", productUsageRepo.count());
        return map;
    }
}
