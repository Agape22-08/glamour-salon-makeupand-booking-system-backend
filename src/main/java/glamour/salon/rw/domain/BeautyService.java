package glamour.salon.rw.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "beauty_service")
public class BeautyService {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "name")
    private String name;

    @ManyToMany(mappedBy = "beautyServices")
    @JsonIgnore
    private List<Stylist> stylists = new ArrayList<>();

    public UUID getId() { return id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public List<Stylist> getStylists() { return stylists; }
    public void setStylists(List<Stylist> stylists) { this.stylists = stylists; }
}
