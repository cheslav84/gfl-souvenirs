package gfl.havryliuk.souvenirs.testDataProvider;

import gfl.havryliuk.souvenirs.entities.Producer;
import gfl.havryliuk.souvenirs.entities.Souvenir;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

public class ProducerProvider {
    public static Producer getProducer() {
        return new Producer("Ukraine Producer", "Ukraine");
    }

    public static Producer getProducerWithSouvenirs() {
        Producer producer = new Producer("UK Producer", "UK");
        producer.getSouvenirs().add(SouvenirProvider.getSouvenir(producer));
        producer.getSouvenirs().add(SouvenirProvider.getSouvenir(producer));
        return producer;
    }

    public static Producer getProducerWithOnlyId() {
        Producer producer = new Producer();
        producer.setId(UUID.randomUUID());
        producer.setSouvenirs(new ArrayList<>());
        return producer;
    }


    public static List<Producer> getProducers(int number) {
        List<Producer> producers = new ArrayList<>(number);
        for (int i = 0; i < number; i++) {
            producers.add(getProducerWithSouvenirs());
        }
        return producers;
    }

    public static List<Producer> getProducerWithSouvenirPrices (int number, double searchPrices) {
        Random random = new Random();

        List<Producer> producers = new ArrayList<>(number);

        for (int i = 0; i < number; i++) {
            String name = "Producer " + i + 1;
            String country = "Country " + i + 1;
            Producer producer = new Producer(name, country);
            int souvenirsAmount = number / 2 > 1 ? number / 2 : number;

            List<Souvenir> souvenirs = SouvenirProvider.getSouvenirs(souvenirsAmount, producer);
            for (int j = 0; j < souvenirsAmount; j++) {
                double souvenirPrice;
                if (i % 2 == 0) {
                    souvenirPrice = searchPrices + random.nextDouble(1, 10);
                } else {
                    souvenirPrice = searchPrices - random.nextDouble(1, 10);
                }
                souvenirs.get(j).setPrice(souvenirPrice);
            }
            producer.setSouvenirs(souvenirs);
            producers.add(producer);
        }
        return producers;
    }


}
