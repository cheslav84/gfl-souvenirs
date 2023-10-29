package gfl.havryliuk.souvenirs.service;

import gfl.havryliuk.souvenirs.entities.Producer;
import gfl.havryliuk.souvenirs.entities.Souvenir;
import gfl.havryliuk.souvenirs.repository.ProducerRepository;
import gfl.havryliuk.souvenirs.repository.SouvenirRepository;
import gfl.havryliuk.souvenirs.storage.ProducerFileStorage;
import gfl.havryliuk.souvenirs.storage.SouvenirFileStorage;

import java.util.List;

public class ProducerService {

    private final ProducerRepository producerRepository;

    public ProducerService() {
        ProducerFileStorage producerStorage = new ProducerFileStorage();//todo create factory
        SouvenirFileStorage souvenirStorage = new SouvenirFileStorage();
        SouvenirRepository souvenirRepository = new SouvenirRepository(souvenirStorage, producerStorage);
        producerRepository = new ProducerRepository(producerStorage, souvenirRepository);
    }

    public void create(Producer producer){
        producerRepository.save(producer);
    }

    public void update(Producer producer){
        List<Souvenir> souvenirs = producer.getSouvenirs();

        for (Souvenir souvenir : souvenirs) {
            souvenir.setProducer(producer);
        }

        producerRepository.save(producer);
    }

    public void delete(Producer producer){
        producerRepository.delete(producer.getId());
    }


    public void createAll(List<Producer> producers){
        producerRepository.saveAll(producers);
    }

    public List<Producer> getAll () {
        return producerRepository.getAll();
    }

    public List<Producer> getAllWithSouvenirs () {
        return producerRepository.getProducersWithSouvenirs();
    }

    public List<Producer> getByPriceLessThan(double price) {
        return producerRepository.getByPriceLessThan(price);
    }

    public List<Producer> getBySouvenirAndProductionYear (String souvenirName, String productionYear) {
        return producerRepository.getProducersBySouvenirAndProductionYear(souvenirName, productionYear);
    }


}
