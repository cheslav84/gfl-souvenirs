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
import java.util.List;
import java.util.UUID;
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


    @BeforeMethod
    public void setUp() {
        when(producerStorage.getStorage()).thenReturn(PRODUCERS);
        when(souvenirStorage.getStorage()).thenReturn(SOUVENIRS);
        new Document<Producer>(producerStorage).create();
        new Document<Souvenir>(souvenirStorage).create();
        producerRepository = new ProducerRepository(producerStorage, souvenirRepository);
        souvenirRepository = new SouvenirRepository(souvenirStorage, producerRepository);
    }

    @AfterMethod
    public void tearDown() {
//        PRODUCERS.deleteOnExit();
//        SOUVENIRS.deleteOnExit();
    }


    @Test
    public void testSave() throws IOException {
        Producer uaProducer = ProducerProvider.getProducer();
        Producer ukProducer = ProducerProvider.getProducerWithSouvenirsId();

        producerRepository.save(uaProducer);
        producerRepository.save(ukProducer);

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
        producerRepository.saveAll(producers);
        assertThat(getSavedProducers()).isEqualTo(producers);
    }


    @Test
    public void testSaveOneInLargeDocument() throws IOException {
        int number = 100_000;
        Producer producer = ProducerProvider.getProducerWithSouvenirsId();
        List<Producer> producers = ProducerProvider.getProducers(number);
        producerRepository.saveAll(producers);
        producerRepository.save(producer);
        producers.add(producer);
        assertThat(getSavedProducers()).isEqualTo(producers);
    }


    @Test
    public void testSpeedSavingLargeNumberOfProducers() {
        int number = 100_000;
        List<Producer> producers = ProducerProvider.getProducers(number);
        long startSaveAll = System.currentTimeMillis();
        producerRepository.saveAll(producers);
        long endSaveAll = System.currentTimeMillis();
        assertThat(endSaveAll - startSaveAll).isLessThan(1000);
    }


    @Test
    public void testSpeedSavingOneIntoLargeDocument() {
        int number = 100_000;
        Producer ukProducer = ProducerProvider.getProducerWithSouvenirsId();
        List<Producer> producers = ProducerProvider.getProducers(number);
        producerRepository.saveAll(producers);
        long startSaveLast = System.currentTimeMillis();
        producerRepository.save(ukProducer);
        long endSaveLast = System.currentTimeMillis();
        assertThat(endSaveLast - startSaveLast).isLessThan(1000);
    }


    @Test
    public void testUpdate() throws IOException {
        Producer uaProducer = ProducerProvider.getProducer();
        Producer ukProducer = ProducerProvider.getProducerWithSouvenirsId();

        producerRepository.save(uaProducer);
        producerRepository.save(ukProducer);

        ukProducer.setCountry("England");
        ukProducer.getSouvenirs().add(SouvenirProvider.getSouvenirWithProducer());
        producerRepository.save(ukProducer);

        List<Producer> savedProducers = getSavedProducers();
        Producer savedUaProducer = savedProducers.get(0);
        Producer savedUkProducer = savedProducers.get(1);

        assertThat(savedProducers).size().isEqualTo(2);
        assertThat(savedUaProducer).isEqualTo(uaProducer);
        assertThat(savedUkProducer).isEqualTo(ukProducer);
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
        int number = 100_000;
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
        producerRepository = new ProducerRepository(producerStorage, souvenirRepository);
        Producer toDelete = ProducerAndSouvenirProvider.initStoragesAndGetProducer(producers, souvenirsInProducer,
                producerRepository, souvenirRepository);
        producerRepository.delete(toDelete.getId());

        assertThat(producerRepository.getAll())
                .doesNotContain(toDelete)
                 .size().isEqualTo(producers - 1);
    }

    @Test
    public void testDeleteSouvenirsRemoved() {
        int producers = 2;
        int souvenirsInProducer = 2;
        producerRepository = new ProducerRepository(producerStorage, souvenirRepository);
        Producer toDelete = ProducerAndSouvenirProvider.initStoragesAndGetProducer(producers, souvenirsInProducer,
                producerRepository, souvenirRepository);

        producerRepository.delete(toDelete.getId());

        assertThat(souvenirRepository.getAll())
                .doesNotContainAnyElementsOf(toDelete.getSouvenirs())
                .size().isEqualTo(producers * souvenirsInProducer - toDelete.getSouvenirs().size());
    }


    @Test
    public void getByPriceLessThan() {
        int number = 10;
        double price = 10;
        List<Producer> producers = ProducerProvider.getProducerWithSouvenirPrices(number, price);
        producerRepository = new ProducerRepository(producerStorage, souvenirRepository);
        producerRepository.saveAll(producers);
        saveSouvenirs(producers);
        assertThat(producerRepository.getByPriceLessThan(price)).isEqualTo(getProducersWithPriceLessThan(producers, price));
    }


    @Test
    public void getByPriceEmptyList() {
        // Ціна непарних виробників товару у тестовому List<Producer> getProducerWithSouvenirPrices буде зазначена
        // як вища за вказану у тесті. Як наслілок - буде створений List лише з одним виробником та вищою ціною.


        int number = 1;
        double price = 10;
        List<Producer> producers = ProducerProvider.getProducerWithSouvenirPrices(number, price);
        producerRepository = new ProducerRepository(producerStorage, souvenirRepository);
        producerRepository.saveAll(producers);
        saveSouvenirs(producers);
        assertThat(producerRepository.getByPriceLessThan(price))
                .isEqualTo(getProducersWithPriceLessThan(producers, price))
                .isEmpty();
    }


//    @Test
//    public void testDeleteAll() {
//        int producers = 20;
//        int souvenirsInProducer = 20;
//        initSouvenirRepository();
//
//        List<Souvenir> listToDelete = ProducerAndSouvenirProvider.initStorages(producers, souvenirsInProducer,
//                producerRepository, souvenirRepository);
//
//
//        producerRepository.deleteAll(listToDelete);
//
//        assertThat(producerRepository.getAll())
//                .doesNotContainAnyElementsOf(listToDelete)
//                .size().isEqualTo(producers * souvenirsInProducer - listToDelete.size());
//    }








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

        initSouvenirRepository();
        souvenirRepository.saveAll(allSouvenirs);
    }

    private void initSouvenirRepository() {
        when(souvenirStorage.getStorage()).thenReturn(SOUVENIRS);
        new Document<Souvenir>(souvenirStorage).create();
//        souvenirRepository = new SouvenirRepository(souvenirStorage, producerStorage);
    }


    private List<Producer> getSavedProducers() throws IOException {
        return mapper.readValue(PRODUCERS, new TypeReference<>(){});
    }

}