package gfl.havryliuk.souvenirs.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import gfl.havryliuk.souvenirs.entities.Producer;
import gfl.havryliuk.souvenirs.util.json.Document;
import gfl.havryliuk.souvenirs.util.json.Mapper;

import java.io.File;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

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
        document.add(mapper.valueToTree(new Producer(producer)));
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
        return null;
    }

    @Override
    public Optional<Producer> getById(UUID id) {
        return Optional.empty();
    }

    @Override
    public void delete(UUID id) {

    }
}
