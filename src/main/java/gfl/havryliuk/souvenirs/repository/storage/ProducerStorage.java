package gfl.havryliuk.souvenirs.repository.storage;


import lombok.Getter;

import java.io.File;

@Getter
public class ProducerStorage implements Storage {//todo виконати через інтерфейс, передавати свій storage при створенні документу
    public static final String PRODUCER_STORAGE = "src/main/resources/data/Producers.json";

    private final File producerStorage;

    public ProducerStorage() {
        this.producerStorage = new File(PRODUCER_STORAGE);
    }

    public File getStorage() {
        return producerStorage;
    }
}
