package gfl.havryliuk.souvenirs.presenter.action.init;

import gfl.havryliuk.souvenirs.entities.Producer;
import gfl.havryliuk.souvenirs.presenter.action.Action;
import gfl.havryliuk.souvenirs.presenter.action.menu.MainMenu;
import gfl.havryliuk.souvenirs.presenter.action.menu.YesOrNoMenu;
import gfl.havryliuk.souvenirs.service.ProducerService;
import gfl.havryliuk.souvenirs.storage.ProducerFileStorage;
import gfl.havryliuk.souvenirs.storage.SouvenirFileStorage;
import gfl.havryliuk.souvenirs.storage.init.DataProvider;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class FillStorage implements Action {

    @Override
    public void execute() {
        log.warn("Fill storage with default data?");
        new YesOrNoMenu().execute();
        log.warn("If data have been added once, it may result in a duplicate of same data!");
        new YesOrNoMenu().execute();
        ProducerFileStorage producerStorage = new ProducerFileStorage();//todo create factory
        SouvenirFileStorage souvenirStorage = new SouvenirFileStorage();

        DataProvider dataProvider = new DataProvider();
        List<Producer> producers = dataProvider.getInitialData();

        ProducerService service = new ProducerService(producerStorage, souvenirStorage);
        service.createAll(producers);

        new MainMenu().execute();
    }


}
