package gfl.havryliuk.souvenirs.presenter.action.menu;

import gfl.havryliuk.souvenirs.presenter.action.Action;
import gfl.havryliuk.souvenirs.presenter.action.Exit;
import gfl.havryliuk.souvenirs.presenter.action.producer.*;
import lombok.AllArgsConstructor;
import lombok.Getter;


public class ProducerSelectMenu extends MenuTemplate {

    public void setActionList() {
        actionList = ActionList.values();
    }

    @Getter
    @AllArgsConstructor
    private enum ActionList implements MenuAction {
        EXIT("End the program", new Exit()),
        ALL("Display all producers", new DisplayAllProducers()),
        ALL_WITH_SOUVENIRS("Display all producers with their souvenirs", new DisplayAllWithSouvenirs()),
        BY_PRICE_LESS_THAN("Display producers by price less than", new DisplayByPriceLessThan()),
        DELETE_PRODUCER("Display producers by souvenir and production year", new DisplayBySouvenirAndProductionYear()),
        PRODUCER_MENU("Producer menu", new ProducerMenu()),
        MAIN_MENU("Main menu", new MainMenu());

        private final String description;
        private final Action action;

        public void execute() {
            action.execute();
        }

    }
}
