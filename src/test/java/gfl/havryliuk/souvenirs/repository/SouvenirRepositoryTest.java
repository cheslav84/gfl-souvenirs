package gfl.havryliuk.souvenirs.repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import gfl.havryliuk.souvenirs.entities.Souvenir;
import gfl.havryliuk.souvenirs.repository.storage.SouvenirStorage;
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

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@Listeners(MockitoTestNGListener.class)
public class SouvenirRepositoryTest {
    @Mock
    private SouvenirStorage souvenirStorage;
    private static final String SOUVENIRS_PATH = "src/test/java/data/Souvenir.json";
    private static final File SOUVENIRS = new File(SOUVENIRS_PATH);
    private final ObjectMapper mapper = Mapper.getMapper();
    private SouvenirRepository repository;


    @BeforeMethod
    public void setUp() {
        when(souvenirStorage.getStorage()).thenReturn(SOUVENIRS);
        new Document<Souvenir>(souvenirStorage).create();
        repository = new SouvenirRepository(souvenirStorage);
    }

    @AfterMethod
    public void tearDown() {
        SOUVENIRS.deleteOnExit();
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
        int number = 10;
        List<Souvenir> souvenirs = SouvenirProvider.getSouvenirsWithProducer(number);
        repository.saveAll(souvenirs);
        Souvenir toDelete = souvenirs.get(number/2);
        repository.delete(toDelete.getId());
        List<Souvenir> afterRemoving = repository.getAll();

        assertThat(afterRemoving)
                .doesNotContain(toDelete)
                .size().isEqualTo(number - 1);
    }


    private List<Souvenir> getSavedSouvenirs() throws IOException {
        return mapper.readValue(SOUVENIRS, new TypeReference<>() {});
    }


}