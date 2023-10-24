package gfl.havryliuk.souvenirs.testDataProvider;

import gfl.havryliuk.souvenirs.entities.Producer;
import gfl.havryliuk.souvenirs.entities.Souvenir;
import gfl.havryliuk.souvenirs.repository.ProducerRepository;
import gfl.havryliuk.souvenirs.repository.SouvenirRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ProducerAndSouvenirProvider {

    public static List<Souvenir> initStoragesAndGetSouvenirs(int producers, int souvenirsInProducer,
                                                             ProducerRepository producerRepository,
                                                             SouvenirRepository souvenirRepository) {
        List<Souvenir> allSouvenirs = new ArrayList<>();
        List<Producer> allProducers = new ArrayList<>();
        List<Souvenir> toReturn = new ArrayList<>();
        initStorages(producers, souvenirsInProducer, producerRepository, souvenirRepository,
                toReturn, allSouvenirs, allProducers);
        return toReturn;
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
        List<Souvenir> stub = new ArrayList<>();
        initStorages(producers, souvenirsInProducer, producerRepository, souvenirRepository,
                stub, allSouvenirs, allProducers);
        return allProducers;
    }


    public static Producer initStoragesAndGetProducer(int producers, int souvenirsInProducer,
                                                      ProducerRepository producerRepository,
                                                      SouvenirRepository souvenirRepository) {
        return initStoragesAndGetProducers(producers, souvenirsInProducer, producerRepository, souvenirRepository)
                .get(producers/2);
    }


    public static List<Producer> initStoragesAndGetProducers(int producers, int souvenirsInProducer,
                                                             String name, String year,
                                                             ProducerRepository producerRepository,
                                                             SouvenirRepository souvenirRepository) {
        List<Souvenir> allSouvenirs = new ArrayList<>();
        List<Producer> allProducers = new ArrayList<>();
        List<Souvenir> stub = new ArrayList<>();
        initStorages(producers, souvenirsInProducer, producerRepository, souvenirRepository,
                stub , allSouvenirs, allProducers);

        List<Producer> toReturn = new ArrayList<>();
        for (int i = 0; i < allProducers.size(); i++) {
            if (i % 3 == 0) {
                Producer currentProducer = allProducers.get(i);
                List<Souvenir> souvenirs = currentProducer.getSouvenirs();
                for (int j = 0; j < souvenirs.size(); j++) {
                    if (j % 3 == 0) {
                        Souvenir currentSouvenir = souvenirs.get(j);
                        currentSouvenir.setName(name);
                        currentSouvenir.setProductionDate(LocalDateTime.parse(year + "-02-02T00:00:00.00"));
                    }
                }
                toReturn.add(currentProducer);
            }
        }

        producerRepository.saveAll(toReturn);


        return toReturn;
    }


    private static void initStorages(int producers, int souvenirsInProducer, ProducerRepository producerRepository,
                                     SouvenirRepository souvenirRepository,
                                     List<Souvenir> toReturn,
                                     List<Souvenir> allSouvenirs, List<Producer> allProducers) {
        for (int i = 0; i < producers; i++) {
            Producer producer = ProducerProvider.getProducer();
            List<Souvenir> souvenirs = SouvenirProvider.getSouvenirs(souvenirsInProducer, producer);
            producer.setSouvenirs(souvenirs);
            toReturn.add(souvenirs.get(i));
            allProducers.add(producer);
            allSouvenirs.addAll(souvenirs);
        }
        producerRepository.saveAll(allProducers);
//        souvenirRepository.saveAll(allSouvenirs);
    }


}
