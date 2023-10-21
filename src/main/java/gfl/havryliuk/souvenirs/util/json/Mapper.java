package gfl.havryliuk.souvenirs.util.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

/**
 * Згідно документації ObjectMapper важкий об'єкт - тому один на програму.
 */
public class Mapper extends ObjectMapper {
    private static Mapper mapper;

    private Mapper(){}

    public static Mapper getObjectMapper(){
        if (mapper == null) {
            mapper = new Mapper();
            mapper.registerModule(new JavaTimeModule());
            mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        }
        return mapper;
    }


    public <T> T mapEntity(JsonNode node, Class<T> valueType) {
//    public Entity mapEntity(JsonNode node, Class<? extends Entity> clazz) {
        try {
            return mapper.treeToValue(node, valueType);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }


}
