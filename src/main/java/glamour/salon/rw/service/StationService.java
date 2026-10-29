package glamour.salon.rw.service;

import glamour.salon.rw.domain.Station;
import glamour.salon.rw.repository.StationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class StationService {

    @Autowired
    private StationRepository stationRepo;

    public String save(Station station) {
        stationRepo.save(station);
        return "Station saved successfully";
    }

    public List<Station> getAll() {
        return stationRepo.findAll();
    }

    public Optional<Station> getById(UUID id) {
        return stationRepo.findById(id);
    }

    public String update(UUID id, Station details) {
        Optional<Station> opt = stationRepo.findById(id);
        if (opt.isEmpty()) return "The station with that id does not exist";
        Station s = opt.get();
        s.setStationNumber(details.getStationNumber());
        s.setName(details.getName());
        stationRepo.save(s);
        return "Station updated successfully";
    }

    public String delete(UUID id) {
        if (!stationRepo.existsById(id)) return "The station with that id does not exist";
        stationRepo.deleteById(id);
        return "Station deleted successfully";
    }

    public List<Station> getFreeStations() {
        return stationRepo.findFreeStations();
    }
}
