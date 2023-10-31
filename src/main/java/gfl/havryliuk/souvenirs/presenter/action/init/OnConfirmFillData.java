package gfl.havryliuk.souvenirs.presenter.action.init;


import gfl.havryliuk.souvenirs.entities.Producer;
import gfl.havryliuk.souvenirs.entities.Souvenir;
import gfl.havryliuk.souvenirs.presenter.action.Action;
import gfl.havryliuk.souvenirs.presenter.action.menu.InitMenu;
import gfl.havryliuk.souvenirs.service.ProducerService;
import gfl.havryliuk.souvenirs.storage.ProducerFileStorage;
import gfl.havryliuk.souvenirs.storage.SouvenirFileStorage;
import gfl.havryliuk.souvenirs.storage.init.DataProvider;
import gfl.havryliuk.souvenirs.util.json.Document;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class OnConfirmFillData implements Action {

    @Override
    public void execute() {
        DataProvider dataProvider = new DataProvider();
        List<Producer> producers = dataProvider.getInitialData();

        ProducerService service = new ProducerService();
        service.createAll(producers);
    }
}
