package gfl.havryliuk.souvenirs.presenter.action.producer;

import gfl.havryliuk.souvenirs.entities.Entity;
import gfl.havryliuk.souvenirs.entities.Producer;
import gfl.havryliuk.souvenirs.presenter.Menu;
import gfl.havryliuk.souvenirs.presenter.action.Action;
import gfl.havryliuk.souvenirs.presenter.action.ActionEntity;
import gfl.havryliuk.souvenirs.presenter.action.ReturnableAction;
import gfl.havryliuk.souvenirs.presenter.action.menu.MenuTemplate;
//import gfl.havryliuk.souvenirs.presenter.action.menu.UpdateDeleteProducerMenu;
import gfl.havryliuk.souvenirs.service.ProducerService;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class DisplayAllWithSouvenirs implements Action, ReturnableAction {
 private List<Producer> producers;

    @Override
    public void execute() {
        List<Producer> producers = new ProducerService().getAll();
        Menu.showEntities(producers, "producer");

//
//        if (isUserChoseProducer(userChoice)) {
//            Producer producer = producers.get(userChoice - 1);
//            System.out.println(producer);
//            MenuTemplate menu = new UpdateDeleteProducerMenu();
//            menu.setEntity(producer);
//            menu.execute();
//
////            Entity entity = entities.get(userChoice - 1);
////            action = updateOrDelete(entity);//todo build subMenu
//        } else {
////            new MainMenu().execute();
//
//        }

    }
    @Override
    public Entity executeAndReturn() {
        List<Producer> producers = new ProducerService().getAll();
        int userChoice = Menu.showEntities(producers, "producer");

        return producers.get(userChoice - 1);
    }

//    private boolean isUserChoseProducer(int userChoice) {
//        return userChoice > 0;
//    }


}
