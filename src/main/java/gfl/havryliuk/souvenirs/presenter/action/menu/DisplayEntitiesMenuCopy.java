package gfl.havryliuk.souvenirs.presenter.action.menu;

import gfl.havryliuk.souvenirs.entities.Entity;
import gfl.havryliuk.souvenirs.presenter.Menu;
import gfl.havryliuk.souvenirs.presenter.action.Action;
import gfl.havryliuk.souvenirs.presenter.action.DeleteEntity;
import gfl.havryliuk.souvenirs.presenter.action.Exit;
import gfl.havryliuk.souvenirs.presenter.action.UpdateEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Slf4j
public abstract class DisplayEntitiesMenuCopy extends MenuTemplate {

    protected List<? extends Entity> entities;

    public DisplayEntitiesMenuCopy() {
        this.entities = getEntities();
    }

    public abstract UpdateEntity getUpdateAction(Entity entity);

    public abstract DeleteEntity getDeleteAction(Entity entity);

    public abstract List<? extends Entity> getEntities();

    public List<MenuAction> getActionList() {
        return Arrays.asList(ActionList.values());
    }


    @Override
    public Action getAction(List<String> menuItems) {
        int userChoice = Menu.showItemsWithEntitiesAndGetAnswer(menuItems, entities);
        Action action;
        if (userChoice <= entities.size() && userChoice != 0) {
//            action = new UpdateOrDelete();
//            Entity entity = entities.get(userChoice - 1);

            Entity entity = entities.get(userChoice - 1);
            action = updateOrDelete(entity);//todo build subMenu
        } else {
            action = (userChoice == 0) ? actionList.get(userChoice) : actionList.get(userChoice - entities.size());
        }
        return action;
    }

    private Action updateOrDelete(Entity entity) {//todo rename
        log.debug("What do you want to do with " + entity + "?");
        List<String> items = createItemList();
        return switch (Menu.showItemsAndGetAnswer(items)) {
            case 1 -> getUpdateAction(entity);
            case 2 -> getDeleteAction(entity);
            default -> new MainMenu();
        };
    }


    private List<String> createItemList() {
        List<String> choices = new ArrayList<>();
        choices.add("Cancel");
        choices.add("Update");
        choices.add("Delete");
        return choices;
    }


    @Getter
    @AllArgsConstructor
    public enum ActionList implements MenuAction {
        EXIT("End the program", new Exit()),
        MAIN_MENU("Main menu", new MainMenu());

        private final String menuItem;
        private final Action action;

        public void execute() {
            action.execute();
        }

    }
}
