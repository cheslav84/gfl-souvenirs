package gfl.havryliuk.souvenirs.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class Souvenir implements Entity {
    private UUID id;

    private String name;

    private double price;

    private LocalDateTime productionDate;

    @JsonIgnoreProperties(value = {"name", "country", "souvenirs" })
    private Producer producer;

    public Souvenir(String name, double price, LocalDateTime productionDate, Producer producer) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.price = price;
        this.productionDate = productionDate;
        this.producer = producer;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Souvenir souvenir = (Souvenir) o;

        if (Double.compare(price, souvenir.price) != 0) return false;
        if (!Objects.equals(id, souvenir.id)) return false;
        if (!Objects.equals(name, souvenir.name)) return false;
        if (!Objects.equals(productionDate, souvenir.productionDate)) return false;
        return Objects.equals(producer.getId(), souvenir.producer.getId());
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        temp = Double.doubleToLongBits(price);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (productionDate != null ? productionDate.hashCode() : 0);
        result = 31 * result + (producer.getId() != null ? producer.getId().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Souvenir" +
                " named '" + name + '\'' +
                ", costs ₴" + price +
                ", produced on " + productionDate.format(DateTimeFormatter.ISO_LOCAL_DATE);
    }
}

