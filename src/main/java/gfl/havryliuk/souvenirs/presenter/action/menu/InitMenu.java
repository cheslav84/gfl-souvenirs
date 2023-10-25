package gfl.havryliuk.souvenirs.presenter.action.menu;

import gfl.havryliuk.souvenirs.presenter.MenuBuilder;
import gfl.havryliuk.souvenirs.presenter.action.Action;
import gfl.havryliuk.souvenirs.presenter.action.Exit;
import gfl.havryliuk.souvenirs.presenter.action.init.CreateStorage;
import gfl.havryliuk.souvenirs.presenter.action.init.FillStorage;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;

@Slf4j
public class InitMenu implements Action, Menu {
    @Override
    public void execute() {
        String[] actions = Arrays.stream(ActionList.values())
                .map(ActionList::getName)
                .toArray(String[]::new);

       ActionList.values()[MenuBuilder.build(actions)].perform();
    }

    @Getter
    @AllArgsConstructor
    private enum ActionList {
        EXIT("End the program", new Exit()),
        CREATE_STORAGES("Create storages", new CreateStorage()),
        FILL_STORAGES_WITH_INITIAL_DATA("Fill storages with initial data", new FillStorage()),
        MAIN_MENU("Main menu", new MainMenu());

        private final String name;
        private final Action action;

        public void perform() {
            action.execute();
        }
    }

}
