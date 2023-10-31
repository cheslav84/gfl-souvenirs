package gfl.havryliuk.souvenirs.presenter.action.init;


import gfl.havryliuk.souvenirs.entities.Producer;
import gfl.havryliuk.souvenirs.entities.Souvenir;
import gfl.havryliuk.souvenirs.presenter.action.Action;
import gfl.havryliuk.souvenirs.storage.ProducerFileStorage;
import gfl.havryliuk.souvenirs.storage.SouvenirFileStorage;
import gfl.havryliuk.souvenirs.util.json.Document;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class OnConfirmCreateStorages implements Action {

    @Override
    public void execute() {
        ProducerFileStorage producerStorage = new ProducerFileStorage();
        SouvenirFileStorage souvenirStorage = new SouvenirFileStorage();
        initStorages(producerStorage, souvenirStorage);
    }
    private static void initStorages(ProducerFileStorage producerStorage, SouvenirFileStorage souvenirStorage) {
        new Document<Producer>(producerStorage).create();
        new Document<Souvenir>(souvenirStorage).create();
    }
}
