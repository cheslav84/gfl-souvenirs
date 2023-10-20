package gfl.havryliuk.souvenirs;

import gfl.havryliuk.souvenirs.entities.Producer;
import gfl.havryliuk.souvenirs.entities.Souvenir;
import gfl.havryliuk.souvenirs.util.StorageProperties;
import gfl.havryliuk.souvenirs.util.json.Document;

import java.io.File;
import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {

        initStorages();

//        File file = new File("src/main/resources/data/Producers.json");
//
//        ObjectMapper mapper = new ObjectMapper();
//        mapper.writeValue(file, new ArrayList<Producer>());
//
//        Producer producer = new Producer("Tea cup producer", "Ukraine");
//        Producer producer2 = new Producer("Coffee cup producer", "Ukraine");

//        List<Producer> producers = mapper.readValue(file, List.class);
//        producers.add(producer);
//        mapper.writeValue(file, producers);

//
//        InputStream exampleInput = Main.class.getClassLoader()
//                        .getResourceAsStream("src/main/resources/data/Producers.json");


//        ArrayNode rootNode = (ArrayNode) mapper.readTree(file);
//        JsonNode newProducer = mapper.valueToTree(producer);
//        JsonNode newProducer2 = mapper.valueToTree(producer2);
//
//        if (rootNode.isArray()) {
//            rootNode.add(newProducer);
//            rootNode.add(newProducer2);
//        }
//        mapper.writeValue(file, rootNode);



//        producers.add(mapper.convertValue(producers, Producer.class));
//        ObjectNode outerObject = mapper.createObjectNode(); //the object with the "data" array
//
//        JsonNode parsedJson = mapper.readTree(json); //parse the String or do what you already are doing to deserialize the JSON

//        ArrayNode outerArray = mapper.createArrayNode(); //your outer array
//        ObjectNode outerObject = mapper.createObjectNode(); //the object with the "data" array
//        outerObject.putPOJO("producer", producersDocument);
//        outerArray.add(outerObject);



//        List<Producer> producersDocument = mapper.readValue(file, List.class);
//
//        List<Producer> listCar = mapper.readValue(jsonCarArray, new TypeReference<List<Car>>(){});

//
//        Producer pr = producersDocument.get(0);
//        System.out.println(producersDocument);
//        System.out.println(pr);


//        ObjectMapper mapper = new ObjectMapper();
//        ObjectWriter writer = mapper.writer(new DefaultPrettyPrinter());
//        writer.writeValue(new File("D:/cp/dataTwo.json"), jsonDataObject);
    }

    private static void initStorages() {
        new Document<Producer>().create(new File(StorageProperties.producersPathStorage));
        new Document<Souvenir>().create(new File(StorageProperties.souvenirsPathStorage));
    }
}
