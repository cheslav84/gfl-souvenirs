package gfl.havryliuk.souvenirs;

import gfl.havryliuk.souvenirs.entities.Producer;
import gfl.havryliuk.souvenirs.entities.Souvenir;
import gfl.havryliuk.souvenirs.storage.ProducerFileStorage;
import gfl.havryliuk.souvenirs.storage.SouvenirFileStorage;
import gfl.havryliuk.souvenirs.util.json.Document;

public class Main {

    public static void main(String[] args) {
        ProducerFileStorage producerStorage = new ProducerFileStorage();//todo create factory
        SouvenirFileStorage souvenirStorage = new SouvenirFileStorage();
        initStorages(producerStorage, souvenirStorage);
    }

    private static void initStorages(ProducerFileStorage producerStorage, SouvenirFileStorage souvenirStorage) {
        new Document<Producer>(producerStorage).create();
        new Document<Souvenir>(souvenirStorage).create();
    }
}
