//package gfl.havryliuk.souvenirs.util.json;
//
//import com.fasterxml.jackson.core.JsonParser;
//import com.fasterxml.jackson.core.ObjectCodec;
//import com.fasterxml.jackson.databind.DeserializationContext;
//import com.fasterxml.jackson.databind.JsonNode;
//import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
//import gfl.havryliuk.souvenirs.entities.dto.SouvenirsGroupedByProductionYear;
//
//import java.io.IOException;
//
//public class CustomSouvenirsByYearDeserializer extends StdDeserializer<SouvenirsGroupedByProductionYear> {
//    public CustomSouvenirsByYearDeserializer() {
//        this(null);
//    }
//
//    public CustomSouvenirsByYearDeserializer(Class<?> vc) {
//        super(vc);
//    }
//
//    @Override
//    public SouvenirsGroupedByProductionYear deserialize(JsonParser parser, DeserializationContext deserializer) throws IOException {
//        SouvenirsGroupedByProductionYear souvenir = new SouvenirsGroupedByProductionYear();
//        ObjectCodec codec = parser.getCodec();
//
//        JsonNode node = codec.readTree(parser);
//
//        // try catch block
//        JsonNode souvenir = node.get("souvenir");
//
//        String name = souvenir.asText();
//        souvenir.setName(name);
//
//
//
//        return souvenir;
//    }
//}
