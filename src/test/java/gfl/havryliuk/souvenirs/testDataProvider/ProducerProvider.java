package gfl.havryliuk.souvenirs.testDataProvider;

import gfl.havryliuk.souvenirs.entities.Producer;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ProducerProvider {
    public static Producer getProducer() {
        return new Producer("Ukraine Producer", "Ukraine");
    }

    public static Producer getProducerWithSouvenirs() {
        Producer producer = new Producer("UK Producer", "UK");
        producer.getSouvenirs().add(SouvenirProvider.getSouvenirWithOnlyId());
        producer.getSouvenirs().add(SouvenirProvider.getSouvenirWithOnlyId());
        return producer;
    }

    public static Producer getProducerWithOnlyId() {
        Producer producer = new Producer();
        producer.setId(UUID.randomUUID());
        return producer;
    }


    public static List<Producer> getProducers(int number) {
        List<Producer> producers = new ArrayList<>(number);
        for (int i = 0; i < number; i++) {
            producers.add(getProducerWithSouvenirs());
        }
        return producers;
    }
}
