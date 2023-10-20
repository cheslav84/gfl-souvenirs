package gfl.havryliuk.souvenirs.repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import gfl.havryliuk.souvenirs.entities.Producer;
import gfl.havryliuk.souvenirs.testDataProvider.ProducerProvider;
import gfl.havryliuk.souvenirs.util.json.Document;
import gfl.havryliuk.souvenirs.util.json.Mapper;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

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

        List<Producer> savedProducers = getSavedProducers();
        Producer savedUaProducer = savedProducers.get(0);
        Producer savedUkProducer = savedProducers.get(1);

        assertThat(savedProducers).size().isEqualTo(2);
        assertThat(savedUaProducer).isEqualTo(uaProducer);
        assertThat(savedUkProducer).isEqualTo(ukProducer);
    }


    @Test
    public void testSaveAllProducerSaving() throws IOException {
        int number = 100_000;
        List<Producer> producers = ProducerProvider.getProducers(number);
        repository.saveAll(producers);
        assertThat(getSavedProducers()).isEqualTo(producers);
    }


    @Test
    public void testSaveOneInLargeDocument() throws IOException {
        int number = 100_000;
        Producer producer = ProducerProvider.getProducerWithSouvenirs();
        List<Producer> producers = ProducerProvider.getProducers(number);
        repository.saveAll(producers);
        repository.save(producer);
        producers.add(producer);
        assertThat(getSavedProducers()).isEqualTo(producers);
    }



    @Test
    public void testSpeedSavingLargeNumberOfProducers() {
        int number = 100_000;
        List<Producer> producers = ProducerProvider.getProducers(number);
        long startSaveAll = System.currentTimeMillis();
        repository.saveAll(producers);
        long endSaveAll = System.currentTimeMillis();
        assertThat(endSaveAll - startSaveAll).isLessThan(1000);
    }


    @Test
    public void testSpeedSavingOneIntoLargeDocument() {
        int number = 100_000;
        Producer ukProducer = ProducerProvider.getProducerWithSouvenirs();
        List<Producer> producers = ProducerProvider.getProducers(number);
        repository.saveAll(producers);
        long startSaveLast = System.currentTimeMillis();
        repository.save(ukProducer);
        long endSaveLast = System.currentTimeMillis();
        assertThat(endSaveLast - startSaveLast).isLessThan(1000);
    }


    @Test
    public void testGetAll() {
        int number = 100_000;
        List<Producer> producers = ProducerProvider.getProducers(number);
        repository.saveAll(producers);
        assertThat(repository.getAll()).isEqualTo(producers);
    }


    @Test
    public void testGetById() {
        int number = 10;
        List<Producer> producers = ProducerProvider.getProducers(number);
        repository.saveAll(producers);
        Producer defaultProducer = producers.get(number/2);
        Producer expected = repository.getById(defaultProducer.getId()).orElseThrow();
        assertThat(expected).isEqualTo(defaultProducer);
    }


    @Test
    public void testGetByIdNotFound() {
        int number = 10;
        List<Producer> producers = ProducerProvider.getProducers(number);
        repository.saveAll(producers);
        assertThat(repository.getById(UUID.randomUUID())).isEmpty();
    }


    @Test
    public void testDelete() {
        int number = 10;
        List<Producer> producers = ProducerProvider.getProducers(number);
        repository.saveAll(producers);
        Producer toDelete = producers.get(number/2);
        repository.delete(toDelete.getId());
        List<Producer> afterRemoving = repository.getAll();

        assertThat(afterRemoving)
                .doesNotContain(toDelete)
                .size().isEqualTo(number - 1);

    }


    private List<Producer> getSavedProducers() throws IOException {
        return mapper.readValue(PRODUCERS, new TypeReference<>(){});
    }

}