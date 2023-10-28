package gfl.havryliuk.souvenirs.presenter.action.menu;

import gfl.havryliuk.souvenirs.entities.Entity;
import gfl.havryliuk.souvenirs.entities.Producer;
import gfl.havryliuk.souvenirs.presenter.action.Action;
import gfl.havryliuk.souvenirs.presenter.action.Exit;
import gfl.havryliuk.souvenirs.presenter.action.ReturnableAction;
import gfl.havryliuk.souvenirs.presenter.action.producer.DisplayAllProducers;
import gfl.havryliuk.souvenirs.presenter.action.producer.DisplayAllWithSouvenirs;
import gfl.havryliuk.souvenirs.presenter.action.producer.DisplayByPriceLessThan;
import gfl.havryliuk.souvenirs.presenter.action.producer.DisplayBySouvenirAndProductionYear;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;


public class ReturnableProducerSelectMenu extends ReturnableMenuTemplate {

    public List<MenuAction> getActionList() {
        return Arrays.asList(ActionList.values());
    }

    @Getter
    @AllArgsConstructor
    private enum ActionList implements MenuAction, ReturnableAction {
        EXIT("End the program", new Exit()),
        ALL("Display all producers", new DisplayAllProducers()),
        ALL_WITH_SOUVENIRS("Display all producers with their souvenirs", new DisplayAllWithSouvenirs()),
        BY_PRICE_LESS_THAN("Display producers by price less than", new DisplayByPriceLessThan()),
        DELETE_PRODUCER("Display producers by souvenir and production year", new DisplayBySouvenirAndProductionYear()),
        PRODUCER_MENU("Producer menu", new ProducerMenu()),
        MAIN_MENU("Main menu", new MainMenu());

        private final String menuItem;
        private final Action action;

        public void execute() {
            action.execute();
        }
        public Entity executeAndReturn() {
            ReturnableAction returnableAction = null;
            if (action instanceof ReturnableAction){
                returnableAction = (ReturnableAction) action;
            }
            return returnableAction.executeAndReturn();// todo can be null
        }


    }
}
