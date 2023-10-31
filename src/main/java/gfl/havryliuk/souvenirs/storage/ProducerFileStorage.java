package gfl.havryliuk.souvenirs.storage;


import java.io.File;

public class ProducerFileStorage implements FileStorage {

    public final String fileName = "Producers.json";

    private final File producerStorage;

    public ProducerFileStorage() {
        this.producerStorage = new File(StorageProperties.storageDirectory + fileName);
    }

    public File getStorage() {
        return producerStorage;
    }
}
