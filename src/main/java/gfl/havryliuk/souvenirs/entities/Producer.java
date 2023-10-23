package gfl.havryliuk.souvenirs.entities;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;


@Getter
@Setter
@NoArgsConstructor
//@JsonIgnoreProperties(ignoreUnknown = true)
public class Producer {

//    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private UUID id;

//    @JsonProperty(access = JsonProperty.Access.READ_WRITE, required = true)
    private String name;

//    @JsonProperty(access = JsonProperty.Access.READ_WRITE, required = true)
    private String country;


//    @JsonIdentityInfo(generator= ObjectIdGenerators.PropertyGenerator.class, property="id")
//    @JsonIdentityReference(alwaysAsId=true)
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
        if (!Objects.equals(country, producer.country)) return false;


        return true;
//        return Objects.equals(souvenirs, producer.souvenirs);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (country != null ? country.hashCode() : 0);
//        result = 31 * result + (souvenirs != null ? souvenirs.hashCode() : 0);
        return result;
    }
}
