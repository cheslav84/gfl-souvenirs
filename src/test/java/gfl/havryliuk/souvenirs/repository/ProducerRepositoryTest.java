package gfl.havryliuk.souvenirs.repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import gfl.havryliuk.souvenirs.entities.Producer;
import gfl.havryliuk.souvenirs.entities.Souvenir;
import gfl.havryliuk.souvenirs.storage.ProducerFileStorage;
import gfl.havryliuk.souvenirs.storage.SouvenirFileStorage;
import gfl.havryliuk.souvenirs.testDataProvider.ProducerAndSouvenirProvider;
import gfl.havryliuk.souvenirs.testDataProvider.ProducerProvider;
import gfl.havryliuk.souvenirs.testDataProvider.SouvenirProvider;
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
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.function.BiPredicate;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@Listeners(MockitoTestNGListener.class)
public class ProducerRepositoryTest {
    @Mock
    private ProducerFileStorage producerStorage;

    @Mock
    private SouvenirFileStorage souvenirStorage;

    private static final String PRODUCERS_PATH = "src/test/java/data/Producers.json";
    private static final String SOUVENIR_PATH = "src/test/java/data/Souvenirs.json";
    private static final File PRODUCERS = new File(PRODUCERS_PATH);
    private static final File SOUVENIRS = new File(SOUVENIR_PATH);
    private final ObjectMapper mapper = Mapper.getMapper();
    private ProducerRepository producerRepository;
    private SouvenirRepository souvenirRepository;

    private final BiPredicate<List<Souvenir>, List<Souvenir>> evaluator = List::containsAll;
    private final BiPredicate<List<Souvenir>, List<Souvenir>> reverseEvaluator = (l1, l2) -> l2.containsAll(l1);

    @BeforeMethod
    public void setUp() {
        when(producerStorage.getStorage()).thenReturn(PRODUCERS);
        when(souvenirStorage.getStorage()).thenReturn(SOUVENIRS);
        new Document<Producer>(producerStorage).create();
        new Document<Souvenir>(souvenirStorage).create();
        souvenirRepository = new SouvenirRepository(souvenirStorage, producerStorage);
        producerRepository = new ProducerRepository(producerStorage, souvenirRepository);
    }

    @AfterMethod
    public void tearDown() {
        PRODUCERS.deleteOnExit();
        SOUVENIRS.deleteOnExit();
    }


    @Test
    public void testSave() throws IOException {
        Producer producer1 = ProducerProvider.getProducerWithSouvenirs();
        Producer producer2 = ProducerProvider.getProducerWithSouvenirs();
        producerRepository = new ProducerRepository(producerStorage, souvenirRepository);

        producerRepository.save(producer1);
        producerRepository.save(producer2);

        List<Producer> savedProducers = getSavedProducers();
        Producer savedUaProducer = savedProducers.get(0);
        Producer savedUkProducer = savedProducers.get(1);

        assertThat(savedProducers).size().isEqualTo(2);
        assertThat(savedUaProducer).isEqualTo(producer1);
        assertThat(savedUkProducer).isEqualTo(producer2);
    }

    @Test
    public void testSaveSouvenirsWhileSavingProducer() {
        Producer producer1 = ProducerProvider.getProducerWithSouvenirs();
        Producer producer2 = ProducerProvider.getProducerWithSouvenirs();
        producerRepository.save(producer1);
        producerRepository.save(producer2);

        List<Souvenir> souvenirs = new ArrayList<>();
        souvenirs.addAll(producer1.getSouvenirs());
        souvenirs.addAll(producer2.getSouvenirs());

        assertThat(souvenirRepository.getAll()).isEqualTo(souvenirs);

    }


    @Test
    public void testSaveAllProducerSaving() throws IOException {
        int number = 10_000;
        List<Producer> producers = ProducerProvider.getProducers(number);
        producerRepository.saveAll(producers);
        assertThat(getSavedProducers()).isEqualTo(producers);
    }


