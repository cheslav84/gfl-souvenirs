package gfl.havryliuk.souvenirs.repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import gfl.havryliuk.souvenirs.entities.Souvenir;
import gfl.havryliuk.souvenirs.util.StorageProperties;
import gfl.havryliuk.souvenirs.util.json.Document;
import gfl.havryliuk.souvenirs.util.json.Mapper;

import java.io.File;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class SouvenirRepository implements Repository<Souvenir> {
    private final StorageProperties storageProperties;
    private final File file;
    private final Document<Souvenir> souvenirDocument;
    private final Mapper mapper;

    public SouvenirRepository(StorageProperties storageProperties) {
        this.storageProperties = storageProperties;
        this.file = new File(storageProperties.getSouvenirsPathStorage());
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
        return StreamSupport.stream(getSpliterator(), false)
                .map((node) -> mapper.mapEntity(node, Souvenir.class))
                .collect(Collectors.toList());

    }


    @Override
    public Optional<Souvenir> getById(UUID id) {
        return StreamSupport.stream(getSpliterator(), false)
                .map((node) -> mapper.mapEntity(node, Souvenir.class))
                .filter(producer -> producer.getId().equals(id))
                .findAny();
    }



    @Override
    public void delete(UUID id) {
        ArrayNode souvenirs = souvenirDocument.getDocument(file);
        Iterator<JsonNode> elements = souvenirs.elements();
        while (elements.hasNext()) {
            JsonNode node = elements.next();
            Souvenir souvenir = mapper.mapEntity(node, Souvenir.class);
            if (souvenir.getId().equals(id)) {
                elements.remove();
                break;
            }
        }
        souvenirDocument.saveDocument(file, souvenirs);
    }

    private Spliterator<JsonNode> getSpliterator() {
        return souvenirDocument.getDocument(file).spliterator();
    }


}
