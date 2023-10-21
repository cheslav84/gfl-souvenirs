package gfl.havryliuk.souvenirs;

import gfl.havryliuk.souvenirs.entities.Producer;
import gfl.havryliuk.souvenirs.entities.Souvenir;
import gfl.havryliuk.souvenirs.util.StorageProperties;
import gfl.havryliuk.souvenirs.util.json.Document;

import java.io.File;

public class Main {

    public static void main(String[] args) {

        initStorages();
    }

    private static void initStorages() {
        StorageProperties properties = new StorageProperties();
        new Document<Producer>().create(new File(properties.getProducersPathStorage()));
        new Document<Souvenir>().create(new File(properties.getSouvenirsPathStorage()));
    }
}
