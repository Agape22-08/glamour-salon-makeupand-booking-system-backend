package glamour.salon.rw.controller;

import glamour.salon.rw.domain.Stylist;
import glamour.salon.rw.service.StylistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/stylists")
public class StylistController {

    @Autowired
    private StylistService stylistService;

    @PostMapping("/save")
    public ResponseEntity<String> save(@RequestBody Stylist stylist) {
        String msg = stylistService.save(stylist);
        return ResponseEntity.status(HttpStatus.CREATED).body(msg);
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok(stylistService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable UUID id) {
        Optional<Stylist> opt = stylistService.getById(id);
        if (opt.isEmpty()) return ResponseEntity.status(HttpStatus.NOT_FOUND).body("The stylist with that id does not exist");
        return ResponseEntity.ok(opt.get());
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> update(@PathVariable UUID id, @RequestBody Stylist details) {
        String msg = stylistService.update(id, details);
        if (msg.equals("The stylist with that id does not exist")) return ResponseEntity.status(HttpStatus.NOT_FOUND).body(msg);
        return ResponseEntity.ok(msg);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable UUID id) {
        String msg = stylistService.delete(id);
        if (msg.equals("The stylist with that id does not exist")) return ResponseEntity.status(HttpStatus.NOT_FOUND).body(msg);
        return ResponseEntity.ok(msg);
    }

    // C1 - stylists by beauty service name
    @GetMapping("/by-beauty-service")
    public ResponseEntity<?> getByBeautyService(@RequestParam String name) {
        return ResponseEntity.ok(stylistService.getByBeautyService(name));
    }

    // D3 - add beauty service to stylist
    @PutMapping("/{stylistId}/beauty-services/{beautyServiceId}")
    public ResponseEntity<String> addBeautyService(@PathVariable UUID stylistId, @PathVariable UUID beautyServiceId) {
        String msg = stylistService.addBeautyService(stylistId, beautyServiceId);
        if (msg.equals("The stylist with that id does not exist") || msg.equals("That beauty service does not exist"))
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(msg);
        if (msg.equals("The stylist already has that beauty service"))
            return ResponseEntity.status(HttpStatus.CONFLICT).body(msg);
        return ResponseEntity.ok(msg);
    }

    // D4 - move stylist to another station
    @PatchMapping("/{stylistId}/station")
    public ResponseEntity<String> moveToStation(@PathVariable UUID stylistId, @RequestParam int stationNumber) {
        String msg = stylistService.moveToStation(stylistId, stationNumber);
        if (msg.equals("The stylist with that id does not exist") || msg.equals("The station with that stationNumber does not exist"))
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(msg);
        if (msg.equals("That station is taken by another stylist"))
            return ResponseEntity.status(HttpStatus.CONFLICT).body(msg);
        return ResponseEntity.ok(msg);
    }
}
