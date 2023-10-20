package gfl.havryliuk.souvenirs.util.json;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

/**
 * Згідно документації ObjectMapper важкий об'єкт - тому один на програму.
 */
public class Mapper extends ObjectMapper {
    private static ObjectMapper mapper;

    private Mapper(){}

    public static ObjectMapper getObjectMapper(){
        if (mapper == null) {
            mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());
            mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        }
        return mapper;
    }

}
