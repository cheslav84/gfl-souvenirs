package gfl.havryliuk.souvenirs.repository;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import gfl.havryliuk.souvenirs.entities.Producer;
import gfl.havryliuk.souvenirs.entities.Souvenir;
import gfl.havryliuk.souvenirs.storage.SouvenirFileStorage;
import gfl.havryliuk.souvenirs.util.json.Document;
import gfl.havryliuk.souvenirs.util.json.Mapper;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class SouvenirRepository implements Repository<Souvenir> {
    private final Mapper mapper;
    private final ProducerRepository producerRepository;
    private final Document<Souvenir> souvenirDocument;

    public SouvenirRepository(SouvenirFileStorage souvenirStorage, ProducerRepository producerRepository) {
        this.mapper = Mapper.getMapper();
        this.producerRepository = producerRepository;
        this.souvenirDocument = new Document<>(souvenirStorage);
    }

    Document<Souvenir> getSouvenirDocument() {
        return souvenirDocument;
    }

    @Override
    public void save(Souvenir souvenir) {
        producerRepository.isAllProducersStored(souvenir.getProducer().getId());
        ArrayNode souvenirArray = souvenirDocument.getRecords();
        removeSouvenir(souvenir.getId(), souvenirArray);
        souvenirArray.add(mapper.valueToTree(souvenir));
        souvenirDocument.saveRecords(souvenirArray);
    }


    public void saveAll(List<Souvenir> souvenirs) {
        UUID[] producerIdToStore = souvenirs.stream()
                .map(s -> s.getProducer().getId())
                .toArray(UUID[]::new);
        producerRepository.isAllProducersStored(producerIdToStore);
        ArrayNode souvenirArray = souvenirDocument.getRecords();
        removeSouvenirs(souvenirs, souvenirArray);//todo подумати, може не видаляти а замінювати,
        for (Souvenir souvenir : souvenirs) {
            souvenirArray.add(mapper.valueToTree(souvenir));
        }
        souvenirDocument.saveRecords(souvenirArray);
    }


    @Override
    public Optional<Souvenir> getById(UUID id) {
        return StreamSupport.stream(souvenirDocument.getSpliterator(), false)
                .map((node) -> mapper.mapEntity(node, Souvenir.class))
                .filter(souvenir -> souvenir.getId().equals(id))
                .findAny();
    }


    @Override
    public List<Souvenir> getAll() {
        return StreamSupport.stream(souvenirDocument.getSpliterator(), false)
                .map((node) -> mapper.mapEntity(node, Souvenir.class))
                .collect(Collectors.toList());

    }


    public List<Souvenir> getByProducer(Producer producer) {
        return StreamSupport.stream(souvenirDocument.getSpliterator(), false)
                .map((node) -> mapper.mapEntity(node, Souvenir.class))
                .filter(s -> s.getProducer().getId().equals(producer.getId()))
                .collect(Collectors.toList());
    }

    public List<Souvenir> getByCountry(String country) {
        List<UUID> producersIdByCountry = producerRepository.getProducersIdByCountry(country);

        return StreamSupport.stream(souvenirDocument.getSpliterator(), false)
                .map((node) -> mapper.mapEntity(node, Souvenir.class))
                .filter(s -> producersIdByCountry.contains(s.getProducer().getId()))
                .distinct()
                .collect(Collectors.toList());
    }






    @Override
    public void delete(UUID id) {
        Optional<Souvenir> souvenirToDelete = getById(id);
        if (souvenirToDelete.isPresent()) {
            ArrayNode souvenirArray = souvenirDocument.getRecords();
            removeSouvenir(id, souvenirArray);
            souvenirDocument.saveRecords(souvenirArray);
            Document<Producer> producerDocument = producerRepository.getProducerDocument();
            ArrayNode producerArray = producerDocument.getRecords();
            removeSouvenirIdFromProducers(id, producerArray);
            producerDocument.saveRecords(producerArray);
        }
    }



    private void removeSouvenir(UUID id, ArrayNode souvenirArray) {
        Iterator<JsonNode> souvenirElements = souvenirArray.elements();
        while (souvenirElements.hasNext()) {
            JsonNode souvenirNode = souvenirElements.next();
            Souvenir souvenir = mapper.mapEntity(souvenirNode, Souvenir.class);
            if (souvenir.getId().equals(id)) {
                souvenirElements.remove();
                break;
            }
        }
    }

    private void removeSouvenirIdFromProducers(UUID id, ArrayNode producerArray) {
        Iterator<JsonNode> producerElements = producerArray.elements();
        while (producerElements.hasNext()) {
            JsonNode producerNode = producerElements.next();
            ArrayNode souvenirsNode = (ArrayNode) producerNode.path("souvenirs");
            removeSouvenir(id, souvenirsNode);
        }
    }


    public void deleteAll(List<Souvenir> souvenirs) {
        ArrayNode souvenirArray = souvenirDocument.getRecords();
        removeSouvenirs(souvenirs, souvenirArray);
        souvenirDocument.saveRecords(souvenirArray);

        Document<Producer> producerDocument = producerRepository.getProducerDocument();

        ArrayNode producerArray = producerDocument.getRecords();
        removeAllSouvenirsIdFromProducers(souvenirs, producerArray);
        producerDocument.saveRecords(producerArray);
    }

    void deleteAllWithoutProducer(List<Souvenir> souvenirs) {
        ArrayNode souvenirArray = souvenirDocument.getRecords();
        removeSouvenirs(souvenirs, souvenirArray);
        souvenirDocument.saveRecords(souvenirArray);
    }



    private void removeAllSouvenirsIdFromProducers(List<Souvenir> souvenirs, ArrayNode producerArray) {
        List<UUID> producersIdDeletedSouvenirs = souvenirs.stream()
                .map(s -> s.getProducer().getId())
                .toList();

        List<UUID> souvenirsIdToDeleted = souvenirs.stream()
                .map(Souvenir::getId)
                .toList();

        Iterator<JsonNode> producerElements = producerArray.elements();

        while (producerElements.hasNext()) {
            JsonNode producerNode = producerElements.next();
            Producer producer = mapper.mapEntity(producerNode, Producer.class);
            if (producersIdDeletedSouvenirs.contains(producer.getId())) {
                removeAllSouvenirsIdFromOneProducer(producerNode, producer, souvenirsIdToDeleted);
            }
        }
    }

    private void removeAllSouvenirsIdFromOneProducer(JsonNode producerNode, Producer producer,
                                                     List<UUID> souvenirsIdToDeleted) {
        ArrayNode souvenirsNode = (ArrayNode) producerNode.path("souvenirs");
        for (Souvenir souvenir: producer.getSouvenirs()) {
            if (souvenirsIdToDeleted.contains(souvenir.getId())){
                removeSouvenir(souvenir.getId(), souvenirsNode);
            }

        }
    }


    private void removeSouvenirs(List<Souvenir> souvenirs, ArrayNode souvenirArray) {
        List<UUID> souvenirsId = souvenirs.stream()
                .map(Souvenir::getId)
                .toList();

        Iterator<JsonNode> souvenirElements = souvenirArray.elements();
        while (souvenirElements.hasNext()) {
            JsonNode souvenirNode = souvenirElements.next();
            Souvenir souvenir = mapper.mapEntity(souvenirNode, Souvenir.class);
            if (souvenirsId.contains(souvenir.getId())){
                souvenirElements.remove();
            }
        }
    }

//    boolean exists(UUID id) {
//        return StreamSupport.stream(souvenirDocument.getSpliterator(), false)
//                .map((node) -> mapper.mapEntity(node, Souvenir.class))
//                .anyMatch(producer -> producer.getId().equals(id));
//    }


}
