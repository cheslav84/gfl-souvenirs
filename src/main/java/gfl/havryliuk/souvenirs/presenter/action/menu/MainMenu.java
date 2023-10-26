package gfl.havryliuk.souvenirs.presenter.action.menu;

import gfl.havryliuk.souvenirs.presenter.action.Action;
import gfl.havryliuk.souvenirs.presenter.action.Exit;
import lombok.AllArgsConstructor;
import lombok.Getter;

public class MainMenu extends MenuTemplate {//todo спробувати вирішити не через конструктор. Рекурсвний виклик методів

    public void setActionList() {
        actionList = ActionList.values();
    }

    @Getter
    @AllArgsConstructor
    private enum ActionList implements MenuAction {//todo команда в команді.
        EXIT("End the program", new Exit()),
        PRODUCER_ACTIONS("Producer options", new ProducerMenu()),
        SOUVENIR_ACTIONS("Souvenir options", new SouvenirMenu());

        private final String description;
        private final Action action;

        public void execute() {
            action.execute();
        }
    }

}

