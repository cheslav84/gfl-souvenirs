package gfl.havryliuk.souvenirs.util.json;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import gfl.havryliuk.souvenirs.storage.FileStorage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Spliterator;



public class Document<T> {//TODO видалити параметризацію

    private final FileStorage storage;

    public Document(FileStorage storage) {
        this.storage = storage;
    }


    public void create(){
        ObjectMapper mapper = Mapper.getMapper();
        try {
            mapper.writeValue(storage.getStorage(), new ArrayList<T>());
        } catch (IOException e) {
            throw new RuntimeException("Error creating document: " + storage.getStorage().getPath(), e);
        }
    }

    public ArrayNode getRecords() {
        ObjectMapper mapper = Mapper.getMapper();
        try {
            JsonNode rootNode = mapper.readTree(storage.getStorage());
            if (rootNode.isArray()) {
                return (ArrayNode) rootNode;
            } else {
                throw new RuntimeException("Json document is not array: " + storage.getStorage().getPath());
            }
        } catch (IOException e) {
            throw new RuntimeException("Error reading document: " + storage.getStorage().getPath(), e);
        }
    }

    public void saveRecords(ArrayNode node){
        ObjectMapper mapper = Mapper.getMapper();
        try {
            mapper.writeValue(storage.getStorage(), node);
        } catch (IOException e) {
            throw new RuntimeException("Error saving document: " + storage.getStorage().getPath(), e);
        }
    }

    public Spliterator<JsonNode> getSpliterator() {
        return getRecords().spliterator();
    }





}
