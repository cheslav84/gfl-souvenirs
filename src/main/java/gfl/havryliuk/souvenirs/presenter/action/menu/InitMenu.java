package gfl.havryliuk.souvenirs.presenter.action.menu;

import gfl.havryliuk.souvenirs.presenter.action.Action;
import gfl.havryliuk.souvenirs.presenter.action.EmptyAction;
import gfl.havryliuk.souvenirs.presenter.action.init.CreateStorage;
import gfl.havryliuk.souvenirs.presenter.action.init.FillStorage;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.List;

@Slf4j
public class InitMenu extends MenuTemplate {
    public List<MenuAction> getActionList() {
        return Arrays.asList(ActionList.values());
    }

    @Getter
    @AllArgsConstructor
    private enum ActionList implements MenuAction {
        EMPTY("Go ahead!", new EmptyAction()),
        CREATE_STORAGES("Create storages", new CreateStorage()),
        FILL_STORAGES_WITH_INITIAL_DATA("Fill storages with initial data", new FillStorage());

        private final String menuItem;
        private final Action action;

        public void execute() {
            action.execute();
        }
    }

}
