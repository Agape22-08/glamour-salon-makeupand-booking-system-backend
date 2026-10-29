package glamour.salon.rw.controller;

import glamour.salon.rw.domain.Booking;
import glamour.salon.rw.domain.BookingStatus;
import glamour.salon.rw.service.BookingService;
import glamour.salon.rw.service.ProductUsageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    @Autowired
    private BookingService bookingService;

    @Autowired
    private ProductUsageService productUsageService;

    // D1 - safe save (also A2)
    @PostMapping("/save")
    public ResponseEntity<String> save(@RequestBody Booking booking) {
        String msg = bookingService.save(booking);
        if (msg.equals("Booking saved successfully")) return ResponseEntity.status(HttpStatus.CREATED).body(msg);
        if (msg.equals("Stylist is already booked on that date")) return ResponseEntity.status(HttpStatus.CONFLICT).body(msg);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(msg);
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok(bookingService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable UUID id) {
        Optional<Booking> opt = bookingService.getById(id);
        if (opt.isEmpty()) return ResponseEntity.status(HttpStatus.NOT_FOUND).body("The booking with that id does not exist");
        return ResponseEntity.ok(opt.get());
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> update(@PathVariable UUID id, @RequestBody Booking details) {
        String msg = bookingService.update(id, details);
        if (msg.equals("The booking with that id does not exist")) return ResponseEntity.status(HttpStatus.NOT_FOUND).body(msg);
        return ResponseEntity.ok(msg);
    }

    // E3 - protect booking deletion
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable UUID id) {
        Optional<Booking> opt = bookingService.getById(id);
        if (opt.isEmpty()) return ResponseEntity.status(HttpStatus.NOT_FOUND).body("The booking with that id does not exist");
        if (productUsageService.existsByBooking(opt.get()))
            return ResponseEntity.status(HttpStatus.CONFLICT).body("That booking has product usages, delete them first");
        String msg = bookingService.delete(id);
        return ResponseEntity.ok(msg);
    }

    // B2 - by status
    @GetMapping("/by-status")
    public ResponseEntity<?> getByStatus(@RequestParam BookingStatus status) {
        return ResponseEntity.ok(bookingService.getByStatus(status));
    }

    // B3 - between dates
    @GetMapping("/between")
    public ResponseEntity<?> getBetween(@RequestParam String start, @RequestParam String end) {
        return ResponseEntity.ok(bookingService.getBetween(LocalDate.parse(start), LocalDate.parse(end)));
    }

    // D2 - status flow
    @PatchMapping("/{id}/status")
    public ResponseEntity<String> updateStatus(@PathVariable UUID id, @RequestParam BookingStatus status) {
        String msg = bookingService.updateStatus(id, status);
        if (msg.equals("The booking with that id does not exist")) return ResponseEntity.status(HttpStatus.NOT_FOUND).body(msg);
        if (msg.startsWith("Cannot change status")) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(msg);
        return ResponseEntity.ok(msg);
    }

    // F1 - stats by status
    @GetMapping("/stats/by-status")
    public ResponseEntity<?> getStatsByStatus() {
        return ResponseEntity.ok(bookingService.getStatsByStatus());
    }

    // F2 - cancel day
    @PatchMapping("/cancel-day")
    public ResponseEntity<String> cancelDay(@RequestParam UUID stylistId, @RequestParam String date) {
        int count = bookingService.cancelDay(stylistId, LocalDate.parse(date));
        return ResponseEntity.ok(count + " bookings cancelled");
    }

    // Bonus - pagination
    @GetMapping("/page")
    public ResponseEntity<?> getPage(@RequestParam int page, @RequestParam int size, @RequestParam String sort) {
        String[] parts = sort.split(",");
        Sort.Direction dir = parts.length > 1 && parts[1].equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(dir, parts[0]));
        return ResponseEntity.ok(bookingService.getPage(pageable));
    }
}
