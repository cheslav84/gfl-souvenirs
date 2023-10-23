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
    public void delete(UUID id) {//todo Видалити id у виробника
        Optional<Souvenir> souvenirToDelete = getById(id);
        if (souvenirToDelete.isPresent()) {
            ArrayNode souvenirs = souvenirDocument.getRecords();
            removeSouvenir(id, souvenirs);
            removeSouvenirIdFromProducers(id, souvenirs);
        }
    }

    private void removeSouvenirIdFromProducers(UUID id, ArrayNode souvenirs) {
        souvenirDocument.saveRecords(souvenirs);
        ArrayNode producers = producerDocument.getRecords();
        Iterator<JsonNode> producerElements = producers.elements();
        while (producerElements.hasNext()) {
            JsonNode producer = producerElements.next();
            ArrayNode souvenirsNode = (ArrayNode) producer.path("souvenirs");
            removeSouvenir(id, souvenirsNode);
        }
        producerDocument.saveRecords(producers);
    }

    private void removeSouvenir(UUID id, ArrayNode souvenirs) {
        Iterator<JsonNode> souvenirElements = souvenirs.elements();
        while (souvenirElements.hasNext()) {
            JsonNode node = souvenirElements.next();
            Souvenir souvenir = mapper.mapEntity(node, Souvenir.class);
            if (souvenir.getId().equals(id)) {
                souvenirElements.remove();
                break;
            }
        }
    }


    public void deleteAll(List<Souvenir> items) {//todo перевірити чи залишилось id у виробників. Якщо так то видалити
        ArrayNode souvenirs = souvenirDocument.getRecords();
        Iterator<JsonNode> elements = souvenirs.elements();
        while (elements.hasNext()) {
            JsonNode node = elements.next();
            Souvenir souvenir = mapper.mapEntity(node, Souvenir.class);
            if (items.contains(souvenir)){
                elements.remove();// todo подумати над тим як видаляти документи через Spliterator, так швидше
            }
        }
        souvenirDocument.saveRecords(souvenirs);

//        ArrayNode document = souvenirDocument.getRecords();
//        for (Souvenir souvenir : items) {
//            document.removeAll(mapper.valueToTree(souvenir));
//        }
//        souvenirDocument.saveRecords(document);
    }



}
