package gfl.havryliuk.souvenirs.presenter.action;

import gfl.havryliuk.souvenirs.presenter.Action;
import gfl.havryliuk.souvenirs.presenter.MainActionList;
import gfl.havryliuk.souvenirs.presenter.menu.Menu;

public class MainAction implements Action {
    @Override
    public void execute() {
        MainActionList[] actions = MainActionList.values();
        String[] actionNames = MainActionList.mapAction();
        actions[Menu.buildMenu(actionNames)].execute();
    }
}
