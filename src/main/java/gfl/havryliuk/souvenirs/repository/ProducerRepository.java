package gfl.havryliuk.souvenirs.repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import gfl.havryliuk.souvenirs.entities.Producer;
import gfl.havryliuk.souvenirs.entities.Souvenir;
import gfl.havryliuk.souvenirs.util.json.Document;
import gfl.havryliuk.souvenirs.util.json.Mapper;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class ProducerRepository implements Repository<Producer> {
    private final File file;
    private final Document<Producer> producersDocument;
    private final ObjectMapper mapper;

    public ProducerRepository(File file) {
        this.file = file;
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
//        List<Producer> producers;
//        try {
//            producers = mapper.readValue(file, new TypeReference<>(){});
//        } catch (IOException e) {
//            throw new RuntimeException("Error reading document: " + file.getPath(), e);
//        }
//        return producers;


        return StreamSupport.stream(getSpliterator(), false)
                .map(this::mapProducer)
                .collect(Collectors.toList());
    }


//    public List<Producer> getBy() {
//
//         Document<Souvenir> souvenirsDocument = new Document<>();
//
//
//        ArrayNode producers = producersDocument.getDocument(file);
//
//        return StreamSupport.stream(producers.spliterator(), false)
//                .map(this::mapProducer)
//                .collect(Collectors.toList());
//
////        boolean result = s.map(entry -> entry.get("color"))
////                .filter(Objects::nonNull)
////                .anyMatch(color -> "green".equals(color.asText()));
//
//
//    }



    @Override
    public Optional<Producer> getById(UUID id) {
        return StreamSupport.stream(getSpliterator(), false)
                .map(this::mapProducer)
                .filter(producer -> producer.getId().equals(id))
                .findAny();
    }



    @Override
    public void delete(UUID id) {
        ArrayNode producers = producersDocument.getDocument(file);
        Iterator<JsonNode> elements = producers.elements();
        while (elements.hasNext()) {
            JsonNode element = elements.next();
            Producer producer = mapProducer(element);
            if (producer.getId().equals(id)) {
                elements.remove();
                break;
            }
        }
        producersDocument.saveDocument(file, producers);
    }

    private Spliterator<JsonNode> getSpliterator() {
        return producersDocument.getDocument(file).spliterator();
    }


    private Producer mapProducer(JsonNode node) {
        try {
            return mapper.treeToValue(node, Producer.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
