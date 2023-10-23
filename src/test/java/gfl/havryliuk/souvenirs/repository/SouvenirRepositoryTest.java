package gfl.havryliuk.souvenirs.repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import gfl.havryliuk.souvenirs.entities.Producer;
import gfl.havryliuk.souvenirs.entities.Souvenir;
import gfl.havryliuk.souvenirs.storage.ProducerFileStorage;
import gfl.havryliuk.souvenirs.storage.SouvenirFileStorage;
import gfl.havryliuk.souvenirs.testDataProvider.ProducerProvider;
import gfl.havryliuk.souvenirs.testDataProvider.SouvenirProvider;
import gfl.havryliuk.souvenirs.util.json.Document;
import gfl.havryliuk.souvenirs.util.json.Mapper;
import org.mockito.Mock;
import org.mockito.testng.MockitoTestNGListener;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@Listeners(MockitoTestNGListener.class)
public class SouvenirRepositoryTest {
    @Mock
    private SouvenirFileStorage souvenirStorage;

    @Mock
    private ProducerFileStorage producerStorage;

    private static final String SOUVENIR_PATH = "src/test/java/data/Souvenirs.json";
    private static final String PRODUCERS_PATH = "src/test/java/data/Producers.json";
    private static final File SOUVENIRS = new File(SOUVENIR_PATH);
    private static final File PRODUCERS = new File(PRODUCERS_PATH);
    private final ObjectMapper mapper = Mapper.getMapper();
    private SouvenirRepository repository;
    private ProducerRepository producerRepository;


    @BeforeMethod
    public void setUp() {
        when(souvenirStorage.getStorage()).thenReturn(SOUVENIRS);
        new Document<Souvenir>(souvenirStorage).create();
        repository = new SouvenirRepository(souvenirStorage, producerStorage);
    }

    @AfterMethod
    public void tearDown() {
//        SOUVENIRS.deleteOnExit();
//        PRODUCERS.deleteOnExit();
    }



    @Test
    public void testSave() throws IOException {
        Souvenir souvenir1 = SouvenirProvider.getSouvenirWithProducer();
        Souvenir souvenir2 = SouvenirProvider.getSouvenirWithProducer();

        repository.save(souvenir1);
        repository.save(souvenir2);

        List<Souvenir> savedSouvenirs = getSavedSouvenirs();
        Souvenir savedSouvenir1Entity = savedSouvenirs.get(0);
        Souvenir savedSouvenir2Entity = savedSouvenirs.get(1);

        assertThat(savedSouvenirs).size().isEqualTo(2);
        assertThat(savedSouvenir1Entity).isEqualTo(souvenir1);
        assertThat(savedSouvenir2Entity).isEqualTo(souvenir2);
    }


    @Test
    public void testSaveAllSouvenirsSaving() throws IOException {
        int number = 100_000;
        List<Souvenir> souvenirs = SouvenirProvider.getSouvenirsWithProducer(number);
        repository.saveAll(souvenirs);
        assertThat(getSavedSouvenirs()).isEqualTo(souvenirs);
    }


    @Test
    public void testSaveOneInLargeDocument() throws IOException {
        int number = 100_000;
        Souvenir souvenir = SouvenirProvider.getSouvenirWithProducer();
        List<Souvenir> souvenirs = SouvenirProvider.getSouvenirsWithProducer(number);
        repository.saveAll(souvenirs);
        repository.save(souvenir);
        souvenirs.add(souvenir);
        assertThat(getSavedSouvenirs()).isEqualTo(souvenirs);
    }


    @Test
    public void testSpeedSavingLargeNumberOfProducers() {
        int number = 100_000;
        List<Souvenir> souvenirs = SouvenirProvider.getSouvenirsWithProducer(number);
        long startSaveAll = System.currentTimeMillis();
        repository.saveAll(souvenirs);
        long endSaveAll = System.currentTimeMillis();
        assertThat(endSaveAll - startSaveAll).isLessThan(1000);
    }


