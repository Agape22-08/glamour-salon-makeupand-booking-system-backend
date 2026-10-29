package glamour.salon.rw.service;

import glamour.salon.rw.domain.BeautyService;
import glamour.salon.rw.repository.BeautyServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class BeautyServiceService {

    @Autowired
    private BeautyServiceRepository beautyServiceRepo;

    public String save(BeautyService beautyService) {
        beautyServiceRepo.save(beautyService);
        return "Beauty service saved successfully";
    }

    public List<BeautyService> getAll() {
        return beautyServiceRepo.findAll();
    }

    public Optional<BeautyService> getById(UUID id) {
        return beautyServiceRepo.findById(id);
    }

    public String update(UUID id, BeautyService details) {
        Optional<BeautyService> opt = beautyServiceRepo.findById(id);
        if (opt.isEmpty()) return "The beauty service with that id does not exist";
        BeautyService bs = opt.get();
        bs.setName(details.getName());
        beautyServiceRepo.save(bs);
        return "Beauty service updated successfully";
    }

    public String delete(UUID id) {
        if (!beautyServiceRepo.existsById(id)) return "The beauty service with that id does not exist";
        beautyServiceRepo.deleteById(id);
        return "Beauty service deleted successfully";
    }

    public List<BeautyService> getUnused() {
        return beautyServiceRepo.findUnusedBeautyServices();
    }
}
