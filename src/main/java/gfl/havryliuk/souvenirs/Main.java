package gfl.havryliuk.souvenirs;

import gfl.havryliuk.souvenirs.entities.Producer;
import gfl.havryliuk.souvenirs.entities.Souvenir;
import gfl.havryliuk.souvenirs.repository.storage.Storage;
import gfl.havryliuk.souvenirs.util.json.Document;

public class Main {

    public static void main(String[] args) {
        Storage storage = new Storage();
        initStorages(storage);
    }

    private static void initStorages(Storage storage) {
        new Document<Producer>(storage).create(storage.getProducerStorage());
        new Document<Souvenir>(storage).create(storage.getSouvenirsStorage());
    }
}
