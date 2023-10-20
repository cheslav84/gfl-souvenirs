package gfl.havryliuk.souvenirs.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
public class Producer implements Cloneable {

//    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private UUID id;

//    @JsonProperty(access = JsonProperty.Access.READ_WRITE, required = true)
    private String name;

//    @JsonProperty(access = JsonProperty.Access.READ_WRITE, required = true)
    private String country;

//    @JsonProperty(access = JsonProperty.Access.READ_WRITE)
    private List<Souvenir> souvenirs;

    public Producer(String name, String country) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.country = country;
        this.souvenirs = new ArrayList<>();
    }

    public Producer (Producer producer) {
        this.id = producer.getId();
        this.name = producer.getName();
        this.country = producer.getCountry();
        this.souvenirs = producer.souvenirs.stream()
                .map(s -> new Souvenir(s.getId()))
                .collect(Collectors.toList());
    }


}
