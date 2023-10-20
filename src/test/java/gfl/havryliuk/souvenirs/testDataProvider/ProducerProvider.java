package gfl.havryliuk.souvenirs.testDataProvider;

import gfl.havryliuk.souvenirs.entities.Producer;

import java.util.ArrayList;
import java.util.List;

public class ProducerProvider {
    public static Producer getProducer() {
        return new Producer("Ukraine Producer", "Ukraine");
    }

    public static Producer getProducerWithSouvenirs() {
        Producer ukProducer = new Producer("UK Producer", "UK");
        ukProducer.getSouvenirs().add(SouvenirProvider.getSouvenirWithOnlyId());
        ukProducer.getSouvenirs().add(SouvenirProvider.getSouvenirWithOnlyId());
        return ukProducer;
    }


    public static List<Producer> getProducers(int number) {
        List<Producer> producers = new ArrayList<>(number);
        for (int i = 0; i < number; i++) {
            producers.add(getProducerWithSouvenirs());
        }
        return producers;
    }
}
