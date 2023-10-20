package gfl.havryliuk.souvenirs.util.json;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Згідно документації ObjectMapper важкий об'єкт - тому один на програму.
 */
public class Mapper extends ObjectMapper {
    private static ObjectMapper mapper;

    private Mapper(){}

    public static ObjectMapper getObjectMapper(){
        if (mapper == null) {
            mapper = new ObjectMapper();
        }
        return mapper;
    }

}
