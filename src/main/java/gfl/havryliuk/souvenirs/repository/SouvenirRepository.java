package gfl.havryliuk.souvenirs.repository;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import gfl.havryliuk.souvenirs.entities.Producer;
import gfl.havryliuk.souvenirs.entities.Souvenir;
import gfl.havryliuk.souvenirs.storage.ProducerFileStorage;
import gfl.havryliuk.souvenirs.storage.SouvenirFileStorage;
import gfl.havryliuk.souvenirs.util.json.Document;
import gfl.havryliuk.souvenirs.util.json.Mapper;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class SouvenirRepository implements Repository<Souvenir> {
    private final Document<Souvenir> souvenirDocument;
    private final Document<Producer> producerDocument;
    private final Mapper mapper;

    public SouvenirRepository(SouvenirFileStorage souvenirStorage, ProducerFileStorage producerStorage) {
        this.souvenirDocument = new Document<>(souvenirStorage);
        this.producerDocument = new Document<>(producerStorage);
        this.mapper = Mapper.getMapper();
    }

    @Override
    public void save(Souvenir souvenir) {
        ArrayNode document = souvenirDocument.getRecords();
        document.add(mapper.valueToTree(souvenir));
        souvenirDocument.saveRecords(document);
    }



    public void saveAll(List<Souvenir> souvenirs) {
        ArrayNode document = souvenirDocument.getRecords();
        for (Souvenir souvenir : souvenirs) {
            document.add(mapper.valueToTree(souvenir));
        }
        souvenirDocument.saveRecords(document);
    }

    @Override
    public List<Souvenir> getAll() {
        return StreamSupport.stream(souvenirDocument.getSpliterator(), false)
                .map((node) -> mapper.mapEntity(node, Souvenir.class))
                .collect(Collectors.toList());

    }


    @Override
    public Optional<Souvenir> getById(UUID id) {
        return StreamSupport.stream(souvenirDocument.getSpliterator(), false)
                .map((node) -> mapper.mapEntity(node, Souvenir.class))
                .filter(producer -> producer.getId().equals(id))
                .findAny();
    }



    @Override
    public void delete(UUID id) {
        Optional<Souvenir> souvenirToDelete = getById(id);
        if (souvenirToDelete.isPresent()) {
            ArrayNode souvenirArray = souvenirDocument.getRecords();
            removeSouvenir(id, souvenirArray);
            souvenirDocument.saveRecords(souvenirArray);

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
        ArrayNode souvenirNodes = souvenirDocument.getRecords();
        removeSouvenirs(souvenirs, souvenirNodes);
        souvenirDocument.saveRecords(souvenirNodes);

        ArrayNode producerArray = producerDocument.getRecords();
        removeAllSouvenirsIdFromProducers(souvenirs, producerArray);
        producerDocument.saveRecords(producerArray);
    }


//    void deleteAllWithoutProducer(List<Souvenir> souvenirs) {
//        ArrayNode souvenirNodes = souvenirDocument.getRecords();
//        removeSouvenirs(souvenirs, souvenirNodes);
//        souvenirDocument.saveRecords(souvenirNodes);
//    }



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


    void deleteAllWithoutProducer(List<Souvenir> souvenirs) {
        ArrayNode souvenirNodes = souvenirDocument.getRecords();
        removeSouvenirs(souvenirs, souvenirNodes);
        souvenirDocument.saveRecords(souvenirNodes);
    }

    private void removeSouvenirs(List<Souvenir> souvenirs, ArrayNode souvenirNodes) {
        List<UUID> souvenirsId = souvenirs.stream()
                .map(Souvenir::getId)
                .toList();

        Iterator<JsonNode> souvenirElements = souvenirNodes.elements();
        while (souvenirElements.hasNext()) {
            JsonNode souvenirNode = souvenirElements.next();
            Souvenir souvenir = mapper.mapEntity(souvenirNode, Souvenir.class);
            if (souvenirsId.contains(souvenir.getId())){
                souvenirElements.remove();
            }
        }
    }


}
