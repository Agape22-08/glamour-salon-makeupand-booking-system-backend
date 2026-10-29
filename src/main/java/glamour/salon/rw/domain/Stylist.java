package glamour.salon.rw.domain;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "stylist")
public class Stylist {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "phone")
    private String phone;

    @ManyToOne
    @JoinColumn(name = "station_id")
    private Station station;

    @ManyToMany
    @JoinTable(
        name = "stylist_beauty_service",
        joinColumns = @JoinColumn(name = "stylist_id"),
        inverseJoinColumns = @JoinColumn(name = "beauty_service_id")
    )
    private List<BeautyService> beautyServices = new ArrayList<>();

    public UUID getId() { return id; }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public Station getStation() { return station; }
    public void setStation(Station station) { this.station = station; }

    public List<BeautyService> getBeautyServices() { return beautyServices; }
    public void setBeautyServices(List<BeautyService> beautyServices) { this.beautyServices = beautyServices; }
}
