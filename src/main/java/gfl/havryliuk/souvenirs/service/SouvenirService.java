package gfl.havryliuk.souvenirs.service;

import gfl.havryliuk.souvenirs.entities.Producer;
import gfl.havryliuk.souvenirs.entities.Souvenir;
import gfl.havryliuk.souvenirs.entities.dto.SouvenirsGroupedByProductionYearDto;
import gfl.havryliuk.souvenirs.repository.ProducerRepository;
import gfl.havryliuk.souvenirs.repository.SouvenirRepository;
import gfl.havryliuk.souvenirs.storage.ProducerFileStorage;
import gfl.havryliuk.souvenirs.storage.SouvenirFileStorage;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class SouvenirService {

    private final ProducerRepository producerRepository;

    private final SouvenirRepository souvenirRepository;

    public SouvenirService() {
        ProducerFileStorage producerStorage = new ProducerFileStorage();//todo create factory
        SouvenirFileStorage souvenirStorage = new SouvenirFileStorage();
        souvenirRepository = new SouvenirRepository(souvenirStorage, producerStorage);
        producerRepository = new ProducerRepository(producerStorage, souvenirRepository);
    }

    public List<Souvenir> getAll(){
        return souvenirRepository.getAll();
    }

    public List<Souvenir> getByProducerNameAndCountry(String name, String country){
        return souvenirRepository.getByProducerNameAndCountry(name, country);
    }

    public List<Souvenir> getByProducerCountry(String country){
        return souvenirRepository.getByProducerCountry(country);
    }

//    public Map<Integer,List<Souvenir>> getGroupedByProductionYear(){
//        return souvenirRepository.getSouvenirsGropedByProductionYear();
//    }


    public List<SouvenirsGroupedByProductionYearDto> getGroupedByProductionYear(){


//        Map<Integer, List<Souvenir>> souvenirsGropedByProductionYear = souvenirRepository.getSouvenirsGropedByProductionYear();

//        List<SouvenirsGroupedByProductionYearDto> souvenirList = new ArrayList<>();
//        for (Map.Entry<Integer, List<Souvenir>> entry : souvenirsGropedByProductionYear.entrySet()) {
//            SouvenirsGroupedByProductionYearDto souvenirs = new SouvenirsGroupedByProductionYearDto();
//            souvenirs.setProductionYear(entry.getKey());
//            souvenirs.setSouvenirs(entry.getValue());
//            souvenirList.add(souvenirs);
//        }

        return souvenirRepository.getSouvenirsGropedByProductionYear().entrySet().stream()
                .map(entry -> {
                    SouvenirsGroupedByProductionYearDto souvenirs = new SouvenirsGroupedByProductionYearDto();
                    souvenirs.setProductionYear(entry.getKey());
                    souvenirs.setSouvenirs(entry.getValue());
                    return souvenirs;
                })
                .collect(Collectors.toList());

//        return souvenirList;
    }

    public void update(Souvenir souvenir) {
        souvenirRepository.save(souvenir);
    }

    public void create(Souvenir souvenir) {
        Producer producer = souvenir.getProducer();
        List<Souvenir> souvenirs = producer.getSouvenirs();
        for (Souvenir item : souvenirs) {
            item.setProducer(producer);
        }
        souvenirs.add(souvenir);
        producerRepository.save(producer);
    }

    public void delete(Souvenir souvenir) {
        souvenirRepository.delete(souvenir.getId());
    }


}
