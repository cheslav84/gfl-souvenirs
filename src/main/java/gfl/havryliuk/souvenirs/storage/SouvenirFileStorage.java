package gfl.havryliuk.souvenirs.storage;


import java.io.File;

public class SouvenirFileStorage implements FileStorage {
    public final String fileName = "Producers.json";

    private final File souvenirStorage;

    public SouvenirFileStorage() {
        this.souvenirStorage = new File(StorageProperties.storageDirectory + fileName);
    }

    public File getStorage() {
        return souvenirStorage;
    }
}
