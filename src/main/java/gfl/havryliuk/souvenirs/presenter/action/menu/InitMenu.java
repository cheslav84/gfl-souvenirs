package gfl.havryliuk.souvenirs.presenter.action.menu;

import gfl.havryliuk.souvenirs.presenter.action.Action;
import gfl.havryliuk.souvenirs.presenter.action.Exit;
import gfl.havryliuk.souvenirs.presenter.action.init.CreateStorage;
import gfl.havryliuk.souvenirs.presenter.action.init.FillStorage;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class InitMenu extends MenuTemplate {
    public void setActionList() {
        actionList = ActionList.values();
    }

    @Getter
    @AllArgsConstructor
    private enum ActionList implements MenuAction {
        EXIT("End the program", new Exit()),
        CREATE_STORAGES("Create storages", new CreateStorage()),
        FILL_STORAGES_WITH_INITIAL_DATA("Fill storages with initial data", new FillStorage()),
        MAIN_MENU("Main menu", new MainMenu());

        private final String description;
        private final Action action;

        public void execute() {
            action.execute();
        }
    }

}
