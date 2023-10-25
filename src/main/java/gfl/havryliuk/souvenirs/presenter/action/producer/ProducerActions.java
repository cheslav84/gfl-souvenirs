package gfl.havryliuk.souvenirs.presenter.action.producer;

import gfl.havryliuk.souvenirs.presenter.Action;
import gfl.havryliuk.souvenirs.presenter.menu.Menu;

public class ProducerActions implements Action {
    @Override
    public void execute() {
        ProducersActionList[] actions = ProducersActionList.values();
        String[] actionNames = ProducersActionList.mapAction();
        actions[Menu.buildMenu(actionNames)].execute();
    }
}
