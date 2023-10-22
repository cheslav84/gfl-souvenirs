package gfl.havryliuk.souvenirs.util.json;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import gfl.havryliuk.souvenirs.repository.storage.Storage;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Spliterator;



public class Document<T> {

    private Storage storage;

    public Document(Storage storage) {
        this.storage = storage;
    }


    public void create(File file){
        ObjectMapper mapper = Mapper.getMapper();
        try {
            mapper.writeValue(file, new ArrayList<T>());
        } catch (IOException e) {
            throw new RuntimeException("Error creating document: " + file.getPath(), e);
        }
    }


    public ArrayNode getRecords(File file) {
        ObjectMapper mapper = Mapper.getMapper();
        try {
            JsonNode rootNode = mapper.readTree(file);
            if (rootNode.isArray()) {
                return (ArrayNode) rootNode;
            } else {
                throw new RuntimeException("Json document is not array: " + file.getPath());
            }
        } catch (IOException e) {
            throw new RuntimeException("Error reading document: " + file.getPath(), e);
        }
    }


    public void saveRecords(File file, ArrayNode node){
        ObjectMapper mapper = Mapper.getMapper();
        try {
            mapper.writeValue(file, node);
        } catch (IOException e) {
            throw new RuntimeException("Error saving document: " + file.getPath(), e);
        }
    }


    public Spliterator<JsonNode> getSpliterator(File file) {

        return this.getRecords(file).spliterator();//todo повторюється в класах
    }






}
