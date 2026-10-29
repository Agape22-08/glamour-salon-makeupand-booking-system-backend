package glamour.salon.rw.controller;

import glamour.salon.rw.domain.Station;
import glamour.salon.rw.service.StationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/stations")
public class StationController {

    @Autowired
    private StationService stationService;

    @PostMapping("/save")
    public ResponseEntity<String> save(@RequestBody Station station) {
        String msg = stationService.save(station);
        return ResponseEntity.status(HttpStatus.CREATED).body(msg);
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok(stationService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable UUID id) {
        Optional<Station> opt = stationService.getById(id);
        if (opt.isEmpty()) return ResponseEntity.status(HttpStatus.NOT_FOUND).body("The station with that id does not exist");
        return ResponseEntity.ok(opt.get());
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> update(@PathVariable UUID id, @RequestBody Station details) {
        String msg = stationService.update(id, details);
        if (msg.equals("The station with that id does not exist")) return ResponseEntity.status(HttpStatus.NOT_FOUND).body(msg);
        return ResponseEntity.ok(msg);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable UUID id) {
        String msg = stationService.delete(id);
        if (msg.equals("The station with that id does not exist")) return ResponseEntity.status(HttpStatus.NOT_FOUND).body(msg);
        return ResponseEntity.ok(msg);
    }

    // C2 - free stations
    @GetMapping("/free")
    public ResponseEntity<?> getFree() {
        return ResponseEntity.ok(stationService.getFreeStations());
    }
}
