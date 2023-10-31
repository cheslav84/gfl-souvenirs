package gfl.havryliuk.souvenirs.presenter.action.menu;

import gfl.havryliuk.souvenirs.presenter.action.Action;
import gfl.havryliuk.souvenirs.presenter.action.EmptyAction;
import gfl.havryliuk.souvenirs.presenter.action.Exit;
import gfl.havryliuk.souvenirs.presenter.action.init.OnConfirmCreateStorages;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;

public class ConfirmCreateStorages extends MenuTemplate {

    public List<MenuAction> getActionList() {
        return Arrays.asList(ActionList.values());
    }

    @Getter
    @AllArgsConstructor
    private enum ActionList implements MenuAction {
        EXIT("End the program", new Exit()),
        CREATE_STORAGES("YES", new OnConfirmCreateStorages()),
        SKIP("NO", new EmptyAction());

        private final String menuItem;
        private final Action action;

        public void execute() {
            action.execute();
        }
    }
}
