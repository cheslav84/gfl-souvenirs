package gfl.havryliuk.souvenirs.presenter.action.menu;

import gfl.havryliuk.souvenirs.presenter.action.Action;
import gfl.havryliuk.souvenirs.presenter.action.Exit;
import gfl.havryliuk.souvenirs.presenter.action.souvenir.CreateSouvenir;
import gfl.havryliuk.souvenirs.presenter.action.souvenir.DeleteSouvenir;
import gfl.havryliuk.souvenirs.presenter.action.souvenir.UpdateSouvenir;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;

public class SouvenirMenu extends MenuTemplate {

    public List<MenuAction> getActionList() {
        return Arrays.asList(ActionList.values());
    }

    @Getter
    @AllArgsConstructor
    private enum ActionList implements MenuAction {
        EXIT("End the program", new Exit()),
        CREATE_SOUVENIR("Create souvenir", new CreateSouvenir()),
        UPDATE_SOUVENIR("Update souvenir", new UpdateSouvenir()),
        DELETE_SOUVENIR("Delete souvenir", new DeleteSouvenir()),
        SELECT_OPTIONS("Select options", new SouvenirSelectMenu()),
        MAIN_MENU("Main menu", new MainMenu());

        private final String menuItem;
        private final Action action;

        public void execute() {
            action.execute();
        }
    }
}
