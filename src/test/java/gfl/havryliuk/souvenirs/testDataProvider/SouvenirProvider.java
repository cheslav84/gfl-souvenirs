package gfl.havryliuk.souvenirs.testDataProvider;

import gfl.havryliuk.souvenirs.entities.Producer;
import gfl.havryliuk.souvenirs.entities.Souvenir;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class SouvenirProvider {


    public static Souvenir getSouvenirWithProducer() {
        Producer producer = ProducerProvider.getProducerWithOnlyId();
        Souvenir souvenir = new Souvenir(
                "Tea cup",
                80.99,
                LocalDateTime.parse("2018-12-30T00:00:00"),
                producer
        );
        producer.getSouvenirs().add(souvenir);
        return souvenir;
    }

    public static Souvenir getSouvenir(Producer producer) {
        return new Souvenir("Tea cup",80.99,  LocalDateTime.parse("2014-12-30T00:00:00"), producer);
    }

//
//    public static Souvenir getSouvenirWithOnlyId() {
//        Souvenir souvenir = new Souvenir();
//        souvenir.setId(UUID.randomUUID());
//        return souvenir;
//    }

    public static List<Souvenir> getSouvenirsWithProducer(int number) {
        Producer producer = ProducerProvider.getProducer();
        List<Souvenir> souvenirs = new ArrayList<>(number);
        for (int i = 0; i < number; i++) {
            souvenirs.add(getSouvenir(producer));
        }
        producer.setSouvenirs(souvenirs);
        return souvenirs;
    }

    public static List<Souvenir> getSouvenirs(int number, Producer producer) {
        List<Souvenir> souvenirs = new ArrayList<>(number);
        for (int i = 0; i < number; i++) {
            Souvenir souvenir = getSouvenir(producer);
            String year = switch (i / 10) {
                case 0 -> "200" + i;
                case 1 -> "20" + i;
                case 2 -> "2" + i;
                case 3 -> "" + i;
                default -> throw new IllegalStateException("Unexpected value: " + i);
            };
            souvenir.setProductionDate(LocalDateTime.parse(year + "-12-30T00:00:00"));
            souvenirs.add(souvenir);
        }
        return souvenirs;
    }



}
