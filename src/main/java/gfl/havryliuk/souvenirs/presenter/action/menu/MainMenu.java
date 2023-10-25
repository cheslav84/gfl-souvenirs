package gfl.havryliuk.souvenirs.presenter.action.menu;

import gfl.havryliuk.souvenirs.presenter.action.Action;
import gfl.havryliuk.souvenirs.presenter.action.Exit;
import gfl.havryliuk.souvenirs.presenter.MenuBuilder;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

public class MainMenu implements Action, Menu {
    @Override
    public void execute() {//todo шаблонний метод. Винести.
        String[] actionNames = Arrays.stream(ActionList.values())
                .map(ActionList::getName)
                .toArray(String[]::new);

        ActionList.values()[MenuBuilder.build(actionNames)].perform();
    }

    @Getter
    @AllArgsConstructor
    private enum ActionList {
        EXIT("End the program", new Exit()),
        PRODUCER_ACTIONS("Producer options", new ProducerMenu()),
        SOUVENIR_ACTIONS("Souvenir options", new SouvenirMenu());

        private final String name;
        private final Action action;

        public void perform() {
            action.execute();
        }
    }

}
