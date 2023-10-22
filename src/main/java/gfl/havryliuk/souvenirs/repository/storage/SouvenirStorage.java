package gfl.havryliuk.souvenirs.repository.storage;


import lombok.Getter;

import java.io.File;

@Getter
public class SouvenirStorage implements Storage {//todo виконати через інтерфейс, передавати свій storage при створенні документу
    public static final String SOUVENIR_STORAGE = "src/main/resources/data/Souvenirs.json";

    private final File souvenirStorage;

    public SouvenirStorage() {
        this.souvenirStorage = new File(SOUVENIR_STORAGE);
    }

    public File getStorage() {
        return souvenirStorage;
    }
}
