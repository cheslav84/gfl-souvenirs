package gfl.havryliuk.souvenirs.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class Souvenir {
//    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private UUID id;

//    @JsonProperty(access = JsonProperty.Access.READ_WRITE, required = true)
    private String name;

//    @JsonProperty(access = JsonProperty.Access.READ_WRITE, required = true)
    private double price;

//    @JsonProperty(access = JsonProperty.Access.READ_WRITE, required = true)
    private LocalDateTime productionDate;

//    @JsonProperty(access = JsonProperty.Access.READ_WRITE, required = true)
    private Producer producer;

    public Souvenir(String name, double price, LocalDateTime productionDate, Producer producer) {
        this.id = UUID.randomUUID();;
        this.name = name;
        this.price = price;
        this.productionDate = productionDate;
        this.producer = producer;
    }


    public Souvenir(UUID id) {
        this.id = id;
    }
}
