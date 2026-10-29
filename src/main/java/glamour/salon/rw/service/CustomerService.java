package glamour.salon.rw.service;

import glamour.salon.rw.domain.Customer;
import glamour.salon.rw.repository.CustomerRepository;
import glamour.salon.rw.repository.StylistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepo;

    @Autowired
    private StylistRepository stylistRepo;

    public String save(Customer customer) {
        customerRepo.save(customer);
        return "Customer saved successfully";
    }

    public List<Customer> getAll() {
        return customerRepo.findAll();
    }

    public Optional<Customer> getById(UUID id) {
        return customerRepo.findById(id);
    }

    public String update(UUID id, Customer details) {
        Optional<Customer> opt = customerRepo.findById(id);
        if (opt.isEmpty()) return "The customer with that id does not exist";
        Customer c = opt.get();
        c.setFirstName(details.getFirstName());
        c.setLastName(details.getLastName());
        c.setPhone(details.getPhone());
        c.setEmail(details.getEmail());
        customerRepo.save(c);
        return "Customer updated successfully";
    }

    public String delete(UUID id) {
        if (!customerRepo.existsById(id)) return "The customer with that id does not exist";
        customerRepo.deleteById(id);
        return "Customer deleted successfully";
    }

    // B1 - by lastName ignoring case
    public List<Customer> getByLastName(String lastName) {
        return customerRepo.findByLastNameIgnoreCaseOrderByFirstNameAsc(lastName);
    }

    // C4 - customers of a stylist
    public String getCustomersOfStylist(UUID stylistId) {
        if (!stylistRepo.existsById(stylistId)) return "The stylist with that id does not exist";
        return null;
    }

    public List<Customer> findCustomersOfStylist(UUID stylistId) {
        return customerRepo.findCustomersOfStylist(stylistId);
    }
}
