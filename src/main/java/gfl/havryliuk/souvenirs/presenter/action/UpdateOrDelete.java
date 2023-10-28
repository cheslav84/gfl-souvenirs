package gfl.havryliuk.souvenirs.presenter.action;

import gfl.havryliuk.souvenirs.presenter.action.menu.MenuAction;
import gfl.havryliuk.souvenirs.presenter.action.menu.MenuTemplate;
import gfl.havryliuk.souvenirs.presenter.action.menu.ProducerMenu;
import gfl.havryliuk.souvenirs.presenter.action.menu.SouvenirMenu;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;

public class UpdateOrDelete extends MenuTemplate {

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

