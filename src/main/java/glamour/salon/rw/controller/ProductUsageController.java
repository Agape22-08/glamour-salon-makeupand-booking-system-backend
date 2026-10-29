package glamour.salon.rw.controller;

import glamour.salon.rw.domain.ProductUsage;
import glamour.salon.rw.service.ProductUsageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/product-usages")
public class ProductUsageController {

    @Autowired
    private ProductUsageService productUsageService;

    // E1 - save product usage
    @PostMapping("/save")
    public ResponseEntity<String> save(@RequestParam UUID bookingId, @RequestBody ProductUsage productUsage) {
        String msg = productUsageService.save(bookingId, productUsage);
        if (msg.equals("Product usage saved successfully")) return ResponseEntity.status(HttpStatus.CREATED).body(msg);
        if (msg.equals("The booking with that id does not exist")) return ResponseEntity.status(HttpStatus.NOT_FOUND).body(msg);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(msg);
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok(productUsageService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable UUID id) {
        Optional<ProductUsage> opt = productUsageService.getById(id);
        if (opt.isEmpty()) return ResponseEntity.status(HttpStatus.NOT_FOUND).body("The product usage with that id does not exist");
        return ResponseEntity.ok(opt.get());
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> update(@PathVariable UUID id, @RequestBody ProductUsage details) {
        String msg = productUsageService.update(id, details);
        if (msg.equals("The product usage with that id does not exist")) return ResponseEntity.status(HttpStatus.NOT_FOUND).body(msg);
        return ResponseEntity.ok(msg);
    }

    // E3 - delete product usage
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable UUID id) {
        String msg = productUsageService.delete(id);
        if (msg.equals("The product usage with that id does not exist")) return ResponseEntity.status(HttpStatus.NOT_FOUND).body(msg);
        return ResponseEntity.ok(msg);
    }

    // E2 - history by customer phone
    @GetMapping("/customer/{phone}")
    public ResponseEntity<?> getByCustomerPhone(@PathVariable String phone) {
        return ResponseEntity.ok(productUsageService.getByCustomerPhone(phone));
    }
}
