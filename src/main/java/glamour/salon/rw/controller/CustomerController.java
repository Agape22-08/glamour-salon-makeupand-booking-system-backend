package glamour.salon.rw.controller;

import glamour.salon.rw.domain.Customer;
import glamour.salon.rw.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @PostMapping("/save")
    public ResponseEntity<String> save(@RequestBody Customer customer) {
        String msg = customerService.save(customer);
        return ResponseEntity.status(HttpStatus.CREATED).body(msg);
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok(customerService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable UUID id) {
        Optional<Customer> opt = customerService.getById(id);
        if (opt.isEmpty()) return ResponseEntity.status(HttpStatus.NOT_FOUND).body("The customer with that id does not exist");
        return ResponseEntity.ok(opt.get());
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> update(@PathVariable UUID id, @RequestBody Customer details) {
        String msg = customerService.update(id, details);
        if (msg.equals("The customer with that id does not exist")) return ResponseEntity.status(HttpStatus.NOT_FOUND).body(msg);
        return ResponseEntity.ok(msg);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable UUID id) {
        String msg = customerService.delete(id);
        if (msg.equals("The customer with that id does not exist")) return ResponseEntity.status(HttpStatus.NOT_FOUND).body(msg);
        return ResponseEntity.ok(msg);
    }

    // B1 - by lastName ignoring case
    @GetMapping("/by-last-name")
    public ResponseEntity<?> getByLastName(@RequestParam String lastName) {
        return ResponseEntity.ok(customerService.getByLastName(lastName));
    }

    // C4 - customers of a stylist
    @GetMapping("/of-stylist/{stylistId}")
    public ResponseEntity<?> getCustomersOfStylist(@PathVariable UUID stylistId) {
        String error = customerService.getCustomersOfStylist(stylistId);
        if (error != null) return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        return ResponseEntity.ok(customerService.findCustomersOfStylist(stylistId));
    }
}
