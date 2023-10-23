package gfl.havryliuk.souvenirs.repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import gfl.havryliuk.souvenirs.entities.Producer;
import gfl.havryliuk.souvenirs.entities.Souvenir;
import gfl.havryliuk.souvenirs.storage.ProducerFileStorage;
import gfl.havryliuk.souvenirs.storage.SouvenirFileStorage;
import gfl.havryliuk.souvenirs.testDataProvider.ProducerAndSouvenirProvider;
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
    private SouvenirRepository souvenirRepository;
    private ProducerRepository producerRepository;


    @BeforeMethod
    public void setUp() {
        when(souvenirStorage.getStorage()).thenReturn(SOUVENIRS);
        new Document<Souvenir>(souvenirStorage).create();
        souvenirRepository = new SouvenirRepository(souvenirStorage, producerStorage);
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

        souvenirRepository.save(souvenir1);
        souvenirRepository.save(souvenir2);

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
        souvenirRepository.saveAll(souvenirs);
        assertThat(getSavedSouvenirs()).isEqualTo(souvenirs);
    }


    @Test
    public void testSaveOneInLargeDocument() throws IOException {
        int number = 100_000;
        Souvenir souvenir = SouvenirProvider.getSouvenirWithProducer();
        List<Souvenir> souvenirs = SouvenirProvider.getSouvenirsWithProducer(number);
        souvenirRepository.saveAll(souvenirs);
        souvenirRepository.save(souvenir);
        souvenirs.add(souvenir);
        assertThat(getSavedSouvenirs()).isEqualTo(souvenirs);
    }


    @Test
    public void testSpeedSavingLargeNumberOfProducers() {
        int number = 100_000;
        List<Souvenir> souvenirs = SouvenirProvider.getSouvenirsWithProducer(number);
        long startSaveAll = System.currentTimeMillis();
        souvenirRepository.saveAll(souvenirs);
        long endSaveAll = System.currentTimeMillis();
        assertThat(endSaveAll - startSaveAll).isLessThan(1000);
    }


    @Test
    public void testSpeedSavingOneIntoLargeDocument() {
        int number = 100_000;
        Souvenir souvenir = SouvenirProvider.getSouvenirWithProducer();
        List<Souvenir> souvenirs = SouvenirProvider.getSouvenirsWithProducer(number);
        souvenirRepository.saveAll(souvenirs);
        long startSaveLast = System.currentTimeMillis();
        souvenirRepository.save(souvenir);
        long endSaveLast = System.currentTimeMillis();
        assertThat(endSaveLast - startSaveLast).isLessThan(1000);
    }


    @Test
    public void testGetAll() {
        int number = 100_000;
        List<Souvenir> souvenirs = SouvenirProvider.getSouvenirsWithProducer(number);
        souvenirRepository.saveAll(souvenirs);
        assertThat(souvenirRepository.getAll()).isEqualTo(souvenirs);
    }


    @Test
    public void testGetById() {
        int number = 10;
        List<Souvenir> souvenirs = SouvenirProvider.getSouvenirsWithProducer(number);
        souvenirRepository.saveAll(souvenirs);
        Souvenir defaultSouvenir = souvenirs.get(number/2);
        Souvenir expected = souvenirRepository.getById(defaultSouvenir.getId()).orElseThrow();
        assertThat(expected).isEqualTo(defaultSouvenir);
    }


    @Test
    public void testGetByIdNotFound() {
        int number = 10;
        List<Souvenir> souvenirs = SouvenirProvider.getSouvenirsWithProducer(number);
        souvenirRepository.saveAll(souvenirs);
        assertThat(souvenirRepository.getById(UUID.randomUUID())).isEmpty();
    }


    @Test
    public void testDelete() {
        int producers = 3;
        int souvenirsInProducer = 3;
        initProducerRepository();
        Souvenir souvenirToDelete = ProducerAndSouvenirProvider.initStoragesAndGetSouvenir(producers, souvenirsInProducer,
                producerRepository, souvenirRepository);

        souvenirRepository.delete(souvenirToDelete.getId());
        assertThat(souvenirRepository.getAll())
                .doesNotContain(souvenirToDelete)
                .size().isEqualTo(producers * souvenirsInProducer - 1);
    }

    @Test
    public void testDeleteRemovesProducerId() {
        int producers = 3;
        int souvenirsInProducer = 3;
        initProducerRepository();

        Souvenir souvenirToDelete = ProducerAndSouvenirProvider.initStoragesAndGetSouvenir(producers, souvenirsInProducer,
                producerRepository, souvenirRepository);

        souvenirRepository.delete(souvenirToDelete.getId());
        assertThat(getProducerSouvenirsId())
                .doesNotContain(souvenirToDelete.getId())
                .size().isEqualTo(producers * souvenirsInProducer - 1);
    }


    @Test
    public void testDeleteAll() {
        int producers = 20;
        int souvenirsInProducer = 20;
        initProducerRepository();

        List<Souvenir> listToDelete = ProducerAndSouvenirProvider.initStoragesAndGetSouvenirs(producers, souvenirsInProducer,
                producerRepository, souvenirRepository);
        souvenirRepository.deleteAll(listToDelete);

        assertThat(souvenirRepository.getAll())
                .doesNotContainAnyElementsOf(listToDelete)
                .size().isEqualTo(producers * souvenirsInProducer - listToDelete.size());
    }




    @Test
    public void testDeleteAllRemovesProducersId() {
        int producers = 10;
        int souvenirsInProducer =10;
        initProducerRepository();

        List<Souvenir> listToDelete = ProducerAndSouvenirProvider.initStoragesAndGetSouvenirs(producers, souvenirsInProducer,
                producerRepository, souvenirRepository);
        List<UUID> removedSouvenirId = listToDelete.stream()
                .map(Souvenir::getId)
                .collect(Collectors.toList());

        souvenirRepository.deleteAll(listToDelete);

        assertThat(getProducerSouvenirsId())
                .doesNotContainAnyElementsOf(removedSouvenirId)
                .size().isEqualTo(producers * souvenirsInProducer - listToDelete.size());
    }

    @Test
    public void testSpeedDeleteAll() {
        int producers = 100;
        int souvenirsInProducer = 500;
        initProducerRepository();

        List<Souvenir> listToDelete = ProducerAndSouvenirProvider.initStoragesAndGetSouvenirs(producers, souvenirsInProducer,
                producerRepository, souvenirRepository);

        long beforeDeleting = System.currentTimeMillis();
        souvenirRepository.deleteAll(listToDelete);
        long afterDeleting = System.currentTimeMillis();

        assertThat(afterDeleting - beforeDeleting).isLessThan(1000);
    }

    private List<UUID> getProducerSouvenirsId() {
        return producerRepository.getAll().stream()
                .flatMap(p -> p.getSouvenirs().stream())
                .map(Souvenir::getId)
                .collect(Collectors.toList());
    }


//    private List<Souvenir> initStorages(int producers, int souvenirsInProducer) {
//        initProducerRepository();
//
//        List<Souvenir> allSouvenirs = new ArrayList<>();
//        List<Producer> allProducers = new ArrayList<>();
//
//        List<Souvenir> toDelete = new ArrayList<>();
//        for (int i = 0; i < producers; i++) {
//            Producer producer = ProducerProvider.getProducer();
//            List<Souvenir> souvenirs = SouvenirProvider.getSouvenirs(souvenirsInProducer, producer);
//            producer.setSouvenirs(souvenirs);
//            toDelete.add(souvenirs.get(i));
//            allSouvenirs.addAll(souvenirs);
//            allProducers.add(producer);
//        }
//        repository.saveAll(allSouvenirs);
//        producerRepository.saveAll(allProducers);
//        return toDelete;
//    }


    private void initProducerRepository() {
        when(producerStorage.getStorage()).thenReturn(PRODUCERS);
        new Document<Producer>(producerStorage).create();
        producerRepository = new ProducerRepository(producerStorage, souvenirStorage, souvenirRepository);
    }


    private List<Souvenir> getSavedSouvenirs() throws IOException {
        return mapper.readValue(SOUVENIRS, new TypeReference<>() {});
    }


}