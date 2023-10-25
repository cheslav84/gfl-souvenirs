package gfl.havryliuk.souvenirs;

import gfl.havryliuk.souvenirs.presenter.action.menu.InitMenu;
import gfl.havryliuk.souvenirs.service.SouvenirService;
import gfl.havryliuk.souvenirs.storage.ProducerFileStorage;
import gfl.havryliuk.souvenirs.storage.SouvenirFileStorage;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Main {


    public static void main(String[] args) {
        log.warn("Welcome to souvenir store!");

//        ProducerFileStorage producerStorage = new ProducerFileStorage();//todo create factory
//        SouvenirFileStorage souvenirStorage = new SouvenirFileStorage();
//        SouvenirService service = new SouvenirService(producerStorage, souvenirStorage);
//
//        System.out.println(service.getAll());

        try {
            new InitMenu().execute();
        } catch (Exception e) {
            log.warn("""
                    An error have occurred.\s
                    The developer is deeply concern about this and will fix one as soon as possible!\s
                    Try please again.""");
            System.exit(0);
        }



    }


}
