package glamour.salon.rw.controller;

import glamour.salon.rw.domain.BeautyService;
import glamour.salon.rw.service.BeautyServiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/beauty-services")
public class BeautyServiceController {

    @Autowired
    private BeautyServiceService beautyServiceService;

    @PostMapping("/save")
    public ResponseEntity<String> save(@RequestBody BeautyService beautyService) {
        String msg = beautyServiceService.save(beautyService);
        return ResponseEntity.status(HttpStatus.CREATED).body(msg);
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok(beautyServiceService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable UUID id) {
        Optional<BeautyService> opt = beautyServiceService.getById(id);
        if (opt.isEmpty()) return ResponseEntity.status(HttpStatus.NOT_FOUND).body("The beauty service with that id does not exist");
        return ResponseEntity.ok(opt.get());
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> update(@PathVariable UUID id, @RequestBody BeautyService details) {
        String msg = beautyServiceService.update(id, details);
        if (msg.equals("The beauty service with that id does not exist")) return ResponseEntity.status(HttpStatus.NOT_FOUND).body(msg);
        return ResponseEntity.ok(msg);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable UUID id) {
        String msg = beautyServiceService.delete(id);
        if (msg.equals("The beauty service with that id does not exist")) return ResponseEntity.status(HttpStatus.NOT_FOUND).body(msg);
        return ResponseEntity.ok(msg);
    }

    // C3 - unused beauty services
    @GetMapping("/unused")
    public ResponseEntity<?> getUnused() {
        return ResponseEntity.ok(beautyServiceService.getUnused());
    }
}
