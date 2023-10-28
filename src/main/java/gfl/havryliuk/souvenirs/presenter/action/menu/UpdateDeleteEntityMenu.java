package gfl.havryliuk.souvenirs.presenter.action.menu;

import gfl.havryliuk.souvenirs.presenter.action.Action;
import gfl.havryliuk.souvenirs.presenter.action.Exit;
import gfl.havryliuk.souvenirs.presenter.action.UpdateEntity;
import gfl.havryliuk.souvenirs.presenter.action.producer.DeleteProducer;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;

public class UpdateDeleteEntityMenu extends MenuTemplate {

    public List<MenuAction> getActionList() {
        return Arrays.asList(ActionList.values());

    }


    @Getter
    @AllArgsConstructor
    private enum ActionList implements MenuAction {
        EXIT("End the program", new Exit()),
        UPDATE("Update", new UpdateEntity()),
        DELETE("Delete", new DeleteProducer()),
        MAIN_MENU("Main menu", new MainMenu());

        private final String menuItem;
        private final Action action;

        public void execute() {
            action.execute();
        }
    }
}
