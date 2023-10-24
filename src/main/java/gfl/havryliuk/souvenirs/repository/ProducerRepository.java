package gfl.havryliuk.souvenirs.repository;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import gfl.havryliuk.souvenirs.entities.Producer;
import gfl.havryliuk.souvenirs.entities.Souvenir;
import gfl.havryliuk.souvenirs.storage.ProducerFileStorage;
import gfl.havryliuk.souvenirs.util.json.Document;
import gfl.havryliuk.souvenirs.util.json.Mapper;

import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class ProducerRepository implements Repository<Producer> {//todo подумати, можливо винести всю роботу з нодами в клас Document
    private final Mapper mapper;
    private final SouvenirRepository souvenirRepository;
    private final Document<Producer> producerDocument;
//    private final Document<Souvenir> souvenirDocument;

    public ProducerRepository(ProducerFileStorage producerStorage, SouvenirRepository souvenirRepository) {
        this.mapper = Mapper.getMapper();
        this.souvenirRepository = souvenirRepository;
        this.producerDocument = new Document<>(producerStorage);
//        this.souvenirDocument = souvenirRepository.getSouvenirDocument();
    }

    Document<Producer> getProducerDocument() {
        return producerDocument;
    }

    @Override
    public void save(Producer producer) {
        ArrayNode producerArray = producerDocument.getRecords();
        removeProducer(producer.getId(), producerArray);
        producerArray.add(mapper.valueToTree(producer));
        producerDocument.saveRecords(producerArray);
        souvenirRepository.saveAll(producer.getSouvenirs());
    }

    public void saveAll(List<Producer> producers) {
        ArrayNode producerArray = producerDocument.getRecords();
        List<Souvenir> souvenirs = new ArrayList<>();
        removeProducers(producers, producerArray);
        for (Producer producer : producers) {
            producerArray.add(mapper.valueToTree(producer));
            souvenirs.addAll(producer.getSouvenirs());
        }
        producerDocument.saveRecords(producerArray);
        souvenirRepository.saveAll(souvenirs);
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
        return StreamSupport.stream(souvenirRepository.getSouvenirDocument().getSpliterator(), false)
                .map((node) -> mapper.mapEntity(node, Souvenir.class))
                .filter(s -> s.getPrice() < price)
                .map(Souvenir::getProducer)
                .distinct()
                .collect(Collectors.toList());
    }


    // Вивести інформацію по всіх виробниках та, для кожного виробника вивести інформацію про всі сувеніри, які він виробляє.
    public List<Producer> getProducersWithSouvenirs() {
        Map<UUID, List<Souvenir>> producerSouvenirs = StreamSupport
                .stream(souvenirRepository.getSouvenirDocument().getSpliterator(), false)
                .map((node) -> mapper.mapEntity(node, Souvenir.class))
                .collect(Collectors.groupingBy(s -> s.getProducer().getId()));
        List<Producer> producers = getAll();
        producers.forEach(p -> p.setSouvenirs(producerSouvenirs.get(p.getId())));
        return producers;
    }


    //Вивести інформацію про виробників заданого сувеніру, виробленого у заданому року.
    public List<Producer> getProducersBySouvenirAndProductionYear(Souvenir souvenir, LocalDateTime productionDate) {
//        return StreamSupport.stream(souvenirRepository.getSouvenirDocument().getSpliterator(), false)
//                .map((node) -> mapper.mapEntity(node, Souvenir.class))
//                .filter(s -> s.getPrice() < price)
//                .map(Souvenir::getProducer)
//                .distinct()
//                .collect(Collectors.toList());
        return null;
    }



    @Override
    public Optional<Producer> getById(UUID id) {
        return StreamSupport.stream(producerDocument.getSpliterator(), false)
                .map((node) -> mapper.mapEntity(node, Producer.class))
                .filter(producer -> producer.getId().equals(id))
                .findAny();
    }


    @Override
    public void delete(UUID id) {
        Optional<Producer> producerToDelete = getById(id);
        if (producerToDelete.isPresent()) {
            ArrayNode producers = producerDocument.getRecords();
            Iterator<JsonNode> elements = producers.elements();
            while (elements.hasNext()) {
                JsonNode producerNode = elements.next();
                Producer producer = mapper.mapEntity(producerNode, Producer.class);
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

    boolean isAllProducersStored(UUID... idArr) {
        List<UUID> uuidList = new ArrayList<>(Arrays.stream(idArr).toList());
        List<UUID> storedProducersId = StreamSupport.stream(producerDocument.getSpliterator(), false)
                .map((node) -> mapper.mapEntity(node, Producer.class))
                .map(Producer::getId)
                .toList();

        for (UUID producerId : uuidList) {
            if (!storedProducersId.contains(producerId)) {
                throw new IllegalStateException("Producer hasn't saved in storage. Save producer first. Producer id: " + producerId);//todo не зовсім вірно подумати як переробити
            }
        }
        return true;

    }


    private void removeProducer(UUID id, ArrayNode producerArray) {
        Iterator<JsonNode> producerElements = producerArray.elements();
        while (producerElements.hasNext()) {
            JsonNode producerNode = producerElements.next();
            Producer producer = mapper.mapEntity(producerNode, Producer.class);
            if (producer.getId().equals(id)) {
                producerElements.remove();
                break;
            }
        }
    }


    private void removeProducers(List<Producer> producers, ArrayNode producerArray) {
        List<UUID> producersId = producers.stream()
                .map(Producer::getId)
                .toList();

        Iterator<JsonNode> producerElements = producerArray.elements();
        while (producerElements.hasNext()) {
            JsonNode producerNode = producerElements.next();
            Producer producer = mapper.mapEntity(producerNode, Producer.class);
            if (producersId.contains(producer.getId())) {
                producerElements.remove();
            }
        }
    }

    List<UUID> getProducersIdByCountry(String country) {
        return StreamSupport.stream(producerDocument.getSpliterator(), false)
                .map((node) -> mapper.mapEntity(node, Producer.class))
                .filter(p -> p.getCountry().equals(country))
                .map(Producer::getId)
                .collect(Collectors.toList());

    }

}
