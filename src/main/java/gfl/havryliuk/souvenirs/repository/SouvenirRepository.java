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
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class SouvenirRepository implements Repository<Souvenir> {
    private final Mapper mapper;
    private final Document<Souvenir> souvenirDocument;
    private final Document<Producer> producerDocument;

    public SouvenirRepository(SouvenirFileStorage souvenirStorage, ProducerFileStorage producerStorage) {
        this.mapper = Mapper.getMapper();
        this.souvenirDocument = new Document<>(souvenirStorage);
        this.producerDocument = new Document<>(producerStorage);
    }

    Document<Souvenir> getSouvenirDocument() {
        return souvenirDocument;
    }

    @Override
    public void save(Souvenir souvenir) {
        checkIfAllProducersSaved(souvenir.getProducer().getId());
        ArrayNode souvenirArray = souvenirDocument.getRecords();
        removeSouvenir(souvenir.getId(), souvenirArray);
        souvenirArray.add(mapper.valueToTree(souvenir));
        updateSouvenir(souvenir, souvenirArray);
        souvenirDocument.saveRecords(souvenirArray);
    }


    public void saveAll(List<Souvenir> souvenirs) {
        UUID[] producerIdToStore = souvenirs.stream()
                .map(s -> s.getProducer().getId())
                .toArray(UUID[]::new);

        checkIfAllProducersSaved(producerIdToStore);
        ArrayNode souvenirArray = souvenirDocument.getRecords();

        for (Souvenir souvenir : souvenirs) {
            updateSouvenir(souvenir, souvenirArray);
        }
        souvenirDocument.saveRecords(souvenirArray);
    }

    private void updateSouvenir(Souvenir souvenir, ArrayNode souvenirArray) {
        Optional<Souvenir> saved = getById(souvenir.getId());
        if (saved.isPresent()){
            if (souvenir.getName() == null) {
                souvenir.setName(saved.get().getName());
            }
            if (souvenir.getPrice() == 0) {
                souvenir.setPrice(saved.get().getPrice());
            }
            if (souvenir.getProductionDate() == null) {
                souvenir.setProductionDate(saved.get().getProductionDate());
            }
            if (souvenir.getProducer() == null) {
                souvenir.setProducer(saved.get().getProducer());
            }
            removeSouvenir(souvenir.getId(), souvenirArray);
        }
        souvenirArray.add(mapper.valueToTree(souvenir));
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


    public List<Souvenir> getByProducerNameAndCountry(String name, String country) {
        return findSouvenirs(findProducersIdByNameAndCountry(name, country));
    }

    public List<Souvenir> getByProducerCountry(String country) {
        return findSouvenirs(findProducersIdByCountry(country));
    }


    private List<UUID> findProducersIdByNameAndCountry(String name, String country) {
        return findProducersId(filterByNameAndCountry(name, country));
    }

    private List<UUID> findProducersIdByCountry(String country) {
        return findProducersId(filterByCountry(country));
    }

    private static Predicate<Producer> filterByNameAndCountry(String name, String country) {
        return p -> p.getName().equalsIgnoreCase(name) && p.getCountry().equalsIgnoreCase(country);
    }

    private static Predicate<Producer> filterByCountry(String country) {
        return p -> p.getCountry().equalsIgnoreCase(country);
    }


    private List<Souvenir> findSouvenirs(List<UUID> producersId) {
        return StreamSupport.stream(souvenirDocument.getSpliterator(), false)
                .map((node) -> mapper.mapEntity(node, Souvenir.class))
                .filter(s -> producersId.contains(s.getProducer().getId()))
                .distinct()
                .collect(Collectors.toList());
    }


    private List<UUID> findProducersId(Predicate<Producer> searchConditions) {
        return StreamSupport.stream(producerDocument.getSpliterator(), false)
                .map((node) -> mapper.mapEntity(node, Producer.class))
                .filter(searchConditions)
                .map(Producer::getId)
                .collect(Collectors.toList());
    }


    public Map<Integer, List<Souvenir>> getSouvenirsGropedByProductionYear() {
        return StreamSupport.stream(souvenirDocument.getSpliterator(), false)
                .map((node) -> mapper.mapEntity(node, Souvenir.class))
                .collect(Collectors.groupingBy(s -> s.getProductionDate().getYear()));
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
        ArrayNode souvenirArray = souvenirDocument.getRecords();
        removeSouvenirs(souvenirs, souvenirArray);
        souvenirDocument.saveRecords(souvenirArray);
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


    private void checkIfAllProducersSaved(UUID... idArr) {
        List<UUID> uuidList = new ArrayList<>(Arrays.stream(idArr).toList());
        List<UUID> storedProducersId = StreamSupport.stream(producerDocument.getSpliterator(), false)
                .map((node) -> mapper.mapEntity(node, Producer.class))
                .map(Producer::getId)
                .toList();

        for (UUID producerId : uuidList) {
            if (!storedProducersId.contains(producerId)) {
                throw new IllegalStateException("Producer hasn't saved in storage. Save producer first. Producer id: "
                        + producerId);
            }
        }
    }



}