    @Test
    public void testSpeedSavingOneIntoLargeDocument() {
        int number = 100_000;
        Souvenir souvenir = SouvenirProvider.getSouvenirWithProducer();
        List<Souvenir> souvenirs = SouvenirProvider.getSouvenirsWithProducer(number);
        repository.saveAll(souvenirs);
        long startSaveLast = System.currentTimeMillis();
        repository.save(souvenir);
        long endSaveLast = System.currentTimeMillis();
        assertThat(endSaveLast - startSaveLast).isLessThan(1000);
    }


    @Test
    public void testGetAll() {
        int number = 100_000;
        List<Souvenir> souvenirs = SouvenirProvider.getSouvenirsWithProducer(number);
        repository.saveAll(souvenirs);
        assertThat(repository.getAll()).isEqualTo(souvenirs);
    }


    @Test
    public void testGetById() {
        int number = 10;
        List<Souvenir> souvenirs = SouvenirProvider.getSouvenirsWithProducer(number);
        repository.saveAll(souvenirs);
        Souvenir defaultSouvenir = souvenirs.get(number/2);
        Souvenir expected = repository.getById(defaultSouvenir.getId()).orElseThrow();
        assertThat(expected).isEqualTo(defaultSouvenir);
    }


    @Test
    public void testGetByIdNotFound() {
        int number = 10;
        List<Souvenir> souvenirs = SouvenirProvider.getSouvenirsWithProducer(number);
        repository.saveAll(souvenirs);
        assertThat(repository.getById(UUID.randomUUID())).isEmpty();
    }


    @Test
    public void testDelete() {
        int number = 3;
        Producer producer = ProducerProvider.getProducer();
        Souvenir souvenirToDelete = initStorages(number, producer);
        repository.delete(souvenirToDelete.getId());
        assertThat(repository.getAll())
                .doesNotContain(souvenirToDelete)
                .size().isEqualTo(number - 1);
    }

    @Test
    public void testDeleteRemovesProducerId() {
        int number = 3;
        Producer producer = ProducerProvider.getProducer();
        Souvenir souvenirToDelete = initStorages(number, producer);
        repository.delete(souvenirToDelete.getId());
        assertThat(getProducerSouvenirsId(producer))
                .doesNotContain(souvenirToDelete.getId())
                .size().isEqualTo(number - 1);
    }

    private List<UUID> getProducerSouvenirsId(Producer producer) {
        return producerRepository.getById(producer.getId())
                .orElseThrow()
                .getSouvenirs().stream()
                .map(Souvenir::getId)
                .collect(Collectors.toList());
    }

    private Souvenir initStorages(int number, Producer producer) {
        initProducerRepository();
        List<Souvenir> souvenirs = SouvenirProvider.getSouvenirs(number, producer);
        producer.setSouvenirs(souvenirs);
        repository.saveAll(souvenirs);
        producerRepository.save(producer);
        return souvenirs.get(number/2);
    }



    private List<Souvenir> getSavedSouvenirs() throws IOException {
        return mapper.readValue(SOUVENIRS, new TypeReference<>() {});
    }


    @Test
    public void testDeleteAll() {
        int number = 100;
        List<Souvenir> souvenirs = SouvenirProvider.getSouvenirsWithProducer(number);

        List<Souvenir> toDelete = souvenirs.stream()
                .filter(s -> s.hashCode() % 2 == 0)
                .collect(Collectors.toList());

        List<Souvenir> remainders = souvenirs.stream()
                .filter(s -> s.hashCode() % 2 != 0)
                .collect(Collectors.toList());

        repository.saveAll(souvenirs);
        repository.deleteAll(toDelete);

        assertThat(repository.getAll())
                .isEqualTo(remainders)
                .doesNotContainAnyElementsOf(toDelete);

    }

    private void initProducerRepository() {
        when(producerStorage.getStorage()).thenReturn(PRODUCERS);
        new Document<Producer>(producerStorage).create();
        producerRepository = new ProducerRepository(producerStorage, souvenirStorage);
    }
}