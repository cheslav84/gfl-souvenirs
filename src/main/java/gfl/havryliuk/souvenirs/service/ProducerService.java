package gfl.havryliuk.souvenirs.service;

import gfl.havryliuk.souvenirs.entities.Producer;
import gfl.havryliuk.souvenirs.repository.ProducerRepository;
import gfl.havryliuk.souvenirs.repository.SouvenirRepository;
import gfl.havryliuk.souvenirs.storage.ProducerFileStorage;
import gfl.havryliuk.souvenirs.storage.SouvenirFileStorage;

import java.util.List;

public class ProducerService {

    private final ProducerRepository producerRepository;

    private final SouvenirRepository souvenirRepository;

    public ProducerService(ProducerFileStorage producerStorage, SouvenirFileStorage souvenirStorage) {
        souvenirRepository = new SouvenirRepository(souvenirStorage, producerStorage);
        producerRepository = new ProducerRepository(producerStorage, souvenirRepository);
    }

    public void createAll(List<Producer> producers){
        producerRepository.saveAll(producers);
    }
}
