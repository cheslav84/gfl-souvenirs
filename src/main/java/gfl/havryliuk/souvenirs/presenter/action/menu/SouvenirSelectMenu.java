package gfl.havryliuk.souvenirs.presenter.action.menu;

import gfl.havryliuk.souvenirs.presenter.action.Action;
import gfl.havryliuk.souvenirs.presenter.action.Exit;
import gfl.havryliuk.souvenirs.presenter.action.souvenir.*;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;

public class SouvenirSelectMenu extends MenuTemplate {

    public List<MenuAction> getActionList() {
        return Arrays.asList(ActionList.values());
    }

    @Getter
    @AllArgsConstructor
    private enum ActionList implements MenuAction {
        EXIT("End the program", new Exit()),
        ALL("Display all souvenirs", new DisplayAllSouvenirs()),
        ALL_GROUPED_BY_PRODUCTION_YEAR("Display all souvenirs grouped by production year", new DisplayAllGroupedByProductionYear()),
        BY_PRODUCER("Display souvenirs by producer", new DisplayByProducer()),
        BY_PRODUCTION_COUNTRY("Display souvenirs by production country", new DisplayByProductionCountry()),
        SOUVENIR_MENU("Souvenir menu", new SouvenirMenu()),
        MAIN_MENU("Main menu", new MainMenu());

        private final String menuItem;
        private final Action action;

        public void execute() {
            action.execute();
        }
    }
}