    @Test
    public void testSaveOneInLargeDocument() throws IOException {
        int number = 10_000;
        Producer producer = ProducerProvider.getProducerWithSouvenirs();
        List<Producer> producers = ProducerProvider.getProducers(number);
        producerRepository.saveAll(producers);
        producerRepository.save(producer);
        producers.add(producer);
        assertThat(getSavedProducers()).isEqualTo(producers);
    }


    @Test
    public void testSpeedSavingLargeNumberOfProducers() {
        int number = 5_000;
        List<Producer> producers = ProducerProvider.getProducers(number);
        long startSaveAll = System.currentTimeMillis();
        producerRepository.saveAll(producers);
        long endSaveAll = System.currentTimeMillis();
        assertThat(endSaveAll - startSaveAll).isLessThan(1000);
    }


    @Test
    public void testSpeedSavingOneIntoLargeDocument() {
        int number = 25_000;
        Producer ukProducer = ProducerProvider.getProducerWithSouvenirs();
        List<Producer> producers = ProducerProvider.getProducers(number);
        producerRepository.saveAll(producers);
        long startSaveLast = System.currentTimeMillis();
        producerRepository.save(ukProducer);
        long endSaveLast = System.currentTimeMillis();
        assertThat(endSaveLast - startSaveLast).isLessThan(1000);
    }


    @Test
    public void testUpdate() throws IOException {
        Producer producer1 = ProducerProvider.getProducerWithSouvenirs();
        Producer producer2 = ProducerProvider.getProducerWithSouvenirs();
        producerRepository.save(producer1);
        producerRepository.save(producer2);

        producer2.setCountry("England");
        producer2.getSouvenirs().add(SouvenirProvider.getSouvenir(producer2));
        producerRepository.save(producer2);

        List<Producer> savedProducers = getSavedProducers();
        Producer savedUaProducer = savedProducers.get(0);
        Producer savedUkProducer = savedProducers.get(1);

        assertThat(savedProducers).size().isEqualTo(2);
        assertThat(savedUaProducer).isEqualTo(producer1);
        assertThat(savedUkProducer).isEqualTo(producer2);
    }


    @Test
    public void testUpdateAll() throws IOException {
        int number = 4;
        int changeFrom = 2;
        int changeTo = 3;
        List<Producer> producers = ProducerProvider.getProducers(number);
        producerRepository.saveAll(producers);

        for (int i = changeFrom; i <= changeTo; i++) {
            Producer item = producers.get(i);
            item.setName("Another name " + i);
            item.setCountry("Another country" + i);
        }

        List<Producer> toChange = producers.subList(changeFrom, changeTo + 1);
        producerRepository.saveAll(toChange);
        assertThat(getSavedProducers()).isEqualTo(producers);
    }


    @Test
    public void testGetAll() {
        int number = 25_000;
        List<Producer> producers = ProducerProvider.getProducers(number);
        producerRepository.saveAll(producers);
        assertThat(producerRepository.getAll()).isEqualTo(producers);
    }


    @Test
    public void testGetById() {
        int number = 10;
        List<Producer> producers = ProducerProvider.getProducers(number);
        producerRepository.saveAll(producers);
        Producer defaultProducerEntity = producers.get(number/2);
        Producer expected = producerRepository.getById(defaultProducerEntity.getId()).orElseThrow();
        assertThat(expected).isEqualTo(defaultProducerEntity);
    }


    @Test
    public void testGetByIdNotFound() {
        int number = 10;
        List<Producer> producers = ProducerProvider.getProducers(number);
        producerRepository.saveAll(producers);
        assertThat(producerRepository.getById(UUID.randomUUID())).isEmpty();
    }


    @Test
    public void testDeleteProducerRemoved() {
        int producers = 2;
        int souvenirsInProducer = 2;
        Producer toDelete = ProducerAndSouvenirProvider.initStoragesAndGetProducer(producers, souvenirsInProducer,
                producerRepository);
        producerRepository.delete(toDelete.getId());

        assertThat(producerRepository.getAll())
                .doesNotContain(toDelete)
                 .size().isEqualTo(producers - 1);
    }

