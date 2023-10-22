package gfl.havryliuk.souvenirs.repository;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import gfl.havryliuk.souvenirs.entities.Souvenir;
import gfl.havryliuk.souvenirs.repository.storage.SouvenirStorage;
import gfl.havryliuk.souvenirs.util.json.Document;
import gfl.havryliuk.souvenirs.util.json.Mapper;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class SouvenirRepository implements Repository<Souvenir> {
    private final Document<Souvenir> souvenirDocument;
    private final Mapper mapper;

    public SouvenirRepository(SouvenirStorage souvenirStorage) {
        this.souvenirDocument = new Document<>(souvenirStorage);
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
    public void delete(UUID id) {
        ArrayNode souvenirs = souvenirDocument.getRecords();
        Iterator<JsonNode> elements = souvenirs.elements();
        while (elements.hasNext()) {
            JsonNode node = elements.next();
            Souvenir souvenir = mapper.mapEntity(node, Souvenir.class);
            if (souvenir.getId().equals(id)) {
                elements.remove();
                break;
            }
        }
        souvenirDocument.saveRecords(souvenirs);
    }


}
