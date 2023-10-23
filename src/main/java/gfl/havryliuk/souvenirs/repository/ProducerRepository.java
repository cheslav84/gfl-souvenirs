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

public class ProducerRepository implements Repository<Producer> {//todo подумати, можливо винести всю роботу з нодами в клас Document
    private final Document<Producer> producerDocument;
    private final Document<Souvenir> souvenirDocument;
    private final SouvenirRepository souvenirRepository;
    private final Mapper mapper;

    public ProducerRepository(ProducerFileStorage producerStorage, SouvenirFileStorage souvenirStorage,
                              SouvenirRepository souvenirRepository) {
        this.producerDocument = new Document<>(producerStorage);
        this.souvenirDocument = new Document<>(souvenirStorage);
        this.souvenirRepository = souvenirRepository;
        this.mapper = Mapper.getMapper();
    }


    @Override
    public void save(Producer producer) {
        ArrayNode records = producerDocument.getRecords();
        records.add(mapper.valueToTree(producer));
        producerDocument.saveRecords(records);
    }

    public void saveAll(List<Producer> producers) {
        ArrayNode records = producerDocument.getRecords();
        for (Producer producer : producers) {
            records.add(mapper.valueToTree(producer));
        }
        producerDocument.saveRecords(records);
    }

    @Override
    public List<Producer> getAll() {
        return StreamSupport.stream(producerDocument.getSpliterator(), false)
                .map((node) -> mapper.mapEntity(node, Producer.class))
                .collect(Collectors.toList());
    }


// Вивести інформацію про виробників, чиї ціни на сувеніри менше заданої.
public List<Producer> getByPriceLessThan(double price) {

    return StreamSupport.stream(producerDocument.getSpliterator(), false)
            .map((node) -> mapper.mapEntity(node, Producer.class))
            .filter(p -> getProducersId(price).stream()
                    .map(Producer::getId)
                    .anyMatch(id -> id.equals(p.getId())))
            .distinct()
            .collect(Collectors.toList());
}

    private List<Producer> getProducersId(double price) {
        return StreamSupport.stream(souvenirDocument.getSpliterator(), false)
                .map((node) -> mapper.mapEntity(node, Souvenir.class))
                .filter(s -> s.getPrice() < price)
                .map(Souvenir::getProducer)
                .distinct()
                .collect(Collectors.toList());
    }


    @Override
    public Optional<Producer> getById(UUID id) {
        return StreamSupport.stream(producerDocument.getSpliterator(), false)
                .map((node) -> mapper.mapEntity(node, Producer.class))
                .filter(producer -> producer.getId().equals(id))
                .findAny();
    }


    @Override
    public void delete(UUID id) {//todo Видалити сувеніри
        Optional<Producer> producerToDelete = getById(id);
        if (producerToDelete.isPresent()) {
            ArrayNode producers = producerDocument.getRecords();
            Iterator<JsonNode> elements = producers.elements();
            while (elements.hasNext()) {
                JsonNode node = elements.next();
                Producer producer = mapper.mapEntity(node, Producer.class);
                if (producer.getId().equals(id)) {
                    elements.remove();
                    if (!producer.getSouvenirs().isEmpty()) {
                        souvenirRepository.deleteAllWithoutProducer(producerToDelete.get().getSouvenirs());
                    }
                    break;
                }
            }
            producerDocument.saveRecords(producers);
        }
    }





}
