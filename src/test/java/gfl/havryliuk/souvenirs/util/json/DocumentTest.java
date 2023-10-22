package gfl.havryliuk.souvenirs.util.json;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import gfl.havryliuk.souvenirs.entities.Producer;
import gfl.havryliuk.souvenirs.repository.storage.Storage;
import gfl.havryliuk.souvenirs.testDataProvider.ProducerProvider;
import org.mockito.Mock;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.testng.Assert.assertTrue;

public class DocumentTest {

    @Mock
    private Storage storage;
    private static final File FILE = new File("src/test/java/data/Strings.json");
    private Document<Producer> producers;
    private ObjectMapper mapper;


    @BeforeMethod
    public void setUp() {
        producers = new Document<>(storage);
        mapper = Mapper.getMapper();
    }

    @AfterMethod
    public void tearDown() {
        FILE.deleteOnExit();
    }


    @Test
    public void testCreate() {
        producers.create(FILE);
        assertTrue(FILE.exists());
    }



    @Test
    public void testGetDocument() {
        producers.create(FILE);
        ArrayNode document = producers.getRecords(FILE);
        assertTrue(document.isEmpty());
    }



    @Test
    public void testSaveDocument() throws IOException {
        Producer uaProducer = ProducerProvider.getProducer();

        producers.create(FILE);
        ArrayNode document = producers.getRecords(FILE);
        document.add(mapper.valueToTree(uaProducer));
        producers.saveRecords(FILE, document);

        List<Producer> savedProducers = mapper.readValue(FILE, new TypeReference<>(){});
        Producer savedUaProducer = savedProducers.get(0);

        assertThat(savedProducers).size().isEqualTo(1);
        assertThat(savedUaProducer.getName()).isEqualTo(uaProducer.getName());
        assertThat(savedUaProducer.getCountry()).isEqualTo(uaProducer.getCountry());

    }






}