package glamour.salon.rw.domain;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "product_usage")
public class ProductUsage {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "product_name")
    private String productName;

    @Column(name = "notes")
    private String notes;

    @Column(name = "quantity")
    private int quantity;

    @Column(name = "used_date")
    private LocalDate usedDate;

    @ManyToOne
    @JoinColumn(name = "booking_id")
    private Booking booking;

    public UUID getId() { return id; }

    public String getProductName() { return productName; }
    public void setProductName(String productName) { this.productName = productName; }

    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }

    public LocalDate getUsedDate() { return usedDate; }
    public void setUsedDate(LocalDate usedDate) { this.usedDate = usedDate; }

    public Booking getBooking() { return booking; }
    public void setBooking(Booking booking) { this.booking = booking; }
}
