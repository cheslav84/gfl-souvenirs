package gfl.havryliuk.souvenirs.testDataProvider;

import gfl.havryliuk.souvenirs.entities.Producer;
import gfl.havryliuk.souvenirs.entities.Souvenir;
import gfl.havryliuk.souvenirs.repository.ProducerRepository;
import gfl.havryliuk.souvenirs.repository.SouvenirRepository;

import java.util.ArrayList;
import java.util.List;

public class ProducerAndSouvenirProvider {

    public static List<Souvenir> initStoragesAndGetSouvenirs(int producers, int souvenirsInProducer,
                                                             ProducerRepository producerRepository,
                                                             SouvenirRepository souvenirRepository) {
        List<Souvenir> allSouvenirs = new ArrayList<>();
        List<Producer> allProducers = new ArrayList<>();
        List<Souvenir> toDelete = new ArrayList<>();
        initStorages(producers, souvenirsInProducer, producerRepository, souvenirRepository,
                toDelete, allSouvenirs, allProducers);
        return toDelete;
    }

    public static Souvenir initStoragesAndGetSouvenir(int producers, int souvenirsInProducer,
                                                             ProducerRepository producerRepository,
                                                             SouvenirRepository souvenirRepository) {

        return initStoragesAndGetSouvenirs(producers, souvenirsInProducer, producerRepository, souvenirRepository)
                .get(0);
    }




    public static List<Producer> initStoragesAndGetProducers(int producers, int souvenirsInProducer,
                                              ProducerRepository producerRepository,
                                              SouvenirRepository souvenirRepository) {
        List<Souvenir> allSouvenirs = new ArrayList<>();
        List<Producer> allProducers = new ArrayList<>();
        List<Souvenir> toDelete = new ArrayList<>();
        initStorages(producers, souvenirsInProducer, producerRepository, souvenirRepository,
                toDelete, allSouvenirs, allProducers);
        return allProducers;
    }


    public static Producer initStoragesAndGetProducer(int producers, int souvenirsInProducer,
                                                      ProducerRepository producerRepository,
                                                      SouvenirRepository souvenirRepository) {
        return initStoragesAndGetProducers(producers, souvenirsInProducer, producerRepository, souvenirRepository)
                .get(producers/2);
    }



    private static void initStorages(int producers, int souvenirsInProducer, ProducerRepository producerRepository,
                                     SouvenirRepository souvenirRepository, List<Souvenir> toDelete,
                                     List<Souvenir> allSouvenirs, List<Producer> allProducers) {
        for (int i = 0; i < producers; i++) {
            Producer producer = ProducerProvider.getProducer();
            List<Souvenir> souvenirs = SouvenirProvider.getSouvenirs(souvenirsInProducer, producer);
            producer.setSouvenirs(souvenirs);
            toDelete.add(souvenirs.get(i));
            allProducers.add(producer);
            allSouvenirs.addAll(souvenirs);
        }
        producerRepository.saveAll(allProducers);
        souvenirRepository.saveAll(allSouvenirs);
    }


}