    @Test
    public void testDeleteSouvenirsRemoved() {
        int producers = 2;
        int souvenirsInProducer = 2;
        Producer toDelete = ProducerAndSouvenirProvider.initStoragesAndGetProducer(producers, souvenirsInProducer,
                producerRepository);
        producerRepository.delete(toDelete.getId());

        assertThat(souvenirRepository.getAll())
                .doesNotContainAnyElementsOf(toDelete.getSouvenirs())
                .size().isEqualTo(producers * souvenirsInProducer - toDelete.getSouvenirs().size());
    }


    @Test
    public void testGetByPriceLessThan() {
        int number = 10;
        double price = 10;
        List<Producer> producers = ProducerProvider.getProducerWithSouvenirPrices(number, price);
        producerRepository.saveAll(producers);
        saveSouvenirs(producers);

        assertThat(producerRepository.getByPriceLessThan(price))
                .isEqualTo(getProducersWithPriceLessThan(producers, price));
    }


    @Test
    public void testGetByPriceEmptyList() {
        // Ціна непарних виробників товару у тестовому List<Producer> getProducerWithSouvenirPrices буде зазначена
        // як вища за вказану у тесті. Як наслілок - буде створений List лише з одним виробником та вищою ціною.
        int number = 1;
        double price = 10;
        List<Producer> producers = ProducerProvider.getProducerWithSouvenirPrices(number, price);
        producerRepository.saveAll(producers);

        assertThat(producerRepository.getByPriceLessThan(price))
                .isEqualTo(getProducersWithPriceLessThan(producers, price))
                .isEmpty();
    }


    @Test
    public void testGetProducersWithSouvenirs() {
        int producerAmount = 8;
        int souvenirsInProducer = 8;
        List<Producer> producers = ProducerAndSouvenirProvider
                .initStoragesAndGetProducers(producerAmount, souvenirsInProducer, producerRepository);

        assertThat(producerRepository.getProducersWithSouvenirs())
                .usingRecursiveComparison()
                .withEqualsForFields(evaluator, "souvenirs")
                .withEqualsForFields(reverseEvaluator, "souvenirs")
                .isEqualTo(producers);
    }

    @Test
    public void testGetProducersWithSouvenirsNotEquals() {
        int producerAmount = 8;
        int souvenirsInProducer = 8;
        List<Producer> producers = ProducerAndSouvenirProvider
                .initStoragesAndGetProducers(producerAmount, souvenirsInProducer, producerRepository);
        List<Producer> producersWithSouvenirs = producerRepository.getProducersWithSouvenirs();
        List<Souvenir> souvenirs = producersWithSouvenirs.get(0).getSouvenirs();
        souvenirs.add(SouvenirProvider.getSouvenirWithProducer());

        assertThat(producersWithSouvenirs)
                .usingRecursiveComparison()
                .withEqualsForFields(evaluator, "souvenirs")
                .withEqualsForFields(reverseEvaluator, "souvenirs")
                .isNotEqualTo(producers);
    }



    @Test
    public void testGetProducersBySouvenirAndProductionYear() {
        int producerAmount = 8;
        int souvenirsInProducer = 8;
        String name = "Nice souvenir";
        String year = "2023";
        List<Producer> producers = ProducerAndSouvenirProvider.initStoragesAndGetProducers( producerAmount,
                souvenirsInProducer, name, year, producerRepository);

        assertThat(producerRepository.getProducersBySouvenirAndProductionYear(name, year))
                .isEqualTo(producers);
    }



    private List<Producer> getProducersWithPriceLessThan(List<Producer> producers, double price) {
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

        initSouvenirRepository();
        souvenirRepository.saveAll(allSouvenirs);
    }

    private void initSouvenirRepository() {
        when(souvenirStorage.getStorage()).thenReturn(SOUVENIRS);
        new Document<Souvenir>(souvenirStorage).create();
    }


    private List<Producer> getSavedProducers() throws IOException {
        return mapper.readValue(PRODUCERS, new TypeReference<>(){});
    }

}