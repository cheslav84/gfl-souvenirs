package gfl.havryliuk.souvenirs.presenter.action.menu;

import gfl.havryliuk.souvenirs.presenter.MenuBuilder;
import gfl.havryliuk.souvenirs.presenter.action.Action;

import java.util.Arrays;

public abstract class MenuTemplate implements Action {

    protected MenuAction[] actionList;

    @Override
    public void execute() {
        setActionList();
        MenuAction action = getAction(getDescriptions());
        action.execute();
    }

    abstract void setActionList();

    private String[] getDescriptions() {
        return Arrays.stream(actionList)
                .map(MenuAction::getDescription)
                .toArray(String[]::new);
    }

    private MenuAction getAction(String[] actionDescriptions) {
        return actionList[MenuBuilder.build(actionDescriptions)];
    }

}
