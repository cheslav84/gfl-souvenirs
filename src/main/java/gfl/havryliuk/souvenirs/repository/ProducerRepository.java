package gfl.havryliuk.souvenirs.repository;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import gfl.havryliuk.souvenirs.entities.Producer;
import gfl.havryliuk.souvenirs.entities.Souvenir;
import gfl.havryliuk.souvenirs.repository.storage.ProducerStorage;
import gfl.havryliuk.souvenirs.repository.storage.SouvenirStorage;
import gfl.havryliuk.souvenirs.util.json.Document;
import gfl.havryliuk.souvenirs.util.json.Mapper;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class ProducerRepository implements Repository<Producer> {
//    private final ProducerStorage producerStorage;// todo - винести storage в document
    private final Document<Producer> producersDocument;
    private final Document<Souvenir> souvenirDocument;
    private final Mapper mapper;

    public ProducerRepository(ProducerStorage producerStorage, SouvenirStorage souvenirStorage) {
        this.producersDocument = new Document<>(producerStorage);
        this.souvenirDocument = new Document<>(souvenirStorage);
        this.mapper = Mapper.getMapper();
    }

//    public ProducerRepository(ProducerStorage producerStorage) {
//        this.producersDocument = new Document<>(producerStorage);
//        this.mapper = Mapper.getMapper();
//    }


    @Override
    public void save(Producer producer) {
        ArrayNode records = producersDocument.getRecords();
        records.add(mapper.valueToTree(producer));
        producersDocument.saveRecords(records);
    }

    public void saveAll(List<Producer> producers) {
        ArrayNode records = producersDocument.getRecords();
        for (Producer producer : producers) {
            records.add(mapper.valueToTree(producer));
        }
        producersDocument.saveRecords(records);
    }

    @Override
    public List<Producer> getAll() {
        return StreamSupport.stream(producersDocument.getSpliterator(), false)
                .map((node) -> mapper.mapEntity(node, Producer.class))
                .collect(Collectors.toList());
    }


// Вивести інформацію про виробників, чиї ціни на сувеніри менше заданої.
public List<Producer> getByPriceLessThan(double price) {

    return StreamSupport.stream(producersDocument.getSpliterator(), false)
            .map((node) -> mapper.mapEntity(node, Producer.class))
            .filter(p -> getProducersId(price).stream()
                    .map(Producer::getId)
                    .anyMatch(id -> id.equals(p.getId())))
            .distinct()
            .collect(Collectors.toList());
}

    private List<Producer> getProducersId(double price) {
//        Spliterator<JsonNode> spliterator = souvenirDocument.getSpliterator();


        return StreamSupport.stream(souvenirDocument.getSpliterator(), false)
//        return StreamSupport.stream(souvenirDocument.getSpliterator(producerStorage.getStorage()), false)
                .map((node) -> mapper.mapEntity(node, Souvenir.class))
                .filter(s -> s.getPrice() < price)
                .map(Souvenir::getProducer)
                .distinct()
                .collect(Collectors.toList());
    }


    @Override
    public Optional<Producer> getById(UUID id) {
        return StreamSupport.stream(producersDocument.getSpliterator(), false)
                .map((node) -> mapper.mapEntity(node, Producer.class))
                .filter(producer -> producer.getId().equals(id))
                .findAny();
    }


    @Override
    public void delete(UUID id) {
        ArrayNode producers = producersDocument.getRecords();
        Iterator<JsonNode> elements = producers.elements();
        while (elements.hasNext()) {
            JsonNode node = elements.next();
            Producer producer = mapper.mapEntity(node, Producer.class);
            if (producer.getId().equals(id)) {
                elements.remove();
                break;
            }
        }
        producersDocument.saveRecords(producers);
    }


}
