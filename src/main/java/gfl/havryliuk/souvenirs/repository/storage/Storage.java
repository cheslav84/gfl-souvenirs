package gfl.havryliuk.souvenirs.repository.storage;


import lombok.Getter;

import java.io.File;

@Getter
public class Storage {//todo виконати через інтерфейс, передавати свій storage при створенні документу
    public static final String PRODUCER_STORAGE = "src/main/resources/data/Producers.json";
    public static final String SOUVENIR_STORAGE = "src/main/resources/data/Souvenirs.json";

    private final File producerStorage;
    private final File souvenirStorage;

    public Storage() {
        this.producerStorage = new File(PRODUCER_STORAGE);
        this.souvenirStorage = new File(SOUVENIR_STORAGE);
    }

    public File getSouvenirsStorage() {
        return souvenirStorage;
    }
}
