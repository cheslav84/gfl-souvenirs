package gfl.havryliuk.souvenirs.repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import gfl.havryliuk.souvenirs.entities.Producer;
import gfl.havryliuk.souvenirs.entities.Souvenir;
import gfl.havryliuk.souvenirs.util.json.Document;
import gfl.havryliuk.souvenirs.util.json.Mapper;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.StreamSupport;

public class SouvenirRepository implements Repository<Souvenir> {
    private final File file;
    private final Document<Souvenir> souvenirDocument;
    private final ObjectMapper mapper;

    public SouvenirRepository(File file) {
        this.file = file;
        this.souvenirDocument = new Document<>();
        this.mapper = Mapper.getObjectMapper();
    }

    @Override
    public void save(Souvenir souvenir) {

        ArrayNode document = souvenirDocument.getDocument(file);
        document.add(mapper.valueToTree(new Souvenir(souvenir)));
        souvenirDocument.saveDocument(file, document);
    }

    public void saveAll(List<Souvenir> souvenirs) {
        ArrayNode document = souvenirDocument.getDocument(file);
        for (Souvenir souvenir : souvenirs) {
            document.add(mapper.valueToTree(souvenir));
        }
        souvenirDocument.saveDocument(file, document);
    }

    @Override
    public List<Souvenir> getAll() {
        List<Souvenir> souvenirs;
        try {
            souvenirs = mapper.readValue(file, new TypeReference<>(){});
        } catch (IOException e) {
            throw new RuntimeException("Error reading document: " + file.getPath(), e);
        }
        return souvenirs;
    }



    @Override
    public Optional<Souvenir> getById(UUID id) {
        ArrayNode souvenirs = souvenirDocument.getDocument(file);
        return StreamSupport.stream(souvenirs.spliterator(), false)
                .map(this::mapProducer)
                .filter(producer -> producer.getId().equals(id))
                .findAny();
    }



    @Override
    public void delete(UUID id) {
        ArrayNode souvenirs = souvenirDocument.getDocument(file);
        Iterator<JsonNode> elements = souvenirs.elements();
        while (elements.hasNext()) {
            JsonNode element = elements.next();
            Souvenir souvenir = mapProducer(element);
            if (souvenir.getId().equals(id)) {
                elements.remove();
                break;
            }
        }
        souvenirDocument.saveDocument(file, souvenirs);
    }



    private Souvenir mapProducer(JsonNode node) {
        try {
            return mapper.treeToValue(node, Souvenir.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
