package gfl.havryliuk.souvenirs;

import gfl.havryliuk.souvenirs.entities.Producer;
import gfl.havryliuk.souvenirs.entities.Souvenir;
import gfl.havryliuk.souvenirs.repository.storage.ProducerStorage;
import gfl.havryliuk.souvenirs.util.json.Document;

public class Main {

    public static void main(String[] args) {
        ProducerStorage producerStorage = new ProducerStorage();
        initStorages(producerStorage);
    }

    private static void initStorages(ProducerStorage producerStorage) {
//        new Document<Producer>(producerStorage).create(producerStorage.getProducerStorage());
//        new Document<Souvenir>(producerStorage).create(producerStorage.getStorage());
    }
}
