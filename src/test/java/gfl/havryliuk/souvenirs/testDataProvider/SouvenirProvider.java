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
                LocalDateTime.parse("2018-12-30T19:34:50.63"),
                producer
        );
        producer.getSouvenirs().add(souvenir);
        return souvenir;
    }

    public static Souvenir getSouvenir(Producer producer) {
        LocalDateTime date = LocalDateTime.of(2014, 12, 20, 2, 30);

        return new Souvenir(
                "Tea cup",
                80.99,
                date,
                producer
        );
    }


    public static Souvenir getSouvenirWithOnlyId() {
        Souvenir souvenir = new Souvenir();
        souvenir.setId(UUID.randomUUID());
        return souvenir;
    }

    public static List<Souvenir> getSouvenirsWithProducer(int number) {
        Producer producer = ProducerProvider.getProducer();
        List<Souvenir> souvenirs = new ArrayList<>(number);
        for (int i = 0; i < number; i++) {
//            souvenirs.add(getSouvenirWithProducer());
            souvenirs.add(getSouvenir(producer));
        }
        producer.setSouvenirs(souvenirs);
        return souvenirs;
    }

    public static List<Souvenir> getSouvenirs(int number, Producer producer) {
        List<Souvenir> souvenirs = new ArrayList<>(number);
        for (int i = 0; i < number; i++) {
            souvenirs.add(getSouvenir(producer));
        }
        return souvenirs;
    }



}
