package gfl.havryliuk.souvenirs.util.json;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Document<T> {

    public void create(File file){
        ObjectMapper mapper = Mapper.getObjectMapper();
        try {
            mapper.writeValue(file, new ArrayList<T>());
        } catch (IOException e) {
            throw new RuntimeException("Error creating document: " + file.getPath(), e);
        }
    }


    public ArrayNode getDocument (File file) {
        ObjectMapper mapper = Mapper.getObjectMapper();
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


    public void saveDocument(File file, ArrayNode node){
        ObjectMapper mapper = Mapper.getObjectMapper();
        try {
            mapper.writeValue(file, node);
        } catch (IOException e) {
            throw new RuntimeException("Error saving document: " + file.getPath(), e);
        }
    }





}
