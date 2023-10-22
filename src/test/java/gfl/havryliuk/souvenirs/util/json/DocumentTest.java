package gfl.havryliuk.souvenirs.util.json;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import gfl.havryliuk.souvenirs.entities.Producer;
import gfl.havryliuk.souvenirs.repository.storage.Storage;
import gfl.havryliuk.souvenirs.testDataProvider.ProducerProvider;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.testng.Assert.assertTrue;

public class DocumentTest {

    private static final File STORAGE = new File("src/test/java/data/Test.json");
    private final ObjectMapper mapper = Mapper.getMapper();
    private Document<Producer> producers;


    @BeforeMethod
    public void setUp() {
        Storage storage = () -> STORAGE;
        new Document<Producer>(storage).create();
        producers = new Document<>(storage);
    }

    @AfterMethod
    public void tearDown() {
        STORAGE.deleteOnExit();
    }


    @Test
    public void testCreate() {
        producers.create();
        assertTrue(STORAGE.exists());
    }


    @Test
    public void testGetDocument() {
        producers.create();
        ArrayNode document = producers.getRecords();
        assertTrue(document.isEmpty());
    }


    @Test
    public void testSaveDocument() throws IOException {
        Producer uaProducer = ProducerProvider.getProducer();
        producers.create();
        ArrayNode document = producers.getRecords();
        document.add(mapper.valueToTree(uaProducer));
        producers.saveRecords(document);
        List<Producer> savedProducers = mapper.readValue(STORAGE, new TypeReference<>(){});
        Producer savedUaProducer = savedProducers.get(0);

        assertThat(savedProducers).size().isEqualTo(1);
        assertThat(savedUaProducer.getName()).isEqualTo(uaProducer.getName());
        assertThat(savedUaProducer.getCountry()).isEqualTo(uaProducer.getCountry());
    }

}