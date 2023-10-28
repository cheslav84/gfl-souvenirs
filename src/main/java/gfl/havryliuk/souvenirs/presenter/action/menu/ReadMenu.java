package gfl.havryliuk.souvenirs.presenter.action.menu;

import gfl.havryliuk.souvenirs.presenter.action.Action;
import gfl.havryliuk.souvenirs.presenter.action.Exit;
import lombok.AllArgsConstructor;
import lombok.Getter;


public class ReadMenu {
//public class ReadMenu extends MenuTemplate {

//    public void setActionList() {
//        actionList = Arrays.asList(ActionList.values());
//    }

    @Getter
    @AllArgsConstructor
    public enum ActionList implements MenuAction {//todo замінити на мапу. це дозволить розширити її командами подумати
        EXIT("End the program", new Exit()),
        MAIN_MENU("Main menu", new MainMenu());

        private final String menuItem;
        private final Action action;

        public void execute() {
//            action.execute();
        }

    }
}
