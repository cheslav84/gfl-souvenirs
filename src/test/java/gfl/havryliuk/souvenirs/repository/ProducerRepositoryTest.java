package gfl.havryliuk.souvenirs.repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import gfl.havryliuk.souvenirs.entities.Producer;
import gfl.havryliuk.souvenirs.entities.Souvenir;
import gfl.havryliuk.souvenirs.repository.storage.ProducerStorage;
import gfl.havryliuk.souvenirs.repository.storage.SouvenirStorage;
import gfl.havryliuk.souvenirs.testDataProvider.ProducerProvider;
import gfl.havryliuk.souvenirs.util.json.Document;
import gfl.havryliuk.souvenirs.util.json.Mapper;
import org.mockito.Mock;
import org.mockito.testng.MockitoTestNGListener;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.annotations.Listeners;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@Listeners(MockitoTestNGListener.class)
public class ProducerRepositoryTest {
    @Mock
    private ProducerStorage producerStorage;

    @Mock
    private SouvenirStorage souvenirStorage;

    private static final String PRODUCERS_PATH = "src/test/java/data/Producers.json";
    private static final String SOUVENIR_PATH = "src/test/java/data/Souvenirs.json";
    private static final File PRODUCERS = new File(PRODUCERS_PATH);
    private static final File SOUVENIRS = new File(SOUVENIR_PATH);
    private final ObjectMapper mapper = Mapper.getMapper();
    private ProducerRepository repository;


    @BeforeMethod
    public void setUp() {
        when(producerStorage.getStorage()).thenReturn(PRODUCERS);
        new Document<Producer>(producerStorage).create();
        repository = new ProducerRepository(producerStorage, souvenirStorage);
    }

    @AfterMethod
    public void tearDown() {
        PRODUCERS.deleteOnExit();
        SOUVENIRS.deleteOnExit();
    }


    @Test
    public void testSave() throws IOException {
        Producer uaProducer = ProducerProvider.getProducer();
        Producer ukProducer = ProducerProvider.getProducerWithSouvenirsId();

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
        Producer producer = ProducerProvider.getProducerWithSouvenirsId();
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
        Producer ukProducer = ProducerProvider.getProducerWithSouvenirsId();
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
        Producer defaultProducerEntity = producers.get(number/2);
        Producer expected = repository.getById(defaultProducerEntity.getId()).orElseThrow();
        assertThat(expected).isEqualTo(defaultProducerEntity);
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


    @Test
    public void getByPriceLessThan() {
        int number = 10;
        double price = 10;
        List<Producer> producers = ProducerProvider.getProducerWithSouvenirPrices(number, price);
        repository.saveAll(producers);
        saveSouvenirs(producers);
        assertThat(repository.getByPriceLessThan(price)).isEqualTo(getProducersWithPriceLessThan(producers, price));
    }


    @Test
    public void getByPriceEmptyList() {
        // Ціна непарних виробників товару у тестовому List<Producer> getProducerWithSouvenirPrices буде зазначена
        // як вища за вказану у тесті. Як наслілок - буде створений List лише з одним виробником та вищою ціною.
        int number = 1;
        double price = 10;
        List<Producer> producers = ProducerProvider.getProducerWithSouvenirPrices(number, price);
        repository.saveAll(producers);
        saveSouvenirs(producers);
        assertThat(repository.getByPriceLessThan(price))
                .isEqualTo(getProducersWithPriceLessThan(producers, price))
                .isEmpty();
    }

    private static List<Producer> getProducersWithPriceLessThan(List<Producer> producers, double price) {
        return producers.stream()
                .filter(p -> p.getSouvenirs().stream()
                        .map(Souvenir::getPrice)
                        .anyMatch(pr -> pr < price))
                         .collect(Collectors.toList());
    }

    private void saveSouvenirs(List<Producer> producers) {
        List<Souvenir> allSouvenirs = producers.stream()
                .flatMap(r -> r.getSouvenirs().stream())
                .toList();

        SouvenirRepository souvenirRepository = initSouvenirRepository();
        souvenirRepository.saveAll(allSouvenirs);
    }

    private SouvenirRepository initSouvenirRepository() {
        when(souvenirStorage.getStorage()).thenReturn(SOUVENIRS);
        new Document<Souvenir>(souvenirStorage).create();
        return new SouvenirRepository(souvenirStorage);
    }


    private List<Producer> getSavedProducers() throws IOException {
        return mapper.readValue(PRODUCERS, new TypeReference<>(){});
    }

}