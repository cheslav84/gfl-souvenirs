package gfl.havryliuk.souvenirs.presenter.action.menu;

import gfl.havryliuk.souvenirs.presenter.action.Action;
import gfl.havryliuk.souvenirs.presenter.action.Exit;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;

public class MainMenu extends MenuTemplate {

    public List<MenuAction> getActionList() {
        return Arrays.asList(ActionList.values());
    }

    @Getter
    @AllArgsConstructor
    private enum ActionList implements MenuAction {
        EXIT("End the program", new Exit()),
        PRODUCER_MENU("Producer menu", new ProducerMenu()),
        SOUVENIR_MENU("Souvenir menu", new SouvenirMenu());

        private final String menuItem;
        private final Action action;

        public void execute() {
            action.execute();
        }
    }

}

