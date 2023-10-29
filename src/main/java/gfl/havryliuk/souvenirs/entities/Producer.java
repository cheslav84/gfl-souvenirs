package gfl.havryliuk.souvenirs.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Producer implements Entity {

    private UUID id;

    private String name;

    private String country;


    @JsonIgnoreProperties(value = {"name", "price", "productionDate", "producer" }, allowSetters = true)
    private List<Souvenir> souvenirs;

    public Producer(String name, String country) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.country = country;
        this.souvenirs = new ArrayList<>();
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Producer producer = (Producer) o;
        if (!Objects.equals(id, producer.id)) return false;
        if (!Objects.equals(name, producer.name)) return false;
        return (Objects.equals(country, producer.country));
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (country != null ? country.hashCode() : 0);
        return result;
    }


    @Override
    public String toString() {
        return "Producer" +
                " named '" + name + '\'' +
                ", from '" + country + '\'';
    }
}
