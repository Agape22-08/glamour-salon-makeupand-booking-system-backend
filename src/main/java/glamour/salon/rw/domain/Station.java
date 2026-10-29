package glamour.salon.rw.domain;

import jakarta.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "station")
public class Station {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "station_number")
    private int stationNumber;

    @Column(name = "name")
    private String name;

    public UUID getId() { return id; }

    public int getStationNumber() { return stationNumber; }
    public void setStationNumber(int stationNumber) { this.stationNumber = stationNumber; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
}
