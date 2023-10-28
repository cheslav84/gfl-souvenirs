package gfl.havryliuk.souvenirs.presenter.action.menu;

import gfl.havryliuk.souvenirs.entities.Entity;
import gfl.havryliuk.souvenirs.presenter.Menu;
import gfl.havryliuk.souvenirs.presenter.action.Action;

import java.util.List;
import java.util.stream.Collectors;

public abstract class MenuTemplate implements Action {

    protected List<MenuAction> actionList;

    protected Entity entity;

    @Override
    public void execute() {
        actionList = getActionList();
        Action action = getAction(getMenuItems());
        action.execute();
    }
    protected abstract List<MenuAction> getActionList();

    public void setEntity(Entity entity) {
        this.entity = entity;
    }

    public List<String> getMenuItems() {
        return actionList.stream()
                .map(MenuAction::getMenuItem)
                .collect(Collectors.toList());
    }

    public Action getAction(List<String> menuItems) {
        return actionList.get(Menu.showItemsAndGetAnswer(menuItems));
    }

}