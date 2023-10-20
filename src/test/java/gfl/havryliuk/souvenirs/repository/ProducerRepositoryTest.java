package gfl.havryliuk.souvenirs.repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import gfl.havryliuk.souvenirs.entities.Producer;
import gfl.havryliuk.souvenirs.entities.Souvenir;
import gfl.havryliuk.souvenirs.testDataProvider.ProducerProvider;
import gfl.havryliuk.souvenirs.util.json.Document;
import gfl.havryliuk.souvenirs.util.json.Mapper;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.testng.Assert.*;

public class ProducerRepositoryTest {

    private static final File PRODUCERS = new File("src/test/java/data/Producers.json");
    private final ObjectMapper mapper = Mapper.getObjectMapper();
    private ProducerRepository repository;




    @BeforeMethod
    public void setUp() {
        new Document<Producer>().create(PRODUCERS);
        repository = new ProducerRepository(PRODUCERS);
    }

    @AfterMethod
    public void tearDown() {
        PRODUCERS.deleteOnExit();
    }




    @Test
    public void testSave() throws IOException {
        Producer uaProducer = ProducerProvider.getProducer();
        Producer ukProducer = ProducerProvider.getProducerWithSouvenirs();

        repository.save(uaProducer);
        repository.save(ukProducer);

        List<Producer> savedProducers = mapper.readValue(PRODUCERS, new TypeReference<>(){});
        Producer savedUaProducer = savedProducers.get(0);
        Producer savedUkProducer = savedProducers.get(1);
        List<Souvenir> savedSouvenirs = savedUkProducer.getSouvenirs();

        assertThat(savedProducers).size().isEqualTo(2);
        assertThat(savedUaProducer.getName()).isEqualTo(uaProducer.getName());
        assertThat(savedUaProducer.getCountry()).isEqualTo(uaProducer.getCountry());
        assertThat(savedUkProducer.getName()).isEqualTo(ukProducer.getName());
        assertThat(savedUkProducer.getCountry()).isEqualTo(ukProducer.getCountry());
        assertThat(savedSouvenirs.get(0).getId()).isEqualTo(ukProducer.getSouvenirs().get(0).getId());
        assertThat(savedSouvenirs.get(1).getId()).isEqualTo(ukProducer.getSouvenirs().get(1).getId());
    }


    @Test
    public void testSaveAll() throws IOException {
        Producer ukProducer = ProducerProvider.getProducerWithSouvenirs();

        int number = 100_000;
        List<Producer> producers = ProducerProvider.getProducers(number);

        long startSaveAll = System.currentTimeMillis();
        repository.saveAll(producers);
        long endSaveAll = System.currentTimeMillis();

        List<Producer> savedProducers = mapper.readValue(PRODUCERS, new TypeReference<>(){});
        assertThat(savedProducers).size().isEqualTo(number);

        long startSaveLast = System.currentTimeMillis();
        repository.save(ukProducer);
        long endSaveLast = System.currentTimeMillis();
        List<Producer> savedAll = mapper.readValue(PRODUCERS, new TypeReference<>(){});

        assertThat(savedAll).size().isEqualTo(number + 1);
        assertThat(endSaveLast - startSaveLast).isLessThan(1000);
        assertThat(endSaveAll - startSaveAll).isLessThan(1000);
    }








    @Test
    public void testGetAll() {
    }

    @Test
    public void testGetById() {
    }

    @Test
    public void testDelete() {
    }
}