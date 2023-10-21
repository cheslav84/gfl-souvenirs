package gfl.havryliuk.souvenirs.repository;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import gfl.havryliuk.souvenirs.entities.Producer;
import gfl.havryliuk.souvenirs.entities.Souvenir;
import gfl.havryliuk.souvenirs.util.StorageProperties;
import gfl.havryliuk.souvenirs.util.json.Document;
import gfl.havryliuk.souvenirs.util.json.Mapper;

import java.io.File;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class ProducerRepository implements Repository<Producer> {
    private final StorageProperties storageProperties;
    private final File file;
    private final Document<Producer> producersDocument;
    private final Mapper mapper;

    public ProducerRepository(StorageProperties storageProperties) {
        this.storageProperties = storageProperties;
        this.file = new File(storageProperties.getProducersPathStorage());//todo здається напрошується стратегія
        this.producersDocument = new Document<>();
        this.mapper = Mapper.getObjectMapper();
    }


    @Override
    public void save(Producer producer) {
        ArrayNode document = producersDocument.getDocument(file);
        document.add(mapper.valueToTree(new Producer(producer)));//todo подумати, можливо замістити на proxy
        producersDocument.saveDocument(file, document);
    }

    public void saveAll(List<Producer> producers) {
        ArrayNode document = producersDocument.getDocument(file);
        for (Producer producer : producers) {
            document.add(mapper.valueToTree(producer));
        }
        producersDocument.saveDocument(file, document);
    }

    @Override
    public List<Producer> getAll() {
        return StreamSupport.stream(getSpliterator(file), false)
                .map((node) -> mapper.mapEntity(node, Producer.class))
//                .map(this::mapProducer)
                .collect(Collectors.toList());
    }


// Вивести інформацію про виробників, чиї ціни на сувеніри менше заданої.
    public List<Producer> getByPriceLessThan(double price) {
        File souvenirsStorage = new File(storageProperties.getSouvenirsPathStorage());

        ArrayNode producers = producersDocument.getDocument(file);
        ArrayNode souvenirs = producersDocument.getDocument(souvenirsStorage);


        StreamSupport.stream(getSpliterator(souvenirsStorage), false)
                .map((node) -> mapper.mapEntity(node, Souvenir.class))
                .collect(Collectors.toList());


//        Iterator<JsonNode> elements = producers.elements();
//        while (elements.hasNext()) {
//            JsonNode element = elements.next();
//            Producer producer = mapProducer(element);
//            if (producer.getId().equals(id)) {
//                elements.remove();
//                break;
//            }
//        }





//        Stream<JsonNode> stream = StreamSupport.stream(getSpliterator(souvenirsStorage), false);
//
//        return StreamSupport.stream(getSpliterator(file), false)
//                .flatMap(this::mapProducer, stream)
//                .collect(Collectors.toList());

//        boolean result = s.map(entry -> entry.get("color"))
//                .filter(Objects::nonNull)
//                .anyMatch(color -> "green".equals(color.asText()));

        return null;

    }

    @Override
    public Optional<Producer> getById(UUID id) {
        return StreamSupport.stream(getSpliterator(file), false)
                .map((node) -> mapper.mapEntity(node, Producer.class))
                .filter(producer -> producer.getId().equals(id))
                .findAny();
    }



    @Override
    public void delete(UUID id) {
        ArrayNode producers = producersDocument.getDocument(file);
        Iterator<JsonNode> elements = producers.elements();
        while (elements.hasNext()) {
            JsonNode node = elements.next();
            Producer producer = mapper.mapEntity(node, Producer.class);
            if (producer.getId().equals(id)) {
                elements.remove();
                break;
            }
        }
        producersDocument.saveDocument(file, producers);
    }

    private Spliterator<JsonNode> getSpliterator(File file) {
        return producersDocument.getDocument(file).spliterator();//todo повторюється в класах
    }


}
