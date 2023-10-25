package gfl.havryliuk.souvenirs;

import gfl.havryliuk.souvenirs.entities.Producer;
import gfl.havryliuk.souvenirs.entities.Souvenir;
import gfl.havryliuk.souvenirs.presenter.action.MainAction;
import gfl.havryliuk.souvenirs.storage.ProducerFileStorage;
import gfl.havryliuk.souvenirs.storage.SouvenirFileStorage;
import gfl.havryliuk.souvenirs.util.json.Document;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Main {


    public static void main(String[] args) {
        log.warn("Welcome to souvenir store!");

//        ProducerFileStorage producerStorage = new ProducerFileStorage();//todo create factory
//        SouvenirFileStorage souvenirStorage = new SouvenirFileStorage();
//        initStorages(producerStorage, souvenirStorage);

//        Service service = new Service();
//        log.error("Program is started. Error");
//        log.debug("Program is started. debug");


//        MainActionList[] actions = MainActionList.values();
//        String[] actionNames = MainActionList.mapAction();

        try {
            new MainAction().execute();
        } catch (Exception e) {
            log.warn("""
                    An error have occurred.\s
                    The developer is deeply concern about this and will fix one as soon as possible!\s
                    Try please again.""");
            System.exit(0);
        }

//        while (true) {
//            int userChoiceNumber = Menu.getMenu(actionNames);
//            actions[userChoiceNumber].execute();
//        }

    }

    private static void initStorages(ProducerFileStorage producerStorage, SouvenirFileStorage souvenirStorage) {
        new Document<Producer>(producerStorage).create();
        new Document<Souvenir>(souvenirStorage).create();
    }
}
