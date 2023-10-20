package gfl.havryliuk.souvenirs.util.json.schema;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import gfl.havryliuk.souvenirs.entities.Producer;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class ProducerSchema {
    @JsonProperty(access = JsonProperty.Access.READ_WRITE, required = true)
    private static List<Producer> producers;


    public static JsonNode createProducersSchema() {
        JsonNode producerDocument = JsonSchemaGenerator.getSchemaGenerator().generateSchema(Producer.class);
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            objectMapper.writeValue(new File("src/main/resources/data/schema/ProducersSchema.json"), producerDocument);
        } catch (IOException e) {
            throw new RuntimeException("Producer schema creation failed.", e);
        }

        return producerDocument;
    }

}
