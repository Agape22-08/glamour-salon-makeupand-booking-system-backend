package glamour.salon.rw.service;

import glamour.salon.rw.domain.BeautyService;
import glamour.salon.rw.domain.Station;
import glamour.salon.rw.domain.Stylist;
import glamour.salon.rw.repository.BeautyServiceRepository;
import glamour.salon.rw.repository.StationRepository;
import glamour.salon.rw.repository.StylistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class StylistService {

    @Autowired
    private StylistRepository stylistRepo;

    @Autowired
    private BeautyServiceRepository beautyServiceRepo;

    @Autowired
    private StationRepository stationRepo;

    public String save(Stylist stylist) {
        stylistRepo.save(stylist);
        return "Stylist saved successfully";
    }

    public List<Stylist> getAll() {
        return stylistRepo.findAll();
    }

    public Optional<Stylist> getById(UUID id) {
        return stylistRepo.findById(id);
    }

    public String update(UUID id, Stylist details) {
        Optional<Stylist> opt = stylistRepo.findById(id);
        if (opt.isEmpty()) return "The stylist with that id does not exist";
        Stylist s = opt.get();
        s.setFirstName(details.getFirstName());
        s.setLastName(details.getLastName());
        s.setPhone(details.getPhone());
        stylistRepo.save(s);
        return "Stylist updated successfully";
    }

    public String delete(UUID id) {
        if (!stylistRepo.existsById(id)) return "The stylist with that id does not exist";
        stylistRepo.deleteById(id);
        return "Stylist deleted successfully";
    }

    public List<Stylist> getByBeautyService(String name) {
        return stylistRepo.findByBeautyServiceName(name);
    }

    // D3 - add beauty service to stylist
    public String addBeautyService(UUID stylistId, UUID beautyServiceId) {
        Optional<Stylist> optStylist = stylistRepo.findById(stylistId);
        if (optStylist.isEmpty()) return "The stylist with that id does not exist";
        Optional<BeautyService> optBs = beautyServiceRepo.findById(beautyServiceId);
        if (optBs.isEmpty()) return "That beauty service does not exist";
        Stylist stylist = optStylist.get();
        BeautyService bs = optBs.get();
        if (stylist.getBeautyServices().contains(bs)) return "The stylist already has that beauty service";
        stylist.getBeautyServices().add(bs);
        stylistRepo.save(stylist);
        return "Beauty service added to the stylist";
    }

    // D4 - move stylist to another station
    public String moveToStation(UUID stylistId, int stationNumber) {
        Optional<Stylist> optStylist = stylistRepo.findById(stylistId);
        if (optStylist.isEmpty()) return "The stylist with that id does not exist";
        Optional<Station> optStation = stationRepo.findByStationNumber(stationNumber);
        if (optStation.isEmpty()) return "The station with that stationNumber does not exist";
        Station newStation = optStation.get();
        Stylist stylist = optStylist.get();
        Optional<Stylist> occupant = stylistRepo.findByStation(newStation);
        if (occupant.isPresent() && !occupant.get().getId().equals(stylistId)) return "That station is taken by another stylist";
        stylist.setStation(newStation);
        stylistRepo.save(stylist);
        return "Station changed successfully";
    }
}
