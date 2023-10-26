package gfl.havryliuk.souvenirs.presenter.action.menu;

import gfl.havryliuk.souvenirs.presenter.action.Action;
import gfl.havryliuk.souvenirs.presenter.action.Exit;
import gfl.havryliuk.souvenirs.presenter.action.souvenir.CreateSouvenir;
import gfl.havryliuk.souvenirs.presenter.action.souvenir.DeleteSouvenir;
import gfl.havryliuk.souvenirs.presenter.action.souvenir.UpdateSouvenir;
import lombok.AllArgsConstructor;
import lombok.Getter;

public class SouvenirMenu extends MenuTemplate {
//    public SouvenirMenu() {
//        super.values = ActionList.values();
//    }

    public void setActionList() {
        actionList = ActionList.values();
    }

    @Getter
    @AllArgsConstructor
    private enum ActionList implements MenuAction {
        EXIT("End the program", new Exit()),
        CREATE_SOUVENIR("Create producer", new CreateSouvenir()),
        UPDATE_SOUVENIR("Update producer", new UpdateSouvenir()),
        DELETE_SOUVENIR("Delete producer", new DeleteSouvenir()),
//        SELECT_OPTIONS("Select options", new DeleteSouvenir());//TODO
        SELECT_OPTIONS("Select options", new DeleteSouvenir()),//TODO
        MAIN_MENU("Main menu", new MainMenu());

        private final String description;
        private final Action action;

        public void execute() {
            action.execute();
        }
    }
}
